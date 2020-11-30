package pl.polsl.lab.saper.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

/**
 * Class responsible for manage model and view game
 * @author Konrad Skrzypczyk
 * @version 1.0
 */
public class AppController {

    private GameController gameController;
    private final MenuController menuController;
    public TextField inputRoundValue;
    public VBox boardViewFXML;

    /**
     * Class constructor.
     */
    public AppController() {
        this.menuController = new MenuController();
        Pair<Integer, Integer> init = this.menuController.show();
        this.gameController = new GameController(init.getKey(), init.getValue());
    }

    @FXML
    void initialize(){
        gameController.initializeBoardView(boardViewFXML);
    }

    public void startNewGame() {
        gameController = null;
        Pair<Integer, Integer> init = this.menuController.show();
        this.gameController = new GameController(init.getKey(), init.getValue());
        gameController.initializeBoardView(boardViewFXML);
    }

    public void exit() {
        gameController = null;
        System.out.println("TODO::EXIT");
    }

    public void userManualInput() {
        gameController.playFromRawText(this.inputRoundValue.getText());
        this.inputRoundValue.setFocusTraversable(false);
        this.inputRoundValue.clear();
    }

}
