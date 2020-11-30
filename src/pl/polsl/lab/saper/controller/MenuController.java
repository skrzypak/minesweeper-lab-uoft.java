package pl.polsl.lab.saper.controller;

import javafx.util.Pair;
import pl.polsl.lab.saper.model.Settings;
import pl.polsl.lab.saper.view.MenuView;

import java.util.Optional;

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

    public Pair<Integer, Integer> show() {
        Integer height = null;
        Integer width = null;

        Optional<Pair<String, String>> data = menuView.show("");
        if(data.isPresent()) {
            height = parse(data.get().getKey());
            width = parse(data.get().getValue());
        }

        while(height == null || width == null) {
            data = menuView.show("Wrong input data ...");
            if(data.isPresent()) {
                height = parse(data.get().getKey());
                width = parse(data.get().getValue());
            }
        }

        settings.setLastBoardHeightInput(height);
        settings.setLastBoardWidthInput(width);

        return new Pair<>(height, width);
    }

    /**
     * Method parse String to Integer
     * @param s number in String format
     * @return number if is in correct format and above 0, otherwise null
     */
    private Integer parse(String s) {
        try {
            int val = Integer.parseInt(s);
            if(val > 0)
                return val;
            else return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Function display error message
     * @param msg the msg
     */
    public void err(String msg) {
        System.out.println(msg);
    }
}
