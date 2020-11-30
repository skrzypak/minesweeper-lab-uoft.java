package pl.polsl.lab.saper.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pl.polsl.lab.saper.model.Dimensions;

import java.util.Optional;

/**
 * Class responsible for show dialog and get height, width new board value from user
 *
 * @author Konrad Skrzypczyk
 * @version 1.0
 */
public class MenuDialogView implements IErrorView {

    /**
     * Show dialog alert
     *
     * @param msg  additional message
     * @param prev previous board dimensions
     * @return user input value as linked list with pair ('H' and 'W')
     */
    public Optional<Dimensions> show(String msg, Dimensions prev) {

        javafx.scene.control.Dialog<Dimensions> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("Start new game");
        dialog.setHeaderText(msg);

        ButtonType playBtnType = new ButtonType("Play", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(playBtnType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ChoiceBox<Integer> heightChoice = new ChoiceBox<>();
        ChoiceBox<Integer> widthChoice = new ChoiceBox<>();
        for (int i = 1; i < 21; i++) {
            heightChoice.getItems().add(i);
            widthChoice.getItems().add(i);
        }

        if (prev.getHeight() != null && prev.getWidth() != null) {
            heightChoice.setValue(prev.getHeight());
            widthChoice.setValue(prev.getWidth());
        } else {
            heightChoice.setValue(1);
            widthChoice.setValue(1);
        }


        grid.add(new Label("Height:"), 0, 0);
        grid.add(heightChoice, 1, 0);
        grid.add(new Label("Width:"), 0, 1);
        grid.add(widthChoice, 1, 1);

        Node playBtn = dialog.getDialogPane().lookupButton(playBtnType);
        playBtn.setDisable(false);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == playBtnType) {
                return new Dimensions(heightChoice.getValue(), widthChoice.getValue());
            }
            return null;
        });

        return dialog.showAndWait();
    }
}
