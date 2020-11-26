package pl.polsl.lab.saper;

import pl.polsl.lab.saper.controller.GameController;
import pl.polsl.lab.saper.controller.MenuController;

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
        primaryStage.setScene(new Scene(root, 650, 450));
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

//        Integer h = 0, w = 0;
//        boolean argsCorrect = true;
//
//        // Read data from args
//        if(args.length == 4) {
//            for(int i = 0; i <= 2; i += 2) {
//                try {
//                    if (args[i].equals("-m")) {
//                        h = Integer.parseInt(args[i + 1]);
//                        if(h <= 0) {
//                            argsCorrect = false;
//                            break;
//                        }
//                    } else if (args[i].equals("-n")) {
//                        w = Integer.parseInt(args[i + 1]);
//                        if(w <= 0) {
//                            argsCorrect = false;
//                            break;
//                        }
//                    } else {
//                        argsCorrect = false;
//                        break;
//                    }
//                } catch (NumberFormatException e) {
//                    argsCorrect = false;
//                    break;
//                }
//            }
//        } else argsCorrect = false;
//
//        MenuController menuController = new MenuController();
//
//
//        // If args was wrong - read start value from player
//        if(!argsCorrect){
//            h = menuController.getNewBoardHeight();
//            w = menuController.getNewBoardWidth();
//        }
//
//        GameController gameController = null;
//
//        try {
//            gameController = new GameController(h, w);
//        } catch (OutOfMemoryError e) {
//            menuController.err("Board is too big ...");
//        }
//
//
//        // Start new game
//        do {
//
//            if(gameController == null) {
//                if(menuController.checkIfStartNewGame()) {
//                    h = menuController.getNewBoardHeight();
//                    w = menuController.getNewBoardWidth();
//
//                    try {
//                        gameController = new GameController(h, w);
//                    } catch (OutOfMemoryError e) {
//                        menuController.err("Board is too big ...");
//                        continue;
//                    }
//
//                } else break;
//            }
//
//            while (gameController.isGameRunning()) {
//                gameController.playRound();
//            }
//
//            gameController = null;
//
//        } while (true);

    }
}
