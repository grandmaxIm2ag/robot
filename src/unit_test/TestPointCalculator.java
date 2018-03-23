package unit_test;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import utils.Point;
import utils.PointCalculator;

public class TestPointCalculator {
	float x;
	float y;
	
	@Before
	public void init(){
		Random rand = new Random();
		x = rand.nextFloat();
		y = rand.nextFloat();
	}
	
	/**
	 * 
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
}
