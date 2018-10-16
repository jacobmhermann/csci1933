import java.awt.Color;

import org.junit.Test;
import static org.junit.Assert.*;


public class TriangleTest {

	private Triangle tri = new Triangle(400, 400, 50, 100);;

	@Test
	public void testCalculateArea() {
		assertEquals(2500, tri.calculateArea(),0.001);
	}

	@Test
	public void testCalculatePerimeter() {
		assertEquals(256.155, tri.calculatePerimeter(),0.001);
	}

	@Test
	public void testSetAndGetColor() {
		tri.setColor(Color.BLUE);
		assertEquals(Color.BLUE, tri.getColor());
	}

	@Test
	public void testSetAndGetPos() {
		tri.setPos(100, 200);
		assertEquals(100.0, tri.getXPos(),0.001);
		assertEquals(200.0, tri.getYPos(),0.001);
	}

	@Test
	public void testSetAndGetHeight() {
		tri.setHeight(150);
		assertEquals(150.0, tri.getHeight(),0.001);
	}

	@Test
	public void testSetAndGetWidth() {
		tri.setWidth(150);
		assertEquals(150.0, tri.getWidth(),0.001);
	}

}
