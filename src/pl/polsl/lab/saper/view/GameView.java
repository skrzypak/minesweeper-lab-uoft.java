package pl.polsl.lab.saper.view;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.polsl.lab.saper.controller.GameController;
import pl.polsl.lab.saper.model.GameBoard;
import pl.polsl.lab.saper.model.IEnumGame;
import pl.polsl.lab.saper.model.Index;
import pl.polsl.lab.saper.view.field.FieldView;

import java.util.ArrayList;

/**
 * Class responsible for render game view
 *
 * @author Konrad Skrzypczyk
 * @version 3.0
 */
public class GameView implements IErrorView {

    private final ArrayList<FieldView> boardView = new ArrayList<>();

    public void initializeView(VBox boardViewFXM, GameBoard gameBoard, GameController gameController) {

        boardViewFXM.getChildren().clear();

        Double fieldSize;
        if(gameBoard.getNumOfRows() > gameBoard.getNumOfCols()) {
            fieldSize = boardViewFXM.getMaxHeight() / (gameBoard.getNumOfRows() - 2);
        } else {
            fieldSize = boardViewFXM.getMaxWidth() / (gameBoard.getNumOfCols() - 2);
        }

        if(fieldSize < 10) fieldSize = 10.0;

        for (int i = 1; i < gameBoard.getNumOfRows() - 1; i++) {
            HBox row = new HBox();
            for (int j = 1; j < gameBoard.getNumOfCols() - 1; j++) {
                Index inx = new Index(i, j);
                FieldView f = new FieldView(inx, fieldSize);
                f.getField().setOnMouseClicked(event -> {
                    if(event.getButton() == MouseButton.PRIMARY) {
                        gameController.onMouseButtonPrimaryFieldClick(inx);
                        f.getField().setOnMouseClicked(null);
                    }
                    else if(event.getButton() == MouseButton.SECONDARY) {
                        gameController.onMouseButtonSecondaryFieldClick(inx);
                    }
                });
                this.boardView.add(f);
                row.getChildren().add(f.getField());
            }
            boardViewFXM.getChildren().add(row);
        }
    }

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


    public Character getSelectedFieldSymbol(Integer num) {
        return (Character) (char) (num + '0');
    }

    public Character getMarkedFieldSymbol() {
        return '*';
    }

    private Character getMineSymbol() {
        return 'M';
    }


    public Character getEmptyFieldSymbol() {
        return ' ';
    }

    private void renderResult(ArrayList<Index> minesIndex) {
        for (Index i: minesIndex) {
            this.boardView.stream()
                    .filter(fw -> fw.getInx().getRowIndex().equals(i.getRowIndex()))
                    .filter(fw -> fw.getInx().getColIndex().equals(i.getColIndex()))
                    .findFirst()
                    .map(fw-> {
                        fw.setText(getMineSymbol());
                        return fw;
                    });
        }
        for (FieldView fw: boardView) {
            fw.getField().setOnMouseClicked(null);
        }
    }

    /**
     * Method render game result
     * @param gameResult game result enum
     */
    public void showResult(IEnumGame.GameResult gameResult, ArrayList<Index> minesIndex) {
        if(gameResult == IEnumGame.GameResult.WIN) {
            System.out.print("YOU WIN :)\n");
        } else if(gameResult == IEnumGame.GameResult.LOSE) {
            System.out.print("YOU LOSE :(\n");
        } else if(gameResult == IEnumGame.GameResult.CANCELED){
            System.out.print("GAME CANCELED\n");
        }
        renderResult(minesIndex);
    }

}
