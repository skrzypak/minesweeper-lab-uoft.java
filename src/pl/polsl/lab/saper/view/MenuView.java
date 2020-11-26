package pl.polsl.lab.saper.view;

import java.util.Scanner;

/**
 * Class responsible for render menu view
 *
 * @author Konrad Skrzypczyk
 * @version 1.0
 */
public class MenuView implements IErrorView {
    private static final Scanner scan = new Scanner(System.in);

    /**
     * Class constructor.
     */
    public MenuView() {}

    /**
     * Method ask player about new board height
     * @return user input string
     */
    public String getNewBoardHeightFromUser() {
        System.out.print("Input number of rows: ");
        return scan.nextLine();
    }

    /**
     * Method ask player about new board width
     * @return user input string
     */
    public String getNewBoardWidthFromUser() {
        System.out.print("Input number of columns: ");
        return scan.nextLine();
    }

    /**
     * Method ask player, if start new game
     * @return user input string
     */
    public String askUserToStartNewGame() {
        System.out.print("Start new game? y/Y or n/N: ");
        return scan.nextLine();
    }

}
