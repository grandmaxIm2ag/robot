package unit_test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import utils.Point;

import controler.Robot;


public class TestControler {
	Robot r;
	
	@Before
	public void init(){
		r = new Robot(new Point(0,0),true,null,null,null,null,null);
	}
	@Test
	public void testDistAndAngle() {
		Point p = new Point(-5,0);
		assertTrue(5f == r.getP().distance(p));
		p = new Point(5,5);
		System.out.println(r.getP().angle(p));
		assertTrue(45f == r.getP().angle(p));
	}

}
