/**
 * Before you implement your SparseIntMatrix class, you will need to write unit tests, using JUnit,
 * to ensure your MatrixEntry class is working as expected. You are required to implement at least
 * four unit tests, but are free to write more than that if you wish. Please write the tests in a java file
 * named MatrixEntryTest.java The unit tests need to assert the following features:
 *
 * ● The MatrixEntry constructor properly initializes the row, column, and data member
 * variables.
 * ● setRow(), setCol(), and setData() properly update the MatrixEntry’s row, column, and
 * data variables.
 * ● setNextColumn() updates the correct MatrixEntry reference variable, and
 * getNextColumn() returns the correct MatrixEntry reference variable.
 * ● setNextRow() updates the correct MatrixEntry reference variable, and getNextRow()
 * returns the correct MatrixEntry reference variable.
 *
 * You are free (and encouraged) to use existing JUnit tests we have provided you for this project
 * as well as previous projects and labs as a resource for creating your testing class.
 *
 * Hint: Because the row, col, data, nextRow, and nextCol variables in your MatrixEntry class must
 * be private, you will need to use your getter methods (i.e. getRow() and getCol()) to ensure the
 * appropriate setter methods are working).
 *
 */

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Random;

import static org.junit.Assert.*;

public class TestMatrixEntry {


    @Rule
    public Timeout timeout = Timeout.seconds(5);

    private MatrixEntry matrixEntry, nextRow, nextCol;
    private int row = 19;
    private int col = 33;
    private int data = 10;

    @Before
    public void setUp() {
        matrixEntry = new MatrixEntry(row, col, data);
        nextRow = new MatrixEntry(row+1, col, data);
        nextCol = new MatrixEntry(row, col+1, data);
    }

    @Test
    public void TestConstructor() {
        assertEquals(row, matrixEntry.getRow());
        assertEquals(col, matrixEntry.getColumn());
        assertEquals(data, matrixEntry.getData());
    }

    @Test
    public void TestSetters() {
        row = 8;
        col = 14;
        data = 2;
        matrixEntry.setRow(row);
        matrixEntry.setColumn(col);
        matrixEntry.setData(data);
        assertEquals(row, matrixEntry.getRow());
        assertEquals(col, matrixEntry.getColumn());
        assertEquals(data, matrixEntry.getData());

    }

    @Test
    public void TestNextRows() {
        matrixEntry.setNextRow(nextRow);
        assertEquals(nextRow, matrixEntry.getNextRow());
    }

    @Test
    public void TestNextCols() {
        matrixEntry.setNextCol(nextCol);
        assertEquals(nextCol, matrixEntry.getNextCol());
    }

}
