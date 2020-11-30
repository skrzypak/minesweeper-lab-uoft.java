package pl.polsl.lab.saper.controller;

import javafx.scene.layout.VBox;
import pl.polsl.lab.saper.exception.FieldException;
import pl.polsl.lab.saper.model.Game;
import pl.polsl.lab.saper.model.Index;
import pl.polsl.lab.saper.view.GameView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class responsible for manage model and view game
 *
 * @author Konrad Skrzypczyk
 * @version 2.0
 */
public class GameController {

    private Game gameModel;             //Game model object handler
    private final GameView gameView;    //Game view object handler

    /**
     * Class constructor.
     * @param height new game board height;
     * @param width  new game board width;
     * @throws OutOfMemoryError when board can't be write to memory
     * @throws IllegalArgumentException when board has wrong arguments value
     */
    public GameController(Integer height, Integer width) {
        try {
            gameModel = new Game(height, width);
            randomMines(height, width);
        } catch (IllegalArgumentException e) {
            this.gameModel = null;
            throw new IllegalArgumentException(e.getMessage());
        }
        catch (OutOfMemoryError e) {
            this.gameModel = null;
            throw new OutOfMemoryError(e.getMessage());
        }
        gameView = new GameView();
    }

    /**
     * Method random mines and putting to game board
     * @param height board height without border (user input value)
     * @param width board width without border (user input value)
     * @throws IllegalArgumentException if size is not correct
     */
    private void randomMines(Integer height, Integer width) throws IllegalArgumentException {

        if(height == null || height <= 0) throw new IllegalArgumentException("Height null or less than 1");
        if(width == null || width <= 0) throw new IllegalArgumentException("Width null or less than 1");

        int boardElements = height*width;
        int numOfMine;

        if(boardElements > 3) {
            if(boardElements < 6) {
                numOfMine = ThreadLocalRandom.current().nextInt(1, boardElements/2);
            }
            else {
                numOfMine = ThreadLocalRandom.current().nextInt(boardElements/6, boardElements/3);
            }
        } else {
            numOfMine = ThreadLocalRandom.current().nextInt(0, 1);
        }

        gameModel.setFreeFieldCounter(gameModel.getFreeFieldCounter() - numOfMine);

        int randRow;
        int randCol;

        for (int r = 0; r < numOfMine; r++) {

            do {

                if(height > 1)
                    randRow = ThreadLocalRandom.current().nextInt(1, height);
                else randRow = 1;

                if(width > 1)
                    randCol = ThreadLocalRandom.current().nextInt(1, width);
                else  randCol = 1;

            } while(gameModel.getBoardData().get(randRow, randCol).isMine());

            gameModel.getBoardData().get(randRow, randCol).setAsMine();
        }
    }

    public void initializeBoardView(VBox boardViewFXML) {

        gameView.initializeView(boardViewFXML, gameModel.getBoardData(), this);
    }

    private void setFieldAsSelected(Index inx, Integer mine) throws FieldException {
        gameModel.setFieldAsSelected(mine, inx.getRowIndex(), inx.getColIndex());
        gameView.updateFieldView(inx, gameView.getSelectedFieldSymbol(mine));
    }

    private void switchFieldMark(Index inx) throws FieldException {
        if(gameModel.getInfoAboutMark(inx.getRowIndex(), inx.getColIndex())) {
            gameModel.removeFieldMark(inx.getRowIndex(), inx.getColIndex());
            gameView.updateFieldView(inx, gameView.getEmptyFieldSymbol());
        } else {
            gameModel.setFieldAsMark(inx.getRowIndex(), inx.getColIndex());
            gameView.updateFieldView(inx, gameView.getMarkedFieldSymbol());
        }
    }

    public void onMouseButtonPrimaryFieldClick(Index inx)  {
        int rowInx = inx.getRowIndex();
        int colInx = inx.getColIndex();
        try {
            Integer mines = countMines(rowInx, colInx);
            if(mines == 0) findUntilNoZeroField(rowInx, colInx);
            else setFieldAsSelected(new Index(rowInx, colInx), mines);
            checkGameStatus(rowInx, colInx);
        } catch (FieldException e) {
            // TODO
            System.out.println(e.getMessage());
        }
    }

    public void onMouseButtonSecondaryFieldClick(Index inx) {
        int rowInx = inx.getRowIndex();
        int colInx = inx.getColIndex();
        try {
            switchFieldMark(new Index(rowInx, colInx));
        } catch (FieldException e) {
            // TODO
            System.out.println(e.getMessage());
        }
    }



    public void playFromRawText(String input) {
        if(!isGameRunning()) return;
        if(!updateField(input)) {
            // TODO
            System.out.println("Input error");
        }
    }

    /**
     * Method update board and game state
     * @param input String, input from player
     * @return true (user input correct values) or false (user input wrong values)
     * */
    private boolean updateField(String input) {
        String[] param = parseUserInput(input);

        if(param.length == 1) {
            if(param[0].equals("e") || param[0].equals("E")) {
                gameModel.setCancel();
                gameResult();
                return true;
            } else {
                gameView.err("You want end? Type E, otherwise input 3 parameters");
                return false;
            }
        }

        if(param.length != 3) {
            gameView.err("Invalid number of arguments. Try again");
            return false;
        }

        try {
            Character sym = param[0].charAt(0);
            int rowInx = Integer.parseInt(param[1]);
            int colInx = Integer.parseInt(param[2]);

            gameModel.isCorrectField(rowInx, colInx);

            if(sym.equals('*'))
                switchFieldMark(new Index(rowInx, colInx));
            else if(sym.equals('o')) {
                Integer mines = countMines(rowInx, colInx);

                if(mines == 0)
                    findUntilNoZeroField(rowInx, colInx);
                else
                    setFieldAsSelected(new Index(rowInx, colInx), mines);

                checkGameStatus(rowInx, colInx);
            } else {
                gameView.err("Invalid first symbol. Use only '*' or 'o");
                return false;
            }

            return true;

        } catch (NumberFormatException e) {
            gameView.err("You input invalid number(s). Try again");
            return false;
        } catch (FieldException e) {
            gameView.err(e.getMessage());
            return false;
        }

    }

    /**
     * Functions check game status
     * If user selected filed with mine, function set game status as lose in model
     * If user selected last empty field, function set game status as win in model
     * @param rowInx row index of clicked filed
     * @param colInx column index clicked field
     */
    private void checkGameStatus(int rowInx, int colInx) {

        try {
            if(gameModel.getInfoAboutMine(rowInx, colInx)) {
                gameModel.setLose();
                gameResult();
                return;
            }
        } catch (FieldException ignored) { }

        if(gameModel.getFreeFieldCounter() <= 0) {
            gameModel.setWin();
            gameResult();
        }
    }

    /**
     * Check if game is running
     * @return TRUE if game is running or FALSE if end
     */
    public boolean isGameRunning() {
        return gameModel.getRunning();
    }

    /**
     * Function parse input from String to String[]
     * @param input string input from user
     * @return parsed input data
     *  */
    private String[] parseUserInput(String input) {
        return input.split(" ");
    }

    /**
     * Function calling to renderGameResult in GameView class
     */
   private void gameResult() {
       ArrayList<Index> minesIndex = new ArrayList<>();
       for(int i = 1; i < gameModel.getNumOfRows() - 1; i++) {
           for(int j = 1; j < gameModel.getNumOfCols() - 1; j++) {
               try {
                   if(gameModel.getInfoAboutMine(i, j))
                       minesIndex.add(new Index(i, j));
               } catch (FieldException ignored) { }
           }
       }
       gameView.showResult(gameModel.getGameResult(), minesIndex);
    }

    /**
     * Function count mines around field
     * @param row index of row filed
     * @param col index of column field
     * @return number of mines around field
     */
    private Integer countMines(Integer row, Integer col) {

        int num = 0;
        
        LinkedList<Index> index = new LinkedList<>();
        index.add(new Index(row-1, col-1));
        index.add(new Index(row-1, col));
        index.add(new Index(row-1, col+1));
        index.add(new Index(row, col-1));
        index.add(new Index(row, col+1));
        index.add(new Index(row+1, col-1));
        index.add(new Index(row+1, col));
        index.add(new Index(row+1, col+1));

        for (Index i: index) {
            try {
                if (gameModel.getInfoAboutMine(i.getRowIndex(), i.getColIndex()))
                    num++;
            } catch (FieldException ignored) { }
        }

        return num;
    }

    /**
     * Discover recursive all zero mines fields
     * @param row index of row filed start
     * @param col index of column field start
     */
    private void findUntilNoZeroField(Integer row, Integer col) {

        try {
            if (gameModel.getInfoAboutMine(row, col) || gameModel.fieldSelected(row, col)) {
                //Field has mine or was selected earlier so end recursive
                return;
            }
        } catch (FieldException e) {
            // Field is boarder so end recursive
            return;
        }

        if (countMines(row, col) > 0) {
            // Field is no mine but around have samo mine, so set field as selected and end recursive
            try {
                setFieldAsSelected(new Index(row, col), countMines(row, col));
            } catch (FieldException ignored) { }
            return;
        }

        try {
            // Field has no mine so set as 0, next call recursive to next fields
            setFieldAsSelected(new Index(row, col), 0);
        } catch (FieldException ignored) { }

        findUntilNoZeroField(row + 1, col - 1);
        findUntilNoZeroField(row + 1, col + 1);
        findUntilNoZeroField(row - 1, col - 1);
        findUntilNoZeroField(row - 1, col + 1);
        findUntilNoZeroField(row + 1, col);
        findUntilNoZeroField(row, col + 1);
        findUntilNoZeroField(row, col - 1);
        findUntilNoZeroField(row - 1, col);

    }

}
