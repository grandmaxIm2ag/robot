package main;

import controler.Robot;
import exception.FinishException;
import motor.Graber;
import motor.Propulsion;
import sensor.Bumper;
import sensor.ColorSensor;
import sensor.UltraSon;
import utils.Point;

public class Test {
	public static void main(String[] args) throws FinishException {
		Robot robot = new Robot(new Point(0,0), false,new ColorSensor(),
				new Propulsion(), new Graber(), new Bumper(), new UltraSon());
		controler.Calibrator.celibrateGrapber(robot.getGraber(), true);
		System.out.println(robot.getGraber().isRunning());
		robot.run(new Point(0, 100), true);
	}
}
