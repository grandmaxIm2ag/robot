package unit_test;

import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import utils.*;
import controler.Planner;
import controler.Mapper;
import exception.EmptyArenaException;
/**
 * 
 */
public class TestPlanner {
	/**
	 * 
	 */
	List<Palet> palet;
	
	/**
	 * 
	 */
	@Before
	public void init(){
		Planner.init(new Mapper(true,true));
		palet = new ArrayList<Palet>();
		palet.add(new Palet(new Point(50, 90), true));
		palet.add(new Palet(new Point(100, 90), true));
		palet.add(new Palet(new Point(150, 90), true));
	}
	
	/**
	 * 
	 * @throws EmptyArenaException
	 */
	@Test
	public void testGetPlan1() throws EmptyArenaException{
		Point p = new Point(50,30);
		
		List<Instruction> expected_plan = new ArrayList<Instruction>();
		
		expected_plan.add(new Move(p, palet.get(0).getP()));
		expected_plan.add(new Pick(palet.get(0), palet.get(0).getP()));
		expected_plan.add(new Move(palet.get(0).getP(), p));
		expected_plan.add(new Deliver(palet.get(0)));
		
		List<Instruction> plan = Planner.getPlan(palet, p, true);
		assertEquals(plan.size(), expected_plan.size());
		Iterator<Instruction> it = plan.iterator();
		for(Instruction ins : expected_plan){
			assertEquals(ins, it.next());
		}
	}
	
	/**
	 * 
	 * @throws EmptyArenaException
	 */
	@Test
	public void testGetPlan2() throws EmptyArenaException{
		Point p = new Point(100,30);
		
		List<Instruction> expected_plan = new ArrayList<Instruction>();
		expected_plan.add(new Move(p, palet.get(1).getP()));
		expected_plan.add(new Pick(palet.get(1), palet.get(1).getP()));
		expected_plan.add(new Move(palet.get(1).getP(), new Point(100, 30)));
		expected_plan.add(new Deliver(palet.get(1)));
		
		List<Instruction> plan = Planner.getPlan(palet, p, true);
		
		assertEquals(plan.size(), expected_plan.size());
		Iterator<Instruction> it = plan.iterator();
		for(Instruction ins : expected_plan){
			assertEquals(ins, it.next());
		}
	}
	
	/**
	 * 
	 * @throws EmptyArenaException
	 */
	@Test
	public void testGetPlan3() throws EmptyArenaException{
		Point p = new Point(150,30);
		
		List<Instruction> expected_plan = new ArrayList<Instruction>();
		expected_plan.add(new Move(p, palet.get(2).getP()));
		expected_plan.add(new Pick(palet.get(2), palet.get(2).getP()));
		expected_plan.add(new Move(palet.get(2).getP(), new Point(150, 30)));
		expected_plan.add(new Deliver(palet.get(2)));
		
		
		List<Instruction> plan = Planner.getPlan(palet, p, true);
		assertEquals(plan.size(), expected_plan.size());
		Iterator<Instruction> it = plan.iterator();
		for(Instruction ins : expected_plan){
			assertEquals(ins, it.next());
		}
	}
	/**
	 * 
	 * @throws EmptyArenaException
	 */
	@Test(expected=EmptyArenaException.class)
	public void testGetPlanEmptyArena() throws EmptyArenaException{
		Point p = new Point(50,30);
		Planner.getPlan(new ArrayList<Palet>(), p, true);
	}
	
	/**
	 * 
	 */
	@Test
	public void testPointToNodeSouth() {
		Mapper m = new Mapper(true,true);
		Point p = new Point(25, 60);
		Node n = new Node(2,2);
		assertEquals(n, m.pointToNode(p));
	}
	
}
