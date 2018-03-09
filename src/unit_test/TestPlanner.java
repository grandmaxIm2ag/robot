package unit_test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import utils.Deliver;
import utils.Instruction;
import utils.Move;
import utils.Palet;
import utils.Pick;
import utils.Point;
import utils.PointCalculator;
import controler.Planner;
import controler.Mapper;
import exception.EmptyArenaException;

public class TestPlanner {

	@Before
	public void init(){
		Planner.init(new Mapper(true,true));
	}
	
	/////////////////////////////////////////////////////////
	/////A MODIFIER LORSQUE LE PLANNER SERA OPERATIONNEL/////
	/////////////////////////////////////////////////////////
	@Test(expected=EmptyArenaException.class)
	public void testGetPlan() throws EmptyArenaException{
		Iterator<Instruction> it_expected;
		Iterator<Instruction> it_ret;
		List<Instruction> ret;
		List<Instruction> expected;
		List<Palet> palets;
		
		//Test avec un seul palet
		palets = new ArrayList<Palet>();
		palets.add(new Palet(new Point(5,5), true));
		expected = new ArrayList<Instruction>();
		expected.add(new Move(new Point(0,0), new Point(5,5)));
		expected.add(new Pick(new Palet(new Point(5,5), true), new Point (5,5)));
		expected.add(new Deliver(new Palet(new Point(5,5), true)));
		ret = Planner.getPlan(palets, new Point(0,0), true);
		it_expected = expected.iterator();
		it_ret = ret.iterator();
		assertEquals(ret.size(), expected.size());
		while(it_expected.hasNext()){
			assertEquals(it_expected.next(), it_ret.next());
		}
		
		//Test avec plusieurs palets
		palets = new ArrayList<Palet>();
		palets.add(new Palet(new Point(5,5), true));
		palets.add(new Palet(new Point(15,15), true));
		expected = new ArrayList<Instruction>();
		expected.add(new Move(new Point(0,0), new Point(5,5)));
		expected.add(new Pick(new Palet(new Point(5,5), true), new Point (5,5)));
		expected.add(new Deliver(new Palet(new Point(5,5), true)));
		Point p = PointCalculator.getWhiteLinePoint(true, 5);
		ret = Planner.getPlan(palets, new Point(0,0), true);
		it_expected = expected.iterator();
		it_ret = ret.iterator();
		assertEquals(ret.size(), expected.size());
		while(it_expected.hasNext()){
			assertEquals(it_expected.next(), it_ret.next());
		}
		//Test sans palet
		palets = new ArrayList<Palet>();
		Planner.getPlan(palets,new Point(0,0), true);
	}
}
