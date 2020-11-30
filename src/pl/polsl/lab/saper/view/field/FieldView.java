package pl.polsl.lab.saper.view.field;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import pl.polsl.lab.saper.model.Index;

public class FieldView {

    private final StackPane field;
    private final Text text;
    private final Index inx;

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
        this.field.getChildren().addAll(bgField, text);
    }

    public StackPane getField() {
        return this.field;
    }

    public void setText(Character value) {
        this.text.setText(value.toString());
    }

    public Index getInx() {
        return this.inx;
    }
}
