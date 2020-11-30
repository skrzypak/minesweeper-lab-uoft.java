package pl.polsl.lab.saper.controller;

import pl.polsl.lab.saper.model.Dimensions;
import pl.polsl.lab.saper.model.Settings;
import pl.polsl.lab.saper.view.MenuDialogView;

import java.util.Optional;

/**
 * Game menu controller class
 *
 * @author Konrad Skrzypczyk
 * @version 1.0
 */
public class MenuController {

    private final Settings settings;    //Settings model object handler
    private final MenuDialogView menuDialogView;    //Menu view object handler

    /**
     * Class constructor.
     */
    public MenuController() {
        settings = new Settings();
        menuDialogView = new MenuDialogView();
    }

    /**
     * Show menu dialog and get value from user
     *
     * @return the optional
     */
    public Optional<Dimensions> show() {

        Optional<Dimensions> data = menuDialogView.show("", new Dimensions(
                settings.getLastBoardHeightInput(),
                settings.getLastBoardWidthInput()
        ));

        if (data.isPresent()) {
            settings.setLastBoardHeightInput(data.get().getHeight());
            settings.setLastBoardWidthInput(data.get().getWidth());
        }

        return data;
    }

    /**
     * Function display error message
     *
     * @param msg the msg
     */
    public void err(String msg) {
        menuDialogView.err(msg);
    }
}
