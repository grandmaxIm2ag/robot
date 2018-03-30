package unit_test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import utils.Node;
import utils.Point;

/**
 * 
 *
 */
public class TestCoord {
	/**
	 * 
	 */
	private Point p;
	/**
	 * 
	 */
	private Node n;
	
	/**
	 * 
	 */
	@Before
	public void init() {
		p = new Point(0,0);
		n = new Node(0,0);
	}
	
	/**
	 * 
	 */
	@Test
	public void testPoint(){
		assertTrue(p.equals(new Point(0,0)));
		assertFalse(p.equals(new Point(0,1)));
		assertFalse(p.equals(new Point(1,0)));
		
		assertTrue(p.distance(new Point(0,5))==5);
		assertTrue(p.distance(new Point(0,5))==5);
		/*
		 * Pour ce test il faut caster Math.sqrt(50) en float pour éviter le 
		 * erreurs d'arrondi
		 */
		assertTrue(p.distance(new Point(5,5))==(float)Math.sqrt(50));
		
		assertTrue(p.angle(new Point(0,5))==0);
		assertTrue(p.angle(new Point(0,-5))==180);
		assertTrue(p.angle(new Point(5,5))==45);
		assertTrue(p.angle(new Point(-5,5))==-45);
	}
	
	/**
	 * 
	 */
	@Test
	public void testNode(){
		assertTrue(n.equals(new Node(0,0)));
		assertFalse(n.equals(new Node(0,1)));
		assertFalse(n.equals(new Node(1,0)));
		
		assertTrue(n.compareTo(new Node(0,0))==0);
		assertTrue(n.compareTo(new Node(0, 61))==1);
	}
}
