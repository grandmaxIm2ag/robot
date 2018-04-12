package unit_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import utils.R2D2Constants;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import junit.runner.Version;
import utils.Point;
import utils.PointCalculator;

/**
 * 
 * test unitaire pour la classe PointCalculator
 *
 */
public class TestPointCalculator {
	/**
	 * une cordonnée aléatoire
	 */
	float x;
	/**
	 * Une coordonnée aléatoire
	 */
	float y;
	/**
	 * delta pour l'imprecision de l'arrondie
	 */
	static final float delta = 0.1f;
	/**
	 * initialise x et y
	 */
	@Before
	public void init(){
		Random rand = new Random();
		x = rand.nextFloat();
		y = rand.nextFloat();
	}
	
	/**
	 * test de la fonction getWhiteLinePoint
	 */
	@Test
	public void testWhiteLine(){
		
		Point expected;
		
		//Test south
		expected = new Point(x, utils.R2D2Constants.Y_SOUTH);
		assertEquals(expected, PointCalculator.getWhiteLinePoint(true, x));
		
		//Test nort
		expected = new Point(x, utils.R2D2Constants.Y_NORTH);
		assertEquals(expected, PointCalculator.getWhiteLinePoint(false, x));
	}
	
	/**
	 * Test de la méthode is_on_goal
	 */
	@Test
	public void testOnGoal() {
		Point p1 = new Point(0, utils.R2D2Constants.Y_NORTH+5);
		Point p2 = new Point(0, utils.R2D2Constants.Y_SOUTH-5);
		
		Point p11 = new Point(0, utils.R2D2Constants.Y_NORTH);
		Point p22 = new Point(0, utils.R2D2Constants.Y_SOUTH);
		
		assertTrue(PointCalculator.is_on_goal(p1, false));
		assertTrue(PointCalculator.is_on_goal(p2, true));
		assertTrue(PointCalculator.is_on_goal(p11, false));
		assertTrue(PointCalculator.is_on_goal(p22, true));
		
		assertFalse(PointCalculator.is_on_goal(p1, true));
		assertFalse(PointCalculator.is_on_goal(p2, false));
		assertFalse(PointCalculator.is_on_goal(p11, true));
		assertFalse(PointCalculator.is_on_goal(p22, false));
	}
	
	/**
	 * Test de la fonction is_on_vertical_line
	 */
	@Test
	public void testOnVertical() {
		assertTrue(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_BLACK, 0)));
		assertTrue(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_BLACK+5, 0)));
		assertTrue(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_BLACK-5, 0)));
		assertTrue(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_RED, 0)));
		assertTrue(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_RED+5, 0)));
		assertTrue(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_RED-5, 0)));
		assertTrue(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_YELLOW, 0)));
		assertTrue(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_YELLOW+5, 0)));
		assertTrue(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_YELLOW-5, 0)));
		
		
		assertFalse(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_BLACK+11, 0)));
		assertFalse(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_BLACK-11, 0)));
		assertFalse(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_RED+11, 0)));
		assertFalse(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_RED-11, 0)));
		assertFalse(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_YELLOW+11, 0)));
		assertFalse(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_YELLOW-11, 0)));
	}	
	
	
	/**
	 * Test la fonction closest_color
	 */
	//@Test
	public void test_closest_color() {
		assertEquals(R2D2Constants.colors[R2D2Constants.RED], PointCalculator
				.closestColor(new Point(R2D2Constants.X_RED-20, 0)));
		assertEquals(R2D2Constants.colors[R2D2Constants.RED], PointCalculator
				.closestColor(new Point(R2D2Constants.X_RED+20, 0)));
		
		assertEquals(R2D2Constants.colors[R2D2Constants.YELLOW], PointCalculator
				.closestColor(new Point(R2D2Constants.X_YELLOW-10, 0)));
		assertEquals(R2D2Constants.colors[R2D2Constants.YELLOW], PointCalculator
				.closestColor(new Point(R2D2Constants.X_YELLOW+10, 0)));
		
		assertEquals(R2D2Constants.colors[R2D2Constants.BLACK], PointCalculator
				.closestColor(new Point(R2D2Constants.X_BLACK-10, 0)));
		assertEquals(R2D2Constants.colors[R2D2Constants.BLACK], PointCalculator
				.closestColor(new Point(R2D2Constants.X_BLACK+10, 0)));
		
		assertFalse(R2D2Constants.colors[R2D2Constants.YELLOW] == PointCalculator
				.closestColor(new Point(R2D2Constants.X_RED-10, 0)));
		assertFalse(R2D2Constants.colors[R2D2Constants.BLACK] == PointCalculator
				.closestColor(new Point(R2D2Constants.X_RED+10, 0)));
		
		assertFalse(R2D2Constants.colors[R2D2Constants.RED] == PointCalculator
				.closestColor(new Point(R2D2Constants.X_YELLOW-10, 0)));
		assertFalse(R2D2Constants.colors[R2D2Constants.BLACK] == PointCalculator
				.closestColor(new Point(R2D2Constants.X_YELLOW+10, 0)));
		
		assertFalse(R2D2Constants.colors[R2D2Constants.YELLOW] == PointCalculator
				.closestColor(new Point(R2D2Constants.X_BLACK-10, 0)));
		assertFalse(R2D2Constants.colors[R2D2Constants.RED] == PointCalculator
				.closestColor(new Point(R2D2Constants.X_BLACK+10, 0)));
	}


	/**
	 * Test de la fonction getPointFromAngle
	 */
	@Test
	public void testDistanceAngle2() {
		Random rand = new Random();
		for(int i=0; i<100000; i++) {
			float x1, x2, y1, y2;
			x1 = rand.nextFloat()*100;
			x2 = rand.nextFloat()*100;
			y1 = rand.nextFloat()*100;
			y2 = rand.nextFloat()*100;
					
			Point p1 = new Point(x1, y1);
			Point p2 = new Point(x2, y2);
			float a2 = (p1.angle(p2));
			float dist = p1.distance(p2);	
			assertEquals(p2.getX(), PointCalculator.getPointFromAngle(p1, dist, a2).getX(), delta);
			assertEquals(p2.getY(), PointCalculator.getPointFromAngle(p1, dist, a2).getY(), delta);
		}
	}
	
}