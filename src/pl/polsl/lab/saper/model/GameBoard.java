package pl.polsl.lab.saper.model;

import pl.polsl.lab.saper.exception.FieldException;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Class contain game board data
 *
 * @author Konrad Skrzypczyk
 * @version 1.0
 */
public class GameBoard {

    ArrayList<Field> fields;                 // Game fields
    private final Integer numOfRows;         // Game board height, user input height + 2
    private final Integer numOfCols;         // Game board width, user input width + 2

    /**
     * Class constructor.
     * @param height new board height;
     * @param width  new board width;
     */
    public GameBoard(Integer height, Integer width) {
        this.fields = new ArrayList<>();
        this.numOfRows = height + 2;
        this.numOfCols = width + 2;
    }

    /**
     * Get num of board rows
     * @return num of rows
     */
    public Integer getNumOfRows() {
        return this.numOfRows;
    }

    /**
     * Get num of board columns
     * @return num of columns
     */
    public Integer getNumOfCols() {
        return this.numOfCols;
    }

    /**
     * Get field data from index
     * @param rowInx row index
     * @param colInx column index
     * @return field data if found, otherwise null
     */
    public Field get(Integer rowInx, Integer colInx) {
        Optional<Field> fieldOptional = this.fields.stream()
                .filter(f -> f.getRowIndex().equals(rowInx))
                .filter(f -> f.getColIndex().equals(colInx))
                .findFirst();
        return fieldOptional.orElse(null);
    }

    /**
     * Generate new empty field with index and putEmptyField to board
     * @param rowInx row index of field
     * @param colInx column index of field
     * @throws FieldException if field value is game board border or out of range
     */
    public void putEmptyField(Integer rowInx, Integer colInx) throws FieldException {
        if(rowInx < 0 || rowInx >= this.numOfRows) throw new FieldException("Row index out of range of board height");
        if(colInx < 0 || colInx >= this.numOfCols) throw new FieldException("Column index out of range of board  width");
        Optional<Field> optional = this.fields.stream()
                .filter(f -> f.getRowIndex().equals(rowInx))
                .filter(f -> f.getColIndex().equals(colInx))
                .findFirst();
        if(optional.isPresent()) {
            throw new FieldException("Field with this index exists");
        }
        this.fields.add(new Field(rowInx, colInx));
    }

    /**
     * Returning raw board fields
     * @return fields ArrayList
     */
    public ArrayList<Field> raw() {
        return this.fields;
    }
}
