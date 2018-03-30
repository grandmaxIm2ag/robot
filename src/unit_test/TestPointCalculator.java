package unit_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

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
	 * initialise x et y
	 */
	@Before
	public void init(){
		Random rand = new Random();
		x = rand.nextFloat();
		y = rand.nextFloat();
	}
	
	/**
	 * test de la classe
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
	 * 
	 */
	@Test
	public void testOnVertical() {
		assertTrue(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_BLACK, 0)));
		System.out.println((Math.abs(utils.R2D2Constants.X_BLACK+5 - utils.R2D2Constants.X_BLACK)<5));
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
				utils.R2D2Constants.X_BLACK+6, 0)));
		assertFalse(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_BLACK-6, 0)));
		assertFalse(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_RED+6, 0)));
		assertFalse(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_RED-6, 0)));
		assertFalse(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_YELLOW+6, 0)));
		assertFalse(PointCalculator.is_on_vertical_line(new Point(
				utils.R2D2Constants.X_YELLOW-6, 0)));
	}	
	
	@Test
	public void testAngleConversion() {
		float angle = 180;
		assertEquals((float) Math.PI, (float) PointCalculator.degreesToRadians(angle), 0.02);
	}
	
	@Test
	public void testDistanceAngle() {
		float o = 0;
		float n = 90;
		float x = 0;
		float y = 150;
		float x1 = 50;
		float y1 = 150;
		float dist = (float) Math.sqrt((x-x1)*(x-x1) + (y-y1)*(y-y1));
		assertEquals(new Point(x,y), PointCalculator.getPointFromAngle(new Point(x1,y1), dist, o, n));
	}
}


