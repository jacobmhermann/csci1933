import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Array;

public class BallScreenSaver extends AnimationFrame {

    private int numBalls;
    private Ball[] ballArray;
    private int ballSize = 18;
    private int speed = 200;
    public final int border = 25;

	/* These are data members for managing the collision logging, which
	   you should also use in your class. */

    private CollisionLogger collisionLogger;
    private static final int loggerBucketWidth = 25;
    private int saveCounter = 0;

    public BallScreenSaver() {
        super();
        numBalls = randInt(5,20);
        ballArray = new Ball[numBalls];
        for (int i = 0; i < numBalls; i++) {
            initiateBall(ballSize,speed,border,i);
        }
        setFPS(35);
        collisionLogger = new CollisionLogger(this.getWidth(), this.getHeight(), loggerBucketWidth);
    }

    public BallScreenSaver(int frameWidth, int frameHeight, String appWindowName, int numBalls) {
        super(frameWidth, frameHeight, appWindowName);
        ballArray = new Ball[numBalls];
        for (int i = 0; i < numBalls; i++) {
            initiateBall(ballSize,speed,border,i);
        }

        // creates a red ball
        ballArray[0].setColor(Color.RED);

        setFPS(35);
        collisionLogger = new CollisionLogger(this.getWidth(), this.getHeight(), loggerBucketWidth);
    }


    public static void main(String[] args) {
        BallScreenSaver go = new BallScreenSaver(800,800,"Bouncing Balls",20);
        go.start();
    }

    public void action() {
        // This method is called once every frame to update the state of each ball.

        for (int i = 0; i < Array.getLength(ballArray); i++) {
            Ball ball = ballArray[i];

            // update both X and Y positions
            double xSpeed = ballArray[i].getSpeedX();
            double dX = xSpeed / 15;
            double dY = ball.getSpeedY() / getFPS();
            ball.setPos(ball.getXPos() + dX, ball.getYPos() + dY);

            // handle collisions with the border
            if (ball.getXPos() < border) {
                ball.setSpeedX(ball.getSpeedX() * -1);
                ball.setPos(ball.getXPos() + 5, ball.getYPos());
            } else if (ball.getXPos() > getHeight() - border - ballSize) {
                ball.setSpeedX(ball.getSpeedX() * -1);
                ball.setPos(ball.getXPos() - 5, ball.getYPos());
            } else if (ball.getYPos() < border) {
                ball.setSpeedY(ball.getSpeedY() * -1);
                ball.setPos(ball.getXPos(), ball.getYPos() + 5);
            } else if (ball.getYPos() > getWidth() - border - ballSize) {
                ball.setSpeedY(ball.getSpeedY() * -1);
                ball.setPos(ball.getXPos(), ball.getYPos() - 5);
            }

            // handle collisions with other balls
            for (int j = 0; j < Array.getLength(ballArray); j++) {
                Ball otherBall = ballArray[j];
                if (ball.intersect(otherBall) && i != j) {
                    ball.collide(otherBall);
                    collisionLogger.collide(ball,otherBall);
                }
            }
        }
    }

    public void draw(Graphics g) {
        //This method is called once every frame to draw the Frame.

        g.setColor(Color.black);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.white);
        g.drawRect(border,border,getWidth()-border*2,getHeight()-border*2);
        for (int i = 0; i<Array.getLength(ballArray); i++) {
            g.setColor(ballArray[i].getColor());
            g.fillOval((int)ballArray[i].getXPos(), (int)ballArray[i].getYPos(), ballSize, ballSize);
        }

    }

    public void initiateBall(int ballSize, int speed, int border, int i) {
        // formats the ball objects
        double xPos = randDouble(border,getWidth()-border);
        double yPos = randDouble(border,getHeight()-border);
        Color color = Color.GREEN;

        // initiates ball objects
        ballArray[i] = new Ball(xPos,yPos,ballSize/2, Color.GREEN);

        // gives ball velocity
        setRandDir(speed, i);
    }

    public void setRandDir(double speed, int i){
        double dir = randDouble(0,Math.PI*2);
        ballArray[i].setSpeedX(Math.cos(dir)*speed);
        ballArray[i].setSpeedY(Math.sin(dir)*speed);
    }

    public int randInt(int min, int max){
        // random int between min and max.
        return (int)(Math.random()*(max-min)+min);
    }

    public double randDouble(double min, double max){
        // random double between min and max.
        return (Math.random()*(max-min)+min);
    }

    public void processKeyEvent(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (e.getID() == KeyEvent.KEY_PRESSED && keyCode == KeyEvent.VK_P) {
            EasyBufferedImage image = EasyBufferedImage.createImage(collisionLogger.getNormalizedHeatMap());
            try {
                image.save("heatmap"+saveCounter+".png", EasyBufferedImage.PNG);
                saveCounter++;
            } catch (IOException exc) {
                exc.printStackTrace();
                System.exit(1);
            }
        }

        else if (e.getID() == KeyEvent.KEY_PRESSED && keyCode == KeyEvent.VK_LEFT) {
            speed *= .9;
            for (int i = 0; i<Array.getLength(ballArray); i++) {
                ballArray[i].setSpeedX(ballArray[i].getSpeedX() * .9);
                ballArray[i].setSpeedY(ballArray[i].getSpeedY() * .9);
            }
            int newFPS = (int) (getFPS()*.9);
            setFPS(newFPS);
        }

        else if (e.getID() == KeyEvent.KEY_PRESSED && keyCode == KeyEvent.VK_RIGHT) {
            speed *= 1.1;
            for (int i = 0; i<Array.getLength(ballArray); i++) {
                ballArray[i].setSpeedX(ballArray[i].getSpeedX() * 1.1);
                ballArray[i].setSpeedY(ballArray[i].getSpeedY() * 1.1);
            }
            int newFPS = (int) (getFPS()*1.1);
            setFPS(newFPS);
        }
    }

}
