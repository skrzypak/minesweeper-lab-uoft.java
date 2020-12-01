package pl.polsl.lab.saper.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import pl.polsl.lab.saper.model.Dimensions;
import pl.polsl.lab.saper.model.IEnumGame;

import java.util.Optional;

/**
 * Class responsible for manage gui
 *
 * @author Konrad Skrzypczyk
 * @version 1.0
 */
public class AppController {

    private final MenuController menuController;    // Menu controller
    public TextField inputRoundValue;               // Input from UI
    public VBox boardViewFXML;                      // Container that contains fields
    public TreeView<String> treeView;               // Display app running game results
    private GameController gameController;          // Game controller

    /**
     * Class constructor.
     */
    public AppController() {
        this.menuController = new MenuController();
        Optional<Dimensions> init = this.menuController.show();
        init.ifPresent(dimensions -> this.gameController =
                new GameController(this, dimensions.getHeight(), dimensions.getWidth()));

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
            this.gameController = new GameController(this, init.get().getHeight(), init.get().getWidth());
            gameController.initializeBoardView(boardViewFXML);
        } else {
            inputRoundValue.setDisable(true);
        }
    }

    /**
     * Method update tree view result
     * @param gameResult game result
     */
    public void updateTreeResult(IEnumGame.GameResult gameResult) {
        if(treeView.getRoot() == null) {
            TreeItem<String> rootItem = new TreeItem<>("RESULTS");
            treeView.setRoot(rootItem);
        }
        TreeItem<String> rootItem = treeView.getRoot();
        String val = gameResult.toString();
        val += " : " + java.util.Calendar.getInstance().getTime().toString();
        rootItem.getChildren().add(new TreeItem<>(val));
        treeView.setRoot(rootItem);
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
