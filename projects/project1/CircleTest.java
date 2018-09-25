import java.awt.Color;

import org.junit.Test;
import static org.junit.Assert.*;


public class CircleTest {
	private Circle circ = new Circle(400, 400, 50);

	@Test
	public void testCalculateArea() {
		assertEquals(7853.98163, circ.calculateArea(),0.001);
	}

	@Test
	public void testCalculatePerimeter() {
		assertEquals(314.15927, circ.calculatePerimeter(),0.001);
	}

	public void testSetAndGetColor() {
		circ.setColor(Color.BLUE);
		assertEquals(Color.BLUE, circ.getColor());
	}

	@Test
	public void testSetAndGetPos() {
		circ.setPos(100, 200);
		assertEquals(100.0, circ.getXPos(),0.001);
		assertEquals(200.0, circ.getYPos(),0.001);
	}

	@Test
	public void testSetAndGetRadius() {
		circ.setRadius(150);
		assertEquals(150.0, circ.getRadius(),0.001);
	}
}
