package pl.polsl.lab.saper.controller;

//import pl.polsl.lab.saper.model.Menu;
import pl.polsl.lab.saper.model.Settings;
import pl.polsl.lab.saper.view.MenuView;

/**
 * Game menu controller class
 *
 * @author Konrad Skrzypczyk
 * @version 1.0
 */
public class MenuController {

    private final Settings settings;    //Settings model object handler
    private final MenuView menuView;    //Menu view object handler

    /**
     * Class constructor.
     */
    public MenuController() {
        settings = new Settings();
        menuView = new MenuView();
    }

    /**
     * Method responsible to get height board value from user
     * @return board height
     */
    public Integer getNewBoardHeight() {
        Integer val;
        String inp;
        do {
            inp = menuView.getNewBoardHeightFromUser();
            val = parse(inp);
        } while(val == null || val <= 0);
        settings.setLastBoardHeightInput(val);
        return val;
    }

    /**
     * Method responsible to get width board value from user
     * @return board width
     */
    public Integer getNewBoardWidth() {
        Integer val;
        String inp;
        do {
            inp = menuView.getNewBoardWidthFromUser();
            val = parse(inp);
        } while(val == null || val <= 0);
        settings.setLastBoardWidthInput(val);
        return val;
    }

    /**
     * Method control if create new game
     * @return true if user want start new game, otherwise false
     */
    public boolean checkIfStartNewGame() {
        String sign;
        do {
            sign = menuView.askUserToStartNewGame();
            if(sign.equals("n") || sign.equals("N")) return false;
            if(sign.equals("y") || sign.equals("Y")) return true;
        } while (true);
    }

    /**
     * Method parse String to Integer
     * @param s number in String format
     * @return number in integer if no err or null
     */
    private Integer parse(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Function display error message
     * @param msg the msg
     */
    public void err(String msg) {
        menuView.err(msg);
    }
}
