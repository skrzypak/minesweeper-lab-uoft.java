package pl.polsl.lab.saper.view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import pl.polsl.lab.saper.model.Index;

/**
 * Class contain field view state
 *
 * @author Konrad Skrzypczyk
 * @version 1.0
 */
public class FieldView {

    private final StackPane field;  //Contain whole rectangle and text
    private final Text text;        //Text that show field state if " " field not selected
    private final Index inx;        //Field index object

    /**
     * Class constructor
     *
     * @param inx  field index from model
     * @param size field size view
     */
    public FieldView(Index inx, Double size) {
        this.inx = inx;
        this.field = new StackPane();
        Rectangle bgField = new Rectangle();
        bgField.setFill(Paint.valueOf("DODGERBLUE"));
        bgField.setHeight(size);
        bgField.setStroke(Paint.valueOf("BLACK"));
        bgField.setStrokeType(StrokeType.INSIDE);
        bgField.setStrokeWidth(2);
        bgField.setWidth(size);
        this.text = new Text("");
        this.text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, size / 2));
        this.field.getChildren().addAll(bgField, text);
    }

    /**
     * Get field
     *
     * @return stack pane object
     */
    public StackPane getField() {
        return this.field;
    }

    /**
     * Set field text
     *
     * @param value Character
     */
    public void setText(Character value) {
        this.text.setText(value.toString());
    }

    /**
     * Get field view index
     *
     * @return inx index field object
     */
    public Index getInx() {
        return this.inx;
    }
}
