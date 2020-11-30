package pl.polsl.lab.saper.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pl.polsl.lab.saper.model.Dimensions;

import java.util.Optional;

/**
 * Class responsible for manage gui
 *
 * @author Konrad Skrzypczyk
 * @version 1.0
 */
public class AppController {

    private final MenuController menuController;    // Menu controller
    /**
     * The Input round value.
     */
    public TextField inputRoundValue;               // Input from UI
    /**
     * The Board view fxml.
     */
    public VBox boardViewFXML;                      // Container that contains fields
    private GameController gameController;          // Game controller

    /**
     * Class constructor.
     */
    public AppController() {
        this.menuController = new MenuController();
        Optional<Dimensions> init = this.menuController.show();
        init.ifPresent(dimensions -> this.gameController =
                new GameController(dimensions.getHeight(), dimensions.getWidth()));
    }

    /**
     * Render board
     */
    @FXML
    void initialize() {
        if (gameController != null) {
            inputRoundValue.setDisable(false);
            gameController.initializeBoardView(boardViewFXML);
        } else {
            inputRoundValue.setDisable(true);
        }
    }

    /**
     * Start new game after btn click
     */
    public void startNewGame() {
        gameController = null;
        Optional<Dimensions> init = this.menuController.show();
        if (init.isPresent()) {
            inputRoundValue.setDisable(false);
            this.gameController = new GameController(init.get().getHeight(), init.get().getWidth());
            gameController.initializeBoardView(boardViewFXML);
        } else {
            inputRoundValue.setDisable(true);
        }
    }

    /**
     * Method exit app
     */
    public void exit() {
        gameController = null;
        Platform.exit();
    }

    /**
     * Method update input value
     */
    public void userManualInput() {
        gameController.playFromRawText(this.inputRoundValue.getText());
        this.inputRoundValue.setFocusTraversable(false);
        this.inputRoundValue.clear();
    }

}
