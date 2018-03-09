package main;

import motor.Graber;
import motor.Propulsion;
import sensor.Bumper;
import sensor.ColorSensor;
import sensor.UltraSon;
import utils.Point;
import controler.Robot;

public class TestRunDist {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Robot robot = new Robot(new Point(0,0), false,new ColorSensor(),
				new Propulsion(), new Graber(), new Bumper(), new UltraSon());
		robot.getPropulsion().runDist(10);
		while(robot.getPropulsion().isRunning()) robot.getPropulsion().chech_dist();
		robot.getPropulsion().runDist(10, false);
		while(robot.getPropulsion().isRunning()) robot.getPropulsion().chech_dist();
	}

}
