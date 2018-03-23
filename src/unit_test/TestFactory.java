package unit_test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import exception.InstructionException;

import utils.Deliver;
import utils.FactoryInstruction;
import utils.Move;
import utils.Node;
import utils.Palet;
import utils.Pick;

/**
 * 
 */
public class TestFactory {
	/**
	 * 
	 */
	HashMap<String, Node> map;
	
	/**
	 * 
	 */
	@Before
	public void init(){
		map = new HashMap<String, Node>();
		map.put("pl1", new Node(0,0));
		map.put("a22", new Node(2,2));
		map.put("a05", new Node(0,5));
		map.put("dock", new Node(0,0));
		FactoryInstruction.init_map(map);
	}
	
	/**
	 * 
	 * @throws InstructionException
	 */
	@Test
	public void TestMove() throws InstructionException{
		Move m1 = new Move(new Node(0,0), new Node(2,2));
		String string_m1 = "move dock a22";
		assertEquals(m1, FactoryInstruction.create(string_m1));
		
		Move m2 = new Move(new Node(0,5), new Node(2,2));
		String string_m2 = "move a05 a22";
		assertEquals(m2, FactoryInstruction.create(string_m2));
	}
	/**
	 * 
	 * @throws InstructionException
	 */
	@Test
	public void TestPick() throws InstructionException{
		Pick p = new Pick(new Palet(new Node(0,0), true), new Node(0,0));
		String string_p = "pick pl1 dock";
		assertEquals(p, FactoryInstruction.create(string_p));
	}
	/**
	 * 
	 * @throws InstructionException
	 */
	@Test
	public void TestDeliver() throws InstructionException{
		Deliver d = new Deliver(new Palet(new Node(0,0), true));
		String string_d = "deliver pl1";
		assertEquals(d, FactoryInstruction.create(string_d));
	}
	/**
	 * 
	 * @throws InstructionException
	 */
	@Test(expected=exception.InstructionException.class)
	public void TestException() throws InstructionException{
		FactoryInstruction.create("delivre");
	}
}

