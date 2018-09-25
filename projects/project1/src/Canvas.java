import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class Canvas extends JApplet {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter base shape (triangle/rectangle/circle): ");
		String baseShapeInput = scanner.next();
		System.out.print("Enter number of iterations: ");
		int IterInput = scanner.nextInt();

		Canvas canvas = new Canvas();
		Color[] colors = new Color[] {Color.RED, Color.BLUE, Color.GREEN};

		if (baseShapeInput.equals("triangle")) {
			Triangle tri1 = new Triangle(0, 0, 0, 0);
			canvas.fractal(tri1, canvas, 1, IterInput, 200, 200,
					300, 500, colors, 0);

		}

		if (baseShapeInput.equals("rectangle")) {
			Rectangle rect1 = new Rectangle(0, 0, 0, 0);
			canvas.fractal(rect1, canvas, 1, IterInput, 200, 200,
					300, 300, colors, 0);
		}

		if (baseShapeInput.equals("circle")) {
			Circle circ1 = new Circle(0, 0, 0);
			canvas.fractal(circ1, canvas, 1, IterInput, 100,
					400, 400, colors, 0);
		}

		double totalArea = canvas.calculateArea();
		System.out.println("The total area of the fractal is: " + totalArea);
	}


	private int height;
	private int width;
	private LinkedList<Circle> circles;
	private LinkedList<Rectangle> rectangles;
	private LinkedList<Triangle> triangles;


	public Canvas() {
		JFrame f = new JFrame("Canvas");
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {System.exit(0);}
		});
		height = 800;
		width = 800;
		f.setSize(width, height);
		f.getContentPane().add(this);
		circles = new LinkedList<Circle>();
		rectangles = new LinkedList<Rectangle>();
		triangles = new LinkedList<Triangle>();
		f.setVisible(true);
	}


	public Canvas(int h, int w) {
		JFrame f = new JFrame("Canvas");
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {System.exit(0);}
		});
		height = h;
		width = w;
		f.setSize(width, height);
		f.getContentPane().add(this);
		circles = new LinkedList<Circle>();
		rectangles = new LinkedList<Rectangle>();
		triangles = new LinkedList<Triangle>();
		f.setVisible(true);
	}


	public synchronized void paint(Graphics g) {
		ListIterator<Circle> circItr = circles.listIterator();
		ListIterator<Rectangle> recItr = rectangles.listIterator();
		ListIterator<Triangle> triItr = triangles.listIterator();

		while (circItr.hasNext()) {
			Circle curCircle = circItr.next();
			g.setColor(curCircle.getColor());
			int curRadius = (int)curCircle.getRadius();
			g.fillOval((int)curCircle.getXPos() - curRadius, (int)curCircle.getYPos()
					- curRadius, 2 * curRadius, 2 * curRadius);

		}

		while (recItr.hasNext()) {
			Rectangle curRectangle = recItr.next();
			g.setColor(curRectangle.getColor());
			g.fillRect((int)curRectangle.getXPos(), (int)curRectangle.getYPos(),
					(int)curRectangle.getWidth(), (int)curRectangle.getHeight());
		}

		while (triItr.hasNext()) {
			Triangle curTriangle = triItr.next();
			g.setColor(curTriangle.getColor());
			Polygon po = new Polygon();
			po.addPoint((int)curTriangle.getXPos(), (int)curTriangle.getYPos());
			po.addPoint((int)(curTriangle.getXPos() + curTriangle.getWidth()), (int)curTriangle
					.getYPos());
			po.addPoint((int)(curTriangle.getXPos() + curTriangle.getWidth() / 2), (int)(curTriangle.getYPos() - curTriangle.getHeight()));
			g.fillPolygon(po);
		}
	}


	public synchronized void drawShape(Triangle tri){ triangles.add(tri); }


	public synchronized void drawShape(Rectangle rect){ rectangles.add(rect); }


	public synchronized void drawShape(Circle circle){ circles.add(circle); }


	public void fractal(Triangle tri, Canvas canvas, int currIter, int maxIter, int triangleWidth,
						int triangleHeight, int xPos, int yPos, Color[] colors, int currCol) {
		Triangle newTri = new Triangle(xPos, yPos, triangleWidth, triangleHeight);
		newTri.setColor(colors[currCol]);
		triangles.add(newTri);
		canvas.drawShape(newTri);
		if (currIter < maxIter) {
			int newCol = 0;
			if (currCol <= 1) { newCol = currCol + 1; }
			// move left
			int newXPos = xPos - (triangleWidth / 2);
			int newYPos = yPos;
			canvas.fractal(tri, canvas, currIter + 1, maxIter, triangleWidth / 2,
					triangleHeight / 2, newXPos, newYPos, colors, newCol);
			// move up
			newXPos = xPos + (triangleWidth / 4);
			newYPos = yPos - triangleHeight;
			canvas.fractal(tri, canvas, currIter + 1, maxIter, triangleWidth / 2,
					triangleHeight / 2, newXPos, newYPos, colors, newCol);
			// move right
			newXPos = xPos + triangleWidth;
			newYPos = yPos;
			canvas.fractal(tri, canvas, currIter + 1, maxIter, triangleWidth / 2,
					triangleHeight / 2, newXPos, newYPos, colors, newCol);
		}
	}


	public void fractal(Rectangle rect, Canvas canvas, int currIter, int maxIter, int rectWidth,
						int rectHeight, int xPos, int yPos, Color[] colors, int currCol) {
		Rectangle newRect = new Rectangle(xPos,yPos,rectWidth,rectHeight);
		newRect.setColor(colors[currCol]);
		rectangles.add(newRect);
		canvas.drawShape(newRect);
		if (currIter < maxIter) {
			int newCol = 0;
			if (currCol <= 1) { newCol = currCol+1; }
			// move left
			int newXPos = xPos - (rectWidth/2);
			int newYPos = yPos + (rectHeight/4);
			canvas.fractal(rect, canvas, currIter+1, maxIter, rectWidth/2,
					rectHeight/2, newXPos, newYPos, colors, newCol);
			// move up
			newXPos = xPos + (rectWidth/4);
			newYPos = yPos + rectHeight;
			canvas.fractal(rect, canvas, currIter+1, maxIter, rectWidth/2,
					rectHeight/2, newXPos, newYPos, colors, newCol);
			// move right
			newXPos = xPos + rectWidth;
			newYPos = yPos + rectHeight/4;
			canvas.fractal(rect, canvas, currIter+1, maxIter, rectWidth/2,
					rectHeight/2, newXPos, newYPos, colors, newCol);
			// move down
			newXPos = xPos + rectWidth/4;
			newYPos = yPos - rectHeight/2;
			canvas.fractal(rect, canvas, currIter+1, maxIter, rectWidth/2,
					rectHeight/2, newXPos, newYPos, colors, newCol);
		}
	}


	public void fractal(Circle circle, Canvas canvas, int currIter, int maxIter, int radius,
						int xPos, int yPos, Color[] colors, int currCol) {
		Circle newCircle = new Circle(xPos, yPos, radius);
		newCircle.setColor(colors[currCol]);
		circles.add(newCircle);
		canvas.drawShape(newCircle);
		// recursively add more circles
		if (currIter < maxIter) {
			int newCol = 0;
			if (currCol <= 1) { newCol = currCol + 1; }
			// move left
			int newXPos = xPos - radius;
			int newYPos = yPos;
			canvas.fractal(circle, canvas, currIter + 1, maxIter, radius / 2,
					newXPos, newYPos, colors, newCol);
			// move up
			newXPos = xPos;
			newYPos = yPos - radius;
			canvas.fractal(circle, canvas, currIter + 1, maxIter, radius / 2,
					newXPos, newYPos, colors, newCol);
			// move right
			newXPos = xPos + radius;
			newYPos = yPos;
			canvas.fractal(circle, canvas, currIter + 1, maxIter, radius / 2,
					newXPos, newYPos, colors, newCol);
			// move down
			newXPos = xPos;
			newYPos = yPos + radius;
			canvas.fractal(circle, canvas, currIter + 1, maxIter, radius / 2,
					newXPos, newYPos, colors, newCol);
		}
	}


	public double calculateArea() {
		double sumArea = 0;
		for (int i = 0; i < triangles.size(); i++) { sumArea += triangles.get(i).calculateArea(); }
		for (int i = 0; i < rectangles.size(); i++) { sumArea += rectangles.get(i).calculateArea(); }
		for (int i = 0; i < circles.size(); i++) { sumArea += circles.get(i).calculateArea(); }
		return sumArea/2;
	}


	public synchronized void clear() {
		circles.clear();
		rectangles.clear();
		triangles.clear();
	}
}