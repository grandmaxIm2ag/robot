package unit_test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import motor.Graber;
import motor.Propulsion;

import org.junit.Before;
import org.junit.Test;

import sensor.Bumper;
import sensor.ColorSensor;
import sensor.UltraSon;
import utils.Palet;
import utils.Point;

import controler.Robot;
import controler.RobotControler;
import exception.EmptyArenaException;


public class TestControler {
	Robot robot;
	RobotControler control;
	
	@Before
	public void init(){
		robot = new Robot(new Point(0,0), false,null,null,null,null,null);
		control = new RobotControler();
	}
	@Test
	public void testDistAndAngle() {
		Point p = new Point(-5,0);
		System.out.println(robot.getP().distance(p));
		assertTrue(5f == robot.getP().distance(p));
		p = new Point(5,5);
		//assertTrue(45f == robot.getP().angle(p));
	}

}
