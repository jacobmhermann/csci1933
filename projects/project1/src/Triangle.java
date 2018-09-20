import java.awt.*;

public class Triangle {

    double xPosition;
    double yPosition;
    double width;
    double height;
    Color color;

    public Triangle(double xpos, double ypos, double w, double h) {
        xPosition = xpos;
        yPosition = ypos;
        width = w;
        height = h;
    }

    public double calculatePerimeter() {
        double hyp = Math.sqrt(Math.pow((width/2),2) + Math.pow(height,2));
        double perimeter = width + (hyp*2);
        return perimeter;
    }

    public double calculateArea(){
        double area = (width*height)/2;
        return area;
    }

    public void setColor(Color col) { color = col; }

    public void setPos(double xpos, double ypos) {
        xPosition = xpos;
        yPosition = ypos;
    }

    public void setWidth(double w) { width = w; }

    public void setHeight(double h) { height = h; }

    public Color getColor() { return color; }

    public double getXPos() { return xPosition; }

    public double getYPos() { return yPosition; }

    public double getWidth() { return width; }

    public double getHeight() { return height; }
}
