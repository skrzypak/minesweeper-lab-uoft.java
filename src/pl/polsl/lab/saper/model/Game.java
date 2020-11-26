package pl.polsl.lab.saper.model;

import pl.polsl.lab.saper.exception.FieldException;

/**
 * Class contain game data
 *
 * @author Konrad Skrzypczyk
 * @version 2.0
 */
public class Game {

    private GameBoard gameBoard;            // Contains game fields
    private Integer freeFieldCounter;       // Number of no selected field by player with no mine, if 0 player win
    private Boolean running;                // Game state (1) game running, otherwise 0
    private IEnumGame.GameResult gameResult; // Contain info about player result, used to display correct result

    /**
     * Class constructor.
     * @param height new board height;
     * @param width  new board width;
     * @throws OutOfMemoryError when board is to big
     */
    public Game(Integer height, Integer width) {
        this.gameResult = IEnumGame.GameResult.NONE;
        this.gameBoard = new GameBoard(height, width);
        this.freeFieldCounter = height * width;
        try {
            createBoard(height, width);
        } catch (OutOfMemoryError e) {
            throw new OutOfMemoryError(e.getMessage());
        }
        this.running = true;
    }

    /**
     * Method initialize empty new board game
     * @param height new board height;
     * @param width new board width;
     * */
    private void createBoard(Integer height, Integer width) {

        this.gameBoard = new GameBoard(height, width);

        // Fill all fields as empty
        for (int i = 0; i < this.gameBoard.getNumOfRows(); i++) {
            for (int j = 0; j < this.gameBoard.getNumOfCols(); j++) {
                try {
                    this.gameBoard.putEmptyField(i, j);
                } catch (FieldException ignored) { }
            }
        }
    }

    /**
     * Return game board
     * @return game board array
     */
    public GameBoard getBoardData() {
        return this.gameBoard;
    }

    /**
     * Get game status
     * @return Game end (0), Game running (1)
     */
    public Boolean getRunning() {
        return this.running;
    }

    /**
     * Method set filed as mark
     * @param rowInx filed row index
     * @param colInx field column index
     * @throws FieldException if field value is game board border or out of range
     */
    public void setFieldAsMark(Integer rowInx, Integer colInx) throws FieldException {
        isCorrectField(rowInx, colInx);
        this.gameBoard.get(rowInx, colInx).setFieldAsMark();
    }

    /**
     * Method set filed as selected by player
     * @param num number of mine around field
     * @param rowInx filed row index
     * @param colInx field column index
     * @throws FieldException if field value is game board border or out of range
     */
    public void setFieldAsSelected(Integer num, Integer rowInx, Integer colInx) throws FieldException {

        if(num < 0 || num > 8) throw new FieldException("Invalid number of mine");
        isCorrectField(rowInx, colInx);

        if(this.gameBoard.get(rowInx, colInx).setFieldAsSelected(num))
            this.freeFieldCounter--;
    }

    /**
     * Method return number of game board rows
     * @return number of rows
     */
    public Integer getNumOfRows() {
        return this.gameBoard.getNumOfRows();
    }

    /**
     * Method return number of game board columns
     * @return number of columns
     */
    public Integer getNumOfCols() {
        return this.gameBoard.getNumOfCols();
    }

    /**
     * Method return info about is field a mine
     * @param rowInx index of row field
     * @param colInx index of column field
     * @return true if is mine, otherwise false
     * @throws FieldException if field value is game board border or out of range
     */
    public boolean getInfoAboutMine(Integer rowInx, Integer colInx) throws FieldException {
        isCorrectField(rowInx, colInx);
        return this.gameBoard.get(rowInx, colInx).isMine();
    }

    /**
     * Method stop game
     */
    private void endGame() {
        this.running = false;
    }

    /**
     * Method set game as loses
     */
    public void setLose() {
        this.gameResult = IEnumGame.GameResult.LOSE;
        endGame();
    }

    /**
     * Method set game as winner
     */
    public void setWin() {
        this.gameResult = IEnumGame.GameResult.WIN;
        endGame();
    }

    /**
     * Method set game as canceled
     */
    public void setCancel() {
        this.gameResult = IEnumGame.GameResult.CANCELED;
        endGame();
    }

    /**
     * Method return game result
     * @return LOSE (player lose) ,WIN (player wind), NONE (currently no result)
     */
    public IEnumGame.GameResult getGameResult() {
        return this.gameResult;
    }

    /**
     * Method return number of non selected fields by user
     * @return number of non selected fields
     */
    public int getFreeFieldCounter() {
        return this.freeFieldCounter;
    }

    /**
     * Method set number of non selected fields by user
     * @param num new number
     */
    public void setFreeFieldCounter(Integer num) {
        this.freeFieldCounter = num;
    }

    /**
     * Method return info about field was selected earlier by player
     * @param rowInx the row
     * @param colInx the col
     * @return true if was selected, otherwise false
     * @throws FieldException if field value is game board border or out of range
     */
    public boolean fieldSelected(Integer rowInx, Integer colInx) throws FieldException {
        isCorrectField(rowInx, colInx);
        return this.gameBoard.get(rowInx, colInx).isSelected();
    }

    /**
     * Method check that field is correct
     * @param rowInx the row
     * @param colInx the col
     * @throws FieldException if field value is game board border or out of range
     */
    public void isCorrectField(Integer rowInx, Integer colInx) throws FieldException {
        if(rowInx <= 0 || rowInx > getNumOfRows() - 2) throw new FieldException("Invalid row index");
        if(colInx <= 0 || colInx > getNumOfCols() - 2) throw new FieldException("Invalid column index");
    }
}

