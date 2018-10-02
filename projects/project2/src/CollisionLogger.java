import java.lang.reflect.Array;

public class CollisionLogger {

    int[][] heatMap;
    int screenWidth,screenHeight,bucketWidth;
    
    public CollisionLogger(int sWidth, int sHeight, int bWidth) {
        screenWidth = sWidth;
        screenHeight = sHeight;
        bucketWidth = bWidth;
    	int numWide = screenWidth / bucketWidth;
    	int numTall = screenHeight / bucketWidth;
    	heatMap = new int[numWide][numTall];

    	// set initial values to 0
        for (int i=0; i<numWide; i++) {
            for (int j=0; j<numTall; j++) {
                heatMap[i][j] = 0;
            }
        }
    }

     /**
     * This method records the collision event between two balls. Specifically, in increments the bucket corresponding to
     * their x and y positions to reflect that a collision occurred in that bucket.
     */

    public void collide(Ball one, Ball two) {

    	// adds collision to heatMap in corresponding heatMap bucket
        double xPos = (one.getXPos() + two.getXPos()) / 2;
        double yPos = (one.getYPos() + two.getYPos()) / 2;
        int numWide = (int) xPos / bucketWidth;
        int numTall = (int) yPos / bucketWidth;
        heatMap[numWide][numTall] += 1;
    }

    /**
     * Returns the heat map scaled such that the bucket with the largest number of collisions has value 255,
     * and buckets without any collisions have value 0.
     */

    public int[][] getNormalizedHeatMap() {
    	
        int[][] normalizedHeatMap = new int[Array.getLength(heatMap)][Array.getLength(heatMap[0])];

        // finds the maximum number of collisions in any one bucket
        int maxCollisions = 0;
        for (int i=0; i<Array.getLength(heatMap); i++) {
            for (int j = 0; j<Array.getLength(heatMap[0]); j++) {
                if (heatMap[i][j] > maxCollisions) {
                    maxCollisions = heatMap[i][j];
                }
            }
        }

        // normalizes heatMap with the bucket having the most collisions equal to 255
        for (int i=0; i < Array.getLength(heatMap); i++) {
            for (int j = 0; j < Array.getLength(heatMap[0]); j++) {
                normalizedHeatMap[i][j] = heatMap[i][j] * (255 / maxCollisions);
            }
        }

        // enlarges normalizedHeatMap to make .png larger
        int[][] resizedNormalizedHeatMap = new int[screenWidth][screenHeight];
        for (int i=0; i < Array.getLength(normalizedHeatMap); i++) {
            for (int j = 0; j < Array.getLength(normalizedHeatMap[0]); j++) {
                for (int k = 0; k < bucketWidth; k++) {
                    for (int l=0; l < bucketWidth; l++) {
                        resizedNormalizedHeatMap[i*bucketWidth + k][j*bucketWidth + l] = normalizedHeatMap[i][j];
                    }
                }
            }
        }

        return resizedNormalizedHeatMap;
    }
}
