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

    private final GameView gameView;    //Game view object handler
    private Game gameModel;             //Game model object handler

    /**
     * Class constructor.
     *
     * @param height new game board height;
     * @param width  new game board width;
     * @throws OutOfMemoryError         when board can't be write to memory
     * @throws IllegalArgumentException when board has wrong arguments value
     */
    public GameController(Integer height, Integer width) {
        try {
            gameModel = new Game(height, width);
            randomMines(height, width);
        } catch (IllegalArgumentException e) {
            this.gameModel = null;
            throw new IllegalArgumentException(e.getMessage());
        } catch (OutOfMemoryError e) {
            this.gameModel = null;
            throw new OutOfMemoryError(e.getMessage());
        }
        gameView = new GameView();
    }

    /**
     * Method random mines and putting to game board
     *
     * @param height board height without border (user input value)
     * @param width  board width without border (user input value)
     * @throws IllegalArgumentException if size is not correct
     */
    private void randomMines(Integer height, Integer width) throws IllegalArgumentException {

        if (height == null || height <= 0) throw new IllegalArgumentException("Height null or less than 1");
        if (width == null || width <= 0) throw new IllegalArgumentException("Width null or less than 1");

        int boardElements = height * width;
        int numOfMine;

        if (boardElements > 3) {
            if (boardElements < 6) {
                numOfMine = ThreadLocalRandom.current().nextInt(1, boardElements / 2);
            } else {
                numOfMine = ThreadLocalRandom.current().nextInt(boardElements / 6, boardElements / 3);
            }
        } else {
            numOfMine = ThreadLocalRandom.current().nextInt(0, 1);
        }

        gameModel.setFreeFieldCounter(gameModel.getFreeFieldCounter() - numOfMine);

        int randRow;
        int randCol;

        for (int r = 0; r < numOfMine; r++) {

            do {

                if (height > 1)
                    randRow = ThreadLocalRandom.current().nextInt(1, height);
                else randRow = 1;

                if (width > 1)
                    randCol = ThreadLocalRandom.current().nextInt(1, width);
                else randCol = 1;

            } while (gameModel.getBoardData().get(new Index(randRow, randCol)).isMine());

            gameModel.getBoardData().get(new Index(randRow, randCol)).setAsMine();
        }
    }

    /**
     * Method render new board
     *
     * @param boardViewFXML VBox container from .fxml
     */
    public void initializeBoardView(VBox boardViewFXML) {

        gameView.initializeView(boardViewFXML, gameModel.getBoardData(), this);
    }

    /**
     * Method set field as selected. Method update game model and view
     *
     * @param inx field index object
     * @param mine number of mine
     * @throws FieldException when invalid index of field
     */
    private void setFieldAsSelected(Index inx, Integer mine) throws FieldException {
        gameModel.setFieldAsSelected(mine, inx);
        gameView.updateFieldView(inx, gameView.getSelectedFieldSymbol(mine));
    }

    /**
     * Method switch field mark. Method update game model and view
     *
     * @param inx field index object
     * @throws FieldException when invalid index of field
     */
    private void switchFieldMark(Index inx) throws FieldException {
        if (gameModel.getInfoAboutMark(inx)) {
            gameModel.removeFieldMark(inx);
            gameView.updateFieldView(inx, gameView.getEmptyFieldSymbol());
        } else {
            gameModel.setFieldAsMark(inx);
            gameView.updateFieldView(inx, gameView.getMarkedFieldSymbol());
        }
    }

    /**
     * Method do action when user click primary mouse button on field
     *
     * @param inx field index object
     */
    public void onMouseButtonPrimaryFieldClick(Index inx) {
        try {
            Integer mines = countMines(inx);
            if (mines == 0) findUntilNoZeroField(inx);
            else setFieldAsSelected(inx, mines);
            checkGameStatus(inx);
        } catch (FieldException e) {
            gameView.err(e.getMessage());
        }
    }

    /**
     * Method do action when user click secondary mouse button on field
     *
     * @param inx field index object
     */
    public void onMouseButtonSecondaryFieldClick(Index inx) {
        int rowInx = inx.getRowIndex();
        int colInx = inx.getColIndex();
        try {
            switchFieldMark(new Index(rowInx, colInx));
        } catch (FieldException e) {
            gameView.err(e.getMessage());
        }
    }

    /**
     * Method play round and update game state, board from user input
     *
     * @param input String, input from player
     */
    public void playFromRawText(String input) {
        if (!isGameRunning()) return;

        String[] param = parseUserInput(input);

        if (param.length == 1) {
            if (param[0].equals("e") || param[0].equals("E")) {
                gameModel.setCancel();
                gameResult();
            } else
                gameView.err("You want cancel game? Type E, otherwise input 3 parameters");
            return;
        }

        if (param.length != 3) {
            gameView.err("Invalid number of arguments. Try again");
            return;
        }

        try {
            Character sym = param[0].charAt(0);
            Index inx = new Index(Integer.parseInt(param[1]), Integer.parseInt(param[2]));

            gameModel.isCorrectField(inx);

            if (sym.equals('*'))
                switchFieldMark(inx);
            else if (sym.equals('o')) {
                Integer mines = countMines(inx);
                if (mines == 0) findUntilNoZeroField(inx);
                else setFieldAsSelected(inx, mines);
                checkGameStatus(inx);
            } else
                gameView.err("Invalid first symbol. Use only '*' or 'o");

        } catch (NumberFormatException e) {
            gameView.err("You input invalid number(s). Try again");
        } catch (FieldException e) {
            gameView.err(e.getMessage());
        }
    }

    /**
     * Functions check game status
     * If user selected filed with mine, function set game status as lose in model
     * If user selected last empty field, function set game status as win in model
     *
     * @param inx field index object
     */
    private void checkGameStatus(Index inx) {

        try {
            if (gameModel.getInfoAboutMine(inx)) {
                gameModel.setLose();
                gameResult();
                return;
            }
        } catch (FieldException ignored) {
        }

        if (gameModel.getFreeFieldCounter() <= 0) {
            gameModel.setWin();
            gameResult();
        }
    }

    /**
     * Check if game is running
     *
     * @return TRUE if game is running or FALSE if end
     */
    public boolean isGameRunning() {
        return gameModel.getRunning();
    }

    /**
     * Function parse input from String to String[]
     *
     * @param input string input from user
     * @return parsed input data
     */
    private String[] parseUserInput(String input) {
        return input.split(" ");
    }

    /**
     * Function calling to renderGameResult in GameView class
     */
    private void gameResult() {
        ArrayList<Index> minesIndex = new ArrayList<>();
        for (int i = 1; i < gameModel.getNumOfRows() - 1; i++) {
            for (int j = 1; j < gameModel.getNumOfCols() - 1; j++) {
                try {
                    Index inx = new Index(i, j);
                    if (gameModel.getInfoAboutMine(inx))
                        minesIndex.add(inx);
                } catch (FieldException ignored) {
                }
            }
        }
        gameView.showResult(gameModel.getGameResult(), minesIndex);
    }

    /**
     * Function count mines around field
     *
     * @param inx field index object
     * @return number of mines around field
     */
    private Integer countMines(Index inx) {

        int num = 0;
        int row = inx.getRowIndex();
        int col = inx.getColIndex();

        LinkedList<Index> index = new LinkedList<>();
        index.add(new Index(row - 1, col - 1));
        index.add(new Index(row - 1, col));
        index.add(new Index(row - 1, col + 1));
        index.add(new Index(row, col - 1));
        index.add(new Index(row, col + 1));
        index.add(new Index(row + 1, col - 1));
        index.add(new Index(row + 1, col));
        index.add(new Index(row + 1, col + 1));

        for (Index i : index) {
            try {
                if (gameModel.getInfoAboutMine(i))
                    num++;
            } catch (FieldException ignored) {
            }
        }

        return num;
    }

    /**
     * Discover recursive all zero mines fields
     *
     * @param inx field index object
     */
    private void findUntilNoZeroField(Index inx) {

        try {
            if (gameModel.getInfoAboutMine(inx) || gameModel.fieldSelected(inx)) {
                //Field has mine or was selected earlier so end recursive
                return;
            }
        } catch (FieldException e) {
            // Field is boarder so end recursive
            return;
        }

        if (countMines(inx) > 0) {
            // Field is no mine but around have samo mine, so set field as selected and end recursive
            try {
                setFieldAsSelected(inx, countMines(inx));
            } catch (FieldException ignored) {
            }
            return;
        }

        try {
            // Field has no mine so set as 0, next call recursive to next fields
            setFieldAsSelected(inx, 0);
        } catch (FieldException ignored) {
        }

        int row = inx.getRowIndex();
        int col = inx.getColIndex();

        findUntilNoZeroField(new Index(row + 1, col - 1));
        findUntilNoZeroField(new Index(row + 1, col + 1));
        findUntilNoZeroField(new Index(row - 1, col - 1));
        findUntilNoZeroField(new Index(row - 1, col + 1));
        findUntilNoZeroField(new Index(row + 1, col));
        findUntilNoZeroField(new Index(row, col + 1));
        findUntilNoZeroField(new Index(row, col - 1));
        findUntilNoZeroField(new Index(row - 1, col));

    }

}
