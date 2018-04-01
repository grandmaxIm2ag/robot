package main;

import controler.Calibrator;
import controler.Robot;
import exception.FinishException;
import lejos.robotics.Color;
import motor.Graber;
import motor.Propulsion;
import sensor.Bumper;
import sensor.ColorSensor;
import sensor.UltraSon;
import utils.Point;
import utils.R2D2Constants;

public class Test {
	static float orientation = R2D2Constants.EAST;
	public static double getRotateToNorth(){
		if(orientation<-R2D2Constants.HALF_CIRCLE){
			return Math.abs(orientation)-R2D2Constants.FULL_CIRCLE;
		}else if(orientation > R2D2Constants.HALF_CIRCLE){
			return R2D2Constants.FULL_CIRCLE-orientation;
		}else{
			return orientation*-1;
		}	
	}
	public static void main(String[] args) throws FinishException {

		Robot robot = new Robot(new Point(0,0), false,new ColorSensor(),
				new Propulsion(), new Graber(), new Bumper(), new UltraSon());
		System.out.println("init "+robot.getZ());
		//Calibrator.calibrateCoor(robot.getColor(), 2);
		robot.setSouth(false);
		System.out.println("set "+robot.getZ());
		//robot.rotate(90);
		System.out.println("rotate "+robot.getZ());
		//robot.orientate_west();
		robot.orientate_east();
		//robot.orientate_south();
		robot.orientate_north();
		System.out.println("north "+robot.getZ());
		//robot.orientate(true);
		//System.out.println("orientate "+robot.getZ());
		//robot.orientate_east();
		/*robot.run(20, true);
		robot.orientate(true);
		robot.run_until_color(Color.GREEN);
		robot.go_to_line(Color.YELLOW);
		robot.orientate(true);
		robot.followLine(Color.YELLOW, 150);*/
	}
}
