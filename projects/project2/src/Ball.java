import java.awt.*;

public class Ball extends Circle {

    double xSpeed, ySpeed;

    public Ball(double x, double y, double r, Color color) {
        super(x,y,r);
        super.color = color;
    }

    public void setSpeedX(double xSpeed) { this.xSpeed = xSpeed; }

    public void setSpeedY(double ySpeed) { this.ySpeed = ySpeed; }

    public double getSpeedX() { return xSpeed; }

    public double getSpeedY() { return this.ySpeed; }

    public void updatePosition(double time) {
        super.x += this.xSpeed * time;
        super.y += this.ySpeed * time;
    }

    public boolean intersect(Ball other) {
        double distance = Math.sqrt(Math.pow(super.x-other.x , 2) + Math.pow(super.y-other.y , 2));
        if (distance > super.r + other.r) { return false; }
        else { return true; }
    }

    public void collide(Ball other) {
        if (this.intersect(other)) {
            double d = Math.sqrt(Math.pow(super.x-other.x, 2) + Math.pow(super.y-other.y, 2));
            double dX = (super.x - other.x)/d;
            double dY = (super.y - other.y)/d;

            // calculate new velocity of this ball in new bases
            double thisNewColVelocity = this.xSpeed * dX + this.ySpeed * dY;
            double thisNewNormVelocity = -1 * this.xSpeed * dY + this.ySpeed * dX;

            // calculate new velocity of other ball in new bases
            double otherNewColVelocity = other.xSpeed * dX + other.ySpeed * dY;
            double otherNewNormVelocity = -1 * other.xSpeed * dY + other.ySpeed * dX;

            // transform new velocity of this ball into original bases
            this.xSpeed = otherNewColVelocity * dX - thisNewNormVelocity * dY;
            this.ySpeed = otherNewColVelocity * dY + thisNewNormVelocity * dX;

            // transform new velocity of other ball into original bases
            other.xSpeed = thisNewColVelocity * dX - otherNewNormVelocity * dY;
            other.ySpeed = thisNewColVelocity * dY + otherNewNormVelocity * dX;

            // adjust for sticky bug
            this.x += 2* dX;
            this.y += 2* dY;

            // recolor balls (if one is red)
            if (this.getColor() == Color.RED || other.getColor() == Color.RED) {
                this.setColor(Color.RED);
                other.setColor(Color.RED);
            }
        }
    }
}