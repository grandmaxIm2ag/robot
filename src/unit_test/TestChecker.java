package unit_test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import utils.Checker;
import utils.Palet;
import utils.Point;

/**
 * 
 * 
 */
public class TestChecker {
	/**
	 * 
	 */
	Checker checker;
	
	/**
	 * 
	 */
	@Before
	public void init(){
		checker = new Checker(new Point(0,0));
	}
	
	/**
	 * 
	 */
	@Test
	public void test(){
		assertTrue(checker.compare(new Palet(new Point(1,1), true), new Palet(
				new Point(0,1), true)) == 1);
		assertTrue(checker.compare(new Palet(new Point(0,1), true), new Palet(
				new Point(1,1), true)) == -1);
		assertTrue(checker.compare(new Palet(new Point(0,1), true), new Palet(
				new Point(0,1), true)) == 0);
	}
}
