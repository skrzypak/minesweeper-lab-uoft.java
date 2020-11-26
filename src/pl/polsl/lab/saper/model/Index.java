package pl.polsl.lab.saper.model;

/**
 * Class define index of field
 *
 * @author Konrad Skrzypczyk
 * @version 1.0
 */
public class Index {
    private final Integer row;  // Contain index of row
    private final Integer col;  // Contain index of col

    /**
     * Class constructor.
     * @param row index of row
     * @param col index of col
     */
    public Index(Integer row, Integer col) {
        this.row = row;
        this.col = col;
    }

    public Integer getRowIndex(){
        return this.row;
    }

    public Integer getColIndex() {
        return this.col;
    }

}
