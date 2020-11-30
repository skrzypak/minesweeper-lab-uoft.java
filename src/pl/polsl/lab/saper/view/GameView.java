package pl.polsl.lab.saper.view;

import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import pl.polsl.lab.saper.controller.GameController;
import pl.polsl.lab.saper.model.GameBoard;
import pl.polsl.lab.saper.model.IEnumGame;
import pl.polsl.lab.saper.model.Index;

import java.util.ArrayList;

/**
 * Class responsible for render game view
 *
 * @author Konrad Skrzypczyk
 * @version 3.0
 */
public class GameView implements IErrorView {

    private final ArrayList<FieldView> boardView = new ArrayList<>(); // Contain references to fields

    /**
     * Method initialize game board view
     *
     * @param boardViewFXML  main container from .fxml file
     * @param gameBoard      game baord data ref
     * @param gameController game container ref
     */
    public void initializeView(VBox boardViewFXML, GameBoard gameBoard, GameController gameController) {

        boardViewFXML.getChildren().clear();

        Double fieldSize;
        if (gameBoard.getNumOfRows() > gameBoard.getNumOfCols()) {
            fieldSize = boardViewFXML.getMaxHeight() / (gameBoard.getNumOfRows() - 2);
        } else {
            fieldSize = boardViewFXML.getMaxWidth() / (gameBoard.getNumOfCols() - 2);
        }

        if (fieldSize < 10) fieldSize = 10.0;

        for (int i = 1; i < gameBoard.getNumOfRows() - 1; i++) {
            HBox row = new HBox();
            for (int j = 1; j < gameBoard.getNumOfCols() - 1; j++) {
                Index inx = new Index(i, j);
                FieldView f = new FieldView(inx, fieldSize);
                f.getField().setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        gameController.onMouseButtonPrimaryFieldClick(inx);
                        f.getField().setOnMouseClicked(null);
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        gameController.onMouseButtonSecondaryFieldClick(inx);
                    }
                });
                this.boardView.add(f);
                row.getChildren().add(f.getField());
            }
            boardViewFXML.getChildren().add(row);
        }
    }

    /**
     * Method update field view
     *
     * @param inx index of field
     * @param sym new field text
     */
    public void updateFieldView(Index inx, Character sym) {
        this.boardView.stream()
                .filter(f -> f.getInx().getRowIndex().equals(inx.getRowIndex()))
                .filter(f -> f.getInx().getColIndex().equals(inx.getColIndex()))
                .findFirst()
                .map(f -> {
                    f.setText(sym);
                    return f;
                });
    }

    /**
     * Get field symbol used in game
     *
     * @param num number of mines
     * @return symbol selected field symbol
     */
    public Character getSelectedFieldSymbol(Integer num) {
        return (Character) (char) (num + '0');
    }

    /**
     * Get marked field symbol
     *
     * @return symbol marked field symbol
     */
    public Character getMarkedFieldSymbol() {
        return '*';
    }

    /**
     * Get mine filed symol
     *
     * @return symbol
     */
    private Character getMineSymbol() {
        return 'M';
    }

    /**
     * Get symbol when field is not selected or marked
     *
     * @return symbol empty field symbol
     */
    public Character getEmptyFieldSymbol() {
        return ' ';
    }

    /**
     * Show game result and render mines
     *
     * @param minesIndex all field index which contain mine
     */
    private void renderResult(ArrayList<Index> minesIndex) {
        for (Index i : minesIndex) {
            this.boardView.stream()
                    .filter(fw -> fw.getInx().getRowIndex().equals(i.getRowIndex()))
                    .filter(fw -> fw.getInx().getColIndex().equals(i.getColIndex()))
                    .findFirst()
                    .map(fw -> {
                        fw.setText(getMineSymbol());
                        return fw;
                    });
        }
        for (FieldView fw : boardView) {
            fw.getField().setOnMouseClicked(null);
        }
    }

    /**
     * Method render game result
     *
     * @param gameResult game result enum
     * @param minesIndex the mines index
     */
    public void showResult(IEnumGame.GameResult gameResult, ArrayList<Index> minesIndex) {
        renderResult(minesIndex);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Game End");
        alert.setHeaderText("Result");
        if (gameResult == IEnumGame.GameResult.WIN) {
            alert.setContentText("YOU WIN :)\n");
        } else if (gameResult == IEnumGame.GameResult.LOSE) {
            alert.setContentText("YOU LOSE :(\n");
        } else {
            alert.setContentText("GAME CANCELED\n");
        }
        alert.showAndWait();
    }

}
