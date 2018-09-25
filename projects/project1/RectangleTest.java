import java.awt.Color;

import org.junit.Test;
import static org.junit.Assert.*;


public class RectangleTest {

	private Rectangle rec = new Rectangle(400, 400, 50, 100);

	@Test
	public void testCalculateArea() {
		assertEquals(5000, rec.calculateArea(),0.001);
	}

	@Test
	public void testCalculatePerimeter() {
		assertEquals(300, rec.calculatePerimeter(),0.001);
	}

	@Test
	public void testSetAndGetColor() {
		rec.setColor(Color.BLUE);
		assertEquals(Color.BLUE, rec.getColor());
	}

	@Test
	public void testSetAndGetPos() {
		rec.setPos(100.0, 200.0);
		assertEquals(100.0, rec.getXPos(),0.001);
		assertEquals(200.0, rec.getYPos(),0.001);
	}

	@Test
	public void testSetAndGetHeight() {
		rec.setHeight(150.0);
		assertEquals(150.0, rec.getHeight(),0.001);
	}

	@Test
	public void testSetAndGetWidth() {
		rec.setWidth(150.0);
		assertEquals(150.0, rec.getWidth(),0.001);
	}
}
