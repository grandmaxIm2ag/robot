package main;

import sensor.Bumper;
import sensor.ColorSensor;
import sensor.UltraSon;
import utils.Point;
import lejos.utility.Delay;
import motor.Graber;
import motor.Propulsion;
import controler.Calibrator;
import controler.Robot;
import controler.RobotControler;

public class Homologation {
	
	public static void main(String[] args){
		Robot robot = new Robot(new Point(0,0), false,new ColorSensor(),
				new Propulsion(), new Graber(), new Bumper(), new UltraSon());
		try{
			Calibrator.calibrateCoor(robot.getColor(), 2);
			robot.homologation();
		}catch(Throwable e){
			e.printStackTrace();
			Delay.msDelay(10000);
		}
		System.exit(0);
	}
}
