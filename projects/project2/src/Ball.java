import java.awt.*;

public class Ball extends Circle {

    private double speedX, speedY;

    public Ball(double x, double y, double r, Color newColor) {
        super(x,y,r);
        color = newColor;
    }

    public void setSpeedX(double newSpeedX) { speedX = newSpeedX; }

    public void setSpeedY(double newSpeedY) { speedY = newSpeedY; }

    public double getSpeedX() { return speedX; }

    public double getSpeedY() { return speedY; }

    public void updatePosition(double time) {
        super.x += speedX * time;
        super.y += speedY * time;
    }

    public boolean intersect(Ball other) {
        double distance = Math.sqrt(Math.pow(x-other.x , 2) + Math.pow(y-other.y , 2));
        if (distance > r + other.r) { return false; }
        else { return true; }
    }

    public void collide(Ball other) {
        if (this.intersect(other)) {

            double d = Math.sqrt(Math.pow(x-other.x, 2) + Math.pow(y-other.y, 2));
            double dX = (x - other.x)/d;
            double dY = (y - other.y)/d;

            // calculate new velocity of this ball in new bases
            double thisNewColVelocity = speedX * dX + speedY * dY;
            double thisNewNormVelocity = -1 * speedX * dY + speedY * dX;

            // calculate new velocity of other ball in new bases
            double otherNewColVelocity = other.speedX * dX + other.speedY * dY;
            double otherNewNormVelocity = -1 * other.speedX * dY + other.speedY * dX;

            // transform new velocity of this ball into original bases
            speedX = otherNewColVelocity * dX - thisNewNormVelocity * dY;
            speedY = otherNewColVelocity * dY + thisNewNormVelocity * dX;

            // transform new velocity of other ball into original bases
            other.speedX = thisNewColVelocity * dX - otherNewNormVelocity * dY;
            other.speedY = thisNewColVelocity * dY + otherNewNormVelocity * dX;

            // adjust for sticky bug
            x += 2*dX;
            y += 2*dY;

            // recolor balls (if one is red)
            if (this.getColor() == Color.RED || other.getColor() == Color.RED) {
                this.setColor(Color.RED);
                other.setColor(Color.RED);
            }
        }
    }
}