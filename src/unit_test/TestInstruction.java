package unit_test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import utils.Deliver;
import utils.Move;
import utils.Node;
import utils.Palet;
import utils.Pick;
import utils.Point;

/**
 * 
 */
public class TestInstruction {
	/**
	 * 
	 */
	Pick pick;
	/**
	 * 
	 */
	Move move;
	/**
	 * 
	 */
	Deliver deliver;
	
	/**
	 * 
	 */
	@Before
	public void init(){
		pick = new Pick(new Palet(new Point(0,0), true), new Point(0,0));
		move = new Move(new Point(0,0), new Point(1,1));
		deliver = new Deliver(new Palet(new Point(0,0), true));
	}
	
	/**
	 * 
	 */
	@Test
	public void testMove(){
		assertTrue(move.equals(new Move(new Point(0,0), new Point(1,1))));
		assertFalse(move.equals(new Move(new Point(0,0), new Point(0,9))));
		assertFalse(move.equals(new Move(new Point(0,9), new Point(0,0))));
	}
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testMoveException(){
		new Move(new Point(0,0), new Node(0,1));
	}
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testMoveException2(){
		new Move(new Point(0,0), new Point(0,0));
	}
	
	/**
	 * 
	 */
	@Test
	public void testPick(){
		assertTrue(pick.equals(new Pick(new Palet(new Point(0,0), true), 
				new Point(0,0))));
		assertFalse(pick.equals(new Pick(new Palet(new Point(1,0), true), 
				new Point(1,0))));
	}
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testPickException(){
		new Pick(new Palet(new Point(0,0), true), new Point(1,1));
	}
	
	/**
	 * 
	 */
	@Test
	public void testDeliver(){
		assertTrue(deliver.equals(new Deliver(new Palet(new Point(0,0), true))));
		assertFalse(deliver.equals(new Deliver(new Palet(new Point(0,0), false))));
	}
	
}
