/*
 * This class creates a sparse matrix using individual MatrixEntry objects and a linked-list format.
 * It also allows two matrices to be added or subtracted.
 *
 * Jacob Hermann, 10-29-18
 * herma655@umn.edu
 *
 * Answers to Part V:
 *  a)  Every matrix entry requires 5 units of memory, with the exception of the last link in the row or column.
 *      For each of these, one fewer memory unit is required, since they have no link to a later entry. Thus,
 *      the total amount of storage for implementing an NxN matrix with m entries using the SparseIntMatrix format
 *      is 5*m - 2N.
 *
 *      An NxN matrix with have N^2 entries. Since each entry uses 1 unit of memory, an NxN matrix would need
 *      N^2 units of memory when using a 2D Array implementation.
 *
 *  b)  Plugging in the values of N=100,000 and m=1,000,000 above, we find that the SparseIntMatrix implementation
 *      requires 5,000,000-200,000 = 4,800,000 units of memory. The 2D Array implementation of the same matrix
 *      requires (100,000)^2 = 10,000,000,000 units of memory. Thus, the 2D Array implementation requires 2,000x
 *      more space than the SparseIntMatrix implementation.
 *
 *      In order to determine whether the SparseIntMatrix or the 2D Array will be more efficient for any values
 *      of m and N, it helps to solve the equation: 5m - 2N < N^2. If this comparison is true, then the
 *      SparseIntMatrix implementation will be more space-efficient. This equation can be simplified to
 *      m < (N^2 + 2N) / 5. If this relationship between m and N is true, then the SparseIntMatrix implementation
 *      is more efficient.
 *
 *      For the example shown above, the SparseIntMatrix implementation is more efficient for any values of m
 *      less than 2,000,040,000. A more simple way to describe this relationship is to say that the
 *      SparseIntMatrix is more efficient when the matrix is less than 1/5 full. Otherwise, the 2D Array is more
 *      efficient.
 *
 */

import java.io.*;
import java.util.*;

public class SparseIntMatrix {

    private int ROWS, COLUMNS;
    private MatrixEntry[] firstInColumn,firstInRow;

    public SparseIntMatrix(int rows, int cols) {
        ROWS = rows;
        COLUMNS = cols;
        firstInColumn = new MatrixEntry[COLUMNS];
        firstInRow = new MatrixEntry[ROWS];
    } // end constructor

    public SparseIntMatrix(int rows, int cols, String inputFile) {
        this(rows, cols);
        MatrixEntry[] matrixEntries = readFile(inputFile);
        arrayToLinkedList(matrixEntries);
    } // end constructor


    public int getElement(int row, int col) {
        int data = 0;
        MatrixEntry entry = firstInColumn[col];
        while (entry != null && entry.getRow() <= row) {
            if (entry.getRow() == row) {
                data = entry.getData();
                entry = entry.getNextRow();
            } else {
                entry = entry.getNextRow();
            }
        }
        return data;
    } // end getElement

    public boolean setElement(int row, int col, int data) {
        boolean successCol, successRow;
        successCol = setColumnElement(row, col, data);
        successRow = setRowElement(row, col, data);
        if (successCol && successRow) {
            return true;
        } else {
            return false;
        }
    } // end setElement

    public boolean setColumnElement(int row, int col, int data) {
        boolean hasNext;
        MatrixEntry newEntry = new MatrixEntry(row, col, data);
        MatrixEntry entry = firstInColumn[col];
        MatrixEntry last = null;

        if (row >= ROWS && col >= COLUMNS) {
            return false;
        } else if (entry == null) {
            firstInColumn[col] = newEntry;
            return true;
        } else {
            try {
                entry.getNextRow();
                hasNext = true;
            } catch (NullPointerException e) {
                hasNext = false;
            }
            while (hasNext && entry.getRow() <= row) {
                if (entry.getRow() == row) {
                    entry.setData(data);
                    return true;
                } else {
                    last = entry;
                    entry = entry.getNextRow();
                    try {
                        entry.getNextRow();
                    } catch (NullPointerException e) {
                        hasNext = false;
                    }
                }
            } // end while

            if (hasNext) {
                last.setNextRow(newEntry);
                newEntry.setNextRow(entry);
            } else {
                last.setNextRow(newEntry);
            }
            return true;
        } // end if/else
    } // end setColumnElement

    public boolean setRowElement(int row, int col, int data) {
        boolean hasNext;
        MatrixEntry newEntry = new MatrixEntry(row, col, data);
        MatrixEntry entry = firstInRow[row];
        MatrixEntry last = null;

        if (row >= ROWS && col >= COLUMNS) {
            return false;
        } else if (entry == null) {
            firstInRow[row] = newEntry;
            return true;
        } else {
            try {
                entry.getNextCol();
                hasNext = true;
            } catch (NullPointerException e) {
                hasNext = false;
            }
            while (hasNext && entry.getColumn() <= col) {
                if (entry.getColumn() == col) {
                    entry.setData(data);
                    return true;
                } else {
                    last = entry;
                    entry = entry.getNextCol();
                    try {
                        entry.getNextCol();
                    } catch (NullPointerException e) {
                        hasNext = false;
                    }
                }
            } // end while

            if (hasNext) {
                last.setNextCol(newEntry);
                newEntry.setNextCol(entry);
            } else {
                last.setNextCol(newEntry);
            }
            return true;
        } // end if/else
    } // end setRowElement

    public boolean removeElement(int row, int col, int data) {
        boolean success = false;
        MatrixEntry entry = firstInColumn[col];
        MatrixEntry last = null;

        while (entry.getRow() <= row) {
            if (entry.getRow() == row) {
                if (last == null) { // if first in column
                    firstInColumn[col] = entry.getNextRow();
                } else if (entry.getNextRow() == null) { // if last in column
                    last.setNextRow(null);
                } else {
                    last.setNextRow(entry.getNextRow());
                }
                success = true;
            } else {
                last = entry;
                entry = entry.getNextRow();
            }
        } // end while

        return success;
    } // end removeElement


    public boolean plus(SparseIntMatrix otherMat) {
        if (this.ROWS == otherMat.getNumRows() && this.COLUMNS == otherMat.getNumCols()) {
            SparseIntMatrix newMat = new SparseIntMatrix(ROWS, COLUMNS);

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    int newData = this.getElement(i, j) + otherMat.getElement(i, j);
                    if (newData != 0) {
                        newMat.setElement(i, j, newData);
                    }
                }
            }
            this.firstInColumn = newMat.firstInColumn;
            return true;
        } else {
            return false;
        }
    } // end plus

    public boolean minus(SparseIntMatrix otherMat) {
        if (this.ROWS == otherMat.getNumRows() && this.COLUMNS == otherMat.getNumCols()) {
            SparseIntMatrix newMat = new SparseIntMatrix(ROWS, COLUMNS);

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    int newData = this.getElement(i, j) - otherMat.getElement(i, j);
                    if (newData != 0) {
                        newMat.setElement(i, j, newData);
                    }
                }
            }
            this.firstInColumn = newMat.firstInColumn;
            return true;
        } else {
            return false;
        }
    } // end minus


    // accessor methods
    public int getNumRows() {
        return ROWS;
    }

    public int getNumCols() {
        return COLUMNS;
    }


    // helper methods
    private MatrixEntry[] readFile(String inputFile) {
        File file = new File(inputFile);

        try {
            Scanner fileScan = new Scanner(file);
            MatrixEntry[] matrixEntries = new MatrixEntry[ROWS * COLUMNS];
            int counter = 0;

            while (fileScan.hasNextLine()) {
                String line = fileScan.nextLine();
                Scanner lineScan = new Scanner(line);
                lineScan.useDelimiter(",");
                int row = Integer.parseInt(lineScan.next());
                int col = Integer.parseInt(lineScan.next());
                int data = Integer.parseInt(lineScan.next());

                if (row <= ROWS && col <= COLUMNS && data != 0) {
                    MatrixEntry entry = new MatrixEntry(row, col, data);
                    matrixEntries[counter] = entry;
                    counter++;
                }
            } // end while

            MatrixEntry[] out = Arrays.copyOf(matrixEntries, getLength(matrixEntries));
            return out;
        } // end try

        catch (FileNotFoundException e) {
            System.out.println("File does not exist");
            MatrixEntry[] out = new MatrixEntry[0];
            return out;
        } // end catch
    } // end readFile

    private void arrayToLinkedList(MatrixEntry[] matrixEntries) {

        for (int col = 0; col < COLUMNS; col++) {
            boolean first = true;
            MatrixEntry prevEntry = null;
            for (MatrixEntry entry : matrixEntries) {
                if (entry.getColumn() == col) {
                    if (first) {
                        firstInColumn[col] = entry;
                        prevEntry = entry;
                        first = false;
                    } else {
                        prevEntry.setNextRow(entry);
                        prevEntry = entry;
                    }
                }
            }
        } // end for (columns)

        for (int row = 0; row < ROWS; row++) {
            boolean first = true;
            MatrixEntry prevEntry = null;
            for (MatrixEntry entry : matrixEntries) {
                if (entry.getRow() == row) {
                    if (first) {
                        firstInRow[row] = entry;
                        prevEntry = entry;
                        first = false;
                    } else {
                        prevEntry.setNextCol(entry);
                        prevEntry = entry;
                    }
                }
            }
        } // end for (rows)
    } // end arrayToLinkedList

    private int getLength(Object[] array) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                count++;
            }
        }
        return count;
    }


    public static void main(String[] args) {
        SparseIntMatrix mat1 = new SparseIntMatrix(800, 800, "matrix1_data.txt");
        MatrixViewer.show(mat1);
        SparseIntMatrix mat2 = new SparseIntMatrix(800, 800, "matrix2_data.txt");
        MatrixViewer.show(mat2);
        SparseIntMatrix mat3 = new SparseIntMatrix(800, 800, "matrix2_noise.txt");
        boolean success = mat2.minus(mat3);
        if (success) {
            MatrixViewer.show(mat2);
        }
    }
}