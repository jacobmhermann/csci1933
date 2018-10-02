import java.lang.reflect.Array;

public class HeatGrid{

    int[][] heatGrid;
    boolean[][] booleanHeatGrid;

    public HeatGrid(int width, int height) {

        heatGrid = new int[width][height];
        booleanHeatGrid = new boolean[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                heatGrid[i][j] = 0;
                booleanHeatGrid[i][j] = false;
            }
        }
    }


    public static void main(String[] args) {
        HeatGrid grid = new HeatGrid(9,8);
        grid.placeSource("campfire", 3,2);
        grid.placeSource("ice-cube",6,2);
        System.out.println(grid.placeSource("refrigerator",8,7));
        grid.displayGrid();

    }



    public boolean placeSource(String src, int x, int y) {
        if (x >= heatGrid.length || x < 0) {
            return false;
        } else if (y >= heatGrid[0].length || y < 0) {
            return false;
        } else if (booleanHeatGrid[x][y]) {
            return false;
        } else {

            int heat, range;
            double decay;

            if (src == "light-bulb") {
                heat = 1;
                range = 0;
                decay = 0;
            } else if (src == "campfire") {
                heat = 4;
                range = 2;
                decay = .5;
            } else if (src == "furnace") {
                heat = 10;
                range = 4;
                decay = .35;
            } else if (src == "ice-cube") {
                heat = -2;
                range = 1;
                decay = .5;
            } else if (src == "refrigerator") {
                heat = -8;
                range = 3;
                decay = .2;
            } else if (src == "glacier") {
                heat = -20;
                range = 5;
                decay = .15;
            } else {  return false; }

            booleanHeatGrid[x][y] = true;
            heatGrid[x][y] += heat;

            for (int i = -range; i <= range; i++) {
                for (int j = -range; j <= range; j++) {
                    decayHeat(x,y,i,j,heat,decay);
                }
            }

            return true;
        }
    }

    public int[][] getHeats() { return heatGrid; }

    public int getHeat(int x, int y) { return heatGrid[x][y]; }

    public int getNetHeat() {
        int sumHeats = 0;

        for (int i = 0; i < heatGrid.length; i++) {
            for (int j = 0; j < heatGrid[0].length; j++) {
                sumHeats += heatGrid[i][j];
            }
        }

        return sumHeats;
    }

    public void displayGrid() {
        for (int i = 0; i < heatGrid[0].length; i++) {
            for (int j = 0; j < heatGrid.length; j++) {
                System.out.print(heatGrid[j][i] + "\t");
            }
            System.out.println();
        }
    }




    private void decayHeat(int x, int y, int i, int j, int heat, double decay) {
        if (i != 0 || j != 0) {
            if (x + i < heatGrid.length && x + i >= 0) {
                if (y + j < heatGrid[0].length && y + j >= 0) {
                    heatGrid[x + i][y + j] += calcDecayHeat(decay, heat, distance(i, j));
                }
            }
        }
    }

    private int calcDecayHeat(double decay, int heat, int distance) {
        // utility method to find heat of neighboring grid spaces
        return (int) (heat * Math.exp(-1 * decay * distance));
    }

    private int distance(int i, int j) {
        // utility method to find distance from source

        // flips negative indices
        if (i < 0) { i = -i; } // Math.abs(i) returns the absolute value of i
        if (j < 0) { j = -j; }

        // returns max value
        if (i >= j) { return i; }
        else { return j; }
    }
}
