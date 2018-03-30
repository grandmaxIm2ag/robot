package unit_test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import utils.Node;
import utils.Palet;
import utils.Point;

/**
 * 
 * Test unitaire pour la classe Palet
 */
public class TestPalet {

	/**
	 * Palet pour le test
	 */
	Palet palet;
	
	/**
	 * Initialise le palet
	 */
	@Before
	public void init() {
		palet = new Palet(new Point(0,0), true);
	}
	
	/**
	 * Le test
	 */
	@Test
	public void test(){
		assertTrue(new Palet(new Point(0,0), true).equals(palet));
		assertFalse(new Palet(new Point(0,0), false).equals(palet));
		assertFalse(new Palet(new Node(0,0), true).equals(palet));
		assertFalse(new Palet(new Point(0,1), true).equals(palet));
	}
}
