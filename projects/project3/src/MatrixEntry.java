/*
 *
 * This class represents a single entry in a linked-list matrix.
 *
 * Jacob Hermann, 10-29-18
 * herma655@umn.edu
 *
 */

public class MatrixEntry {

    private int row, col, data;
    private MatrixEntry nextRow, nextCol;

    public MatrixEntry(int row, int col, int data) {
        this.row = row;
        this.col = col;
        this.data = data;
    }

    // accessor methods
    public int getRow() { return row; }

    public int getColumn() { return col; }

    public int getData() { return data; }

    public MatrixEntry getNextRow() { return nextRow; }

    public MatrixEntry getNextCol() { return nextCol; }

    // mutator methods
    public void setRow(int row) { this.row = row; }

    public void setColumn(int col) { this.col = col; }

    public void setData(int data) { this.data = data; }

    public void setNextRow(MatrixEntry el) { this.nextRow = el; }

    public void setNextCol(MatrixEntry el) { this.nextCol = el; }

} // end MatrixEntry
