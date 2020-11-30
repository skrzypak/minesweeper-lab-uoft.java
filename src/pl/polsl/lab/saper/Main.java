package pl.polsl.lab.saper;

//import pl.polsl.lab.saper.controller.GameController;
//import pl.polsl.lab.saper.controller.MenuController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Saper game main class
 *
 * @author Konrad Skrzypczyk
 * @version 1.0
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(root, 650, 650));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Main function (arguments are optionals)
     * Arguments:
     * [-m Integer] board game height
     * [-n Integer] board game width;
     * @param args program arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
