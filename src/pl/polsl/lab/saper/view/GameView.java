package pl.polsl.lab.saper.view;

import pl.polsl.lab.saper.model.IEnumGame;
import pl.polsl.lab.saper.model.Field;
import pl.polsl.lab.saper.model.GameBoard;

import java.util.Scanner;

/**
 * Class responsible for render game view
 *
 * @author Konrad Skrzypczyk
 * @version 2.0
 */
public class GameView implements IErrorView {

    private final String prompt;                            // Sentence which display in each round
    private final Scanner scan = new Scanner(System.in);    // Scanner object

    /**
     * Class constructor.
     * @param height board height
     * @param width  board width
     */
    public GameView(Integer height, Integer width) {
        this.prompt = "E (END) or [*, o] [row index (1-"+height+")] "+"[column index (1-"+width+")]: ";
        startInfoRender();
    }

    /**
     * Get input from user
     * @return input from user in String format
     */
    public String getInputFromUser() {
        System.out.print(this.prompt);
        return scan.nextLine();
    }

    /**
     * Render game board in console
     * @param gameBoard    - game data board
     * @param showMines - if true display field with mine as 'M'
     */
    public void renderBoard(GameBoard gameBoard, boolean showMines) {

        System.out.flush();

        int fWidthCol =  Integer.toString(gameBoard.getNumOfCols() - 2).length() + 1;

        // Set width of content fields
        String formatC = "%"+fWidthCol+"s";
        fWidthCol++;
        String formatCN = "%"+fWidthCol+"s";

        // Set width of left row index
        int fWidthRowInx =  Integer.toString(gameBoard.getNumOfRows() - 2).length() + 2;
        String formatRN = "%"+fWidthRowInx+"s";

        // Left corner print '_____ ... _|'
        for(int i = 1; i < fWidthRowInx; i++)
            System.out.print("_");
        System.out.print("|");

        // Print column index num
        for (int i = 1; i < gameBoard.getNumOfCols() - 1; i++) {
            System.out.printf(formatCN, "C" + i + "|");
        }
        System.out.print("\n");

        // Print row index and fields content
        for (int i = 1; i < gameBoard.getNumOfRows() - 1; i++) {
            System.out.printf(formatRN, "R"+i+"|");
            for (int j = 1; j < gameBoard.getNumOfCols() - 1; j++) {
                if(!showMines) {
                    System.out.printf(formatC, getFieldSymbol(gameBoard.get(i, j)));
                } else {
                    System.out.printf(formatC, getFieldSymbolAndShowMines(gameBoard.get(i, j)));
                }
                System.out.print('|');
            }
            System.out.print("\n");
        }
    }

    /**
     * Get view field symbol during game
     * @param f field
     * @return symbol of field
     * */
    private Character getFieldSymbol(Field f) {
        if(f.isSelected()){
            return (Character) (char) (f.getNumOfMinesAroundField() + '0');
        } else if(f.isMarked()) return '*';
        else {
            return '#';
        }
    }

    /**
     * Get field symbol and show mines
     * @param f field
     * @return symbol of field
     * */
    private Character getFieldSymbolAndShowMines(Field f) {
        if(f.isMine()) return 'M';
        return getFieldSymbol(f);
    }

    /**
     * Method render game result
     * @param gameBoard game board array
     * @param gameResult game result enum
     */
    public void renderGameResult(GameBoard gameBoard, IEnumGame.GameResult gameResult) {
        System.out.println("################################################");
        System.out.print("GAME STATE ___ ");
        if(gameResult == IEnumGame.GameResult.WIN) {
            System.out.print("YOU WIN :)\n");
        } else if(gameResult == IEnumGame.GameResult.LOSE) {
            System.out.print("YOU LOSE :(\n");
        } else if(gameResult == IEnumGame.GameResult.CANCELED){
            System.out.print("GAME CANCELED\n");
        }

        System.out.println("################################################");

        this.renderBoard(gameBoard, true);

        System.out.println("################################################");
    }

    /**
     * Render new game header
     */
    private void startInfoRender() {
        System.out.println("################################################");
        System.out.println("NEW GAME START");
        System.out.println("################################################");
    }

}
