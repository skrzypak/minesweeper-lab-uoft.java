package pl.polsl.lab.saper.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class MenuView {

    public Optional<Pair<String, String>> show(String msg) {
        javafx.scene.control.Dialog<Pair<String, String>> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("Start new game");

        // TODO
        System.out.println(msg);

        ButtonType playBtnType = new ButtonType("Play", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(playBtnType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField height = new TextField();
        height.setPromptText("Type new board height");
        TextField width = new TextField();
        width.setPromptText("Type new board width");

        grid.add(new Label("Height:"), 0, 0);
        grid.add(height, 1, 0);
        grid.add(new Label("Width:"), 0, 1);
        grid.add(width, 1, 1);

        Node playBtn = dialog.getDialogPane().lookupButton(playBtnType);
        playBtn.setDisable(true);

        AtomicReference<Boolean> heightEmpty = new AtomicReference<>(true);
        AtomicReference<Boolean> widthEmpty = new AtomicReference<>(true);

        height.textProperty().addListener((observable, oldValue, newValue) -> {
            heightEmpty.set(newValue.trim().isEmpty());
            playBtn.setDisable(heightEmpty.get() || widthEmpty.get());
        });

        width.textProperty().addListener((observable, oldValue, newValue) -> {
            widthEmpty.set(newValue.trim().isEmpty());
            playBtn.setDisable(heightEmpty.get() || widthEmpty.get());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(height::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == playBtnType) {
                return new Pair<>(height.getText().trim(), width.getText().trim());
            }
            return null;
        });

        return dialog.showAndWait();
    }
}
