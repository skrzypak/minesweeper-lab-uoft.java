package pl.polsl.lab.saper.view;

import javafx.scene.control.Alert;

/**
 * Interface that render error to screen
 *
 * @author Konrad Skrzypczyk
 * @version 2.0
 */
public interface IErrorView {

    /**
     * Show message error
     *
     * @param msg message in String format
     */
    default void err(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Detect error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
