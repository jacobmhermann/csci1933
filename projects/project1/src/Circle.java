import java.awt.*;

public class Circle {

    double xPosition;
    double yPosition;
    double radius;
    Color color;


    public Circle(double xpos, double ypos, double rad) {
        xPosition = xpos;
        yPosition = ypos;
        radius = rad;
    }

    public double calculatePerimeter() {
        double perimeter = 2 * radius * Math.PI;
        return perimeter;
    }

    public double calculateArea(){
        double area = Math.pow(radius,2) * Math.PI;
        return area;
    }

    public void setColor(Color col) { color = col; }

    public void setPos(double xpos, double ypos) {
        xPosition = xpos;
        yPosition = ypos;
    }

    public void setRadius(double rad) { radius = rad; }

    public Color getColor() { return color; }

    public double getXPos() { return xPosition; }

    public double getYPos() { return yPosition; }

    public double getRadius() { return radius; }
}
