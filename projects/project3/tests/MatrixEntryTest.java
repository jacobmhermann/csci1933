

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Random;

import static org.junit.Assert.*;

public class MatrixEntryTest {


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
