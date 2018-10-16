public class CollisionLogger {

    private int[][] heatMap;
    private int screenWidth,screenHeight,bucketWidth;
    
    public CollisionLogger(int sWidth, int sHeight, int bWidth) {

        screenWidth = sWidth;
        screenHeight = sHeight;
        bucketWidth = bWidth;
    	int numWide = screenWidth / bucketWidth;
    	int numTall = screenHeight / bucketWidth;
    	heatMap = new int[numTall][numWide];

    	// set initial values to 0
        for (int i=0; i<numTall; i++) {
            for (int j=0; j<numWide; j++) {
                heatMap[i][j] = 0;
            }
        }
    }

     /**
      * This method records the collision event between two balls. Specifically, in increments the bucket
      * corresponding to their x and y positions to reflect that a collision occurred in that bucket.
      */

    public void collide(Ball one, Ball two) {

    	// adds collision to heatMap in corresponding heatMap bucket

        int xPos = (int)(one.getXPos() + two.getXPos()) / 2;
        int yPos = (int)(one.getYPos() + two.getYPos()) / 2;
        int numWide = xPos / bucketWidth;
        int numTall = yPos / bucketWidth;
        heatMap[numTall][numWide] += 1;
    }

    /**
     * Returns the heat map scaled such that the bucket with the largest number of collisions has value 255,
     * and buckets without any collisions have value 0.
     */

    public int[][] getNormalizedHeatMap() {
    	
        int[][] normalizedHeatMap = new int[heatMap.length][heatMap[0].length];

        // finds the maximum number of collisions in any one bucket
        int maxCollisions = 0;
        for (int i=0; i < heatMap.length; i++) {
            for (int j = 0; j < heatMap[i].length; j++) {
                if (heatMap[i][j] > maxCollisions) {
                    maxCollisions = heatMap[i][j];
                }
            }
        }

        // normalizes heatMap with the bucket having the most collisions equal to 255
        if (maxCollisions != 0) {
            for (int i = 0; i < heatMap.length; i++) {
                for (int j = 0; j < heatMap[i].length; j++) {
                    normalizedHeatMap[i][j] = (heatMap[i][j] * 255) / maxCollisions;
                }
            }
        } else {
            // avoids / by 0 errors when there are no collisions logged
            for (int i = 0; i < heatMap.length; i++) {
                for (int j = 0; j < heatMap[0].length; j++) {
                    normalizedHeatMap[i][j] = 0;
                }
            }
        }

        return normalizedHeatMap;
    }

    public int[][] getResizedHeatMap() {
        // enlarges normalized HeatMap to make .png larger

        int[][] normalizedHeatMap = getNormalizedHeatMap();

        int[][] resizedNormalizedHeatMap = new int[screenHeight][screenWidth];
        for (int i = 0; i < normalizedHeatMap.length; i++) {
            for (int j = 0; j < normalizedHeatMap[0].length; j++) {
                for (int k = 0; k < bucketWidth; k++) {
                    for (int l = 0; l < bucketWidth; l++) {
                        resizedNormalizedHeatMap[i*bucketWidth+k][j*bucketWidth+l] = normalizedHeatMap[i][j];
                    }
                }
            }
        }

        return resizedNormalizedHeatMap;
    }
}