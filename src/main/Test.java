package main;

import java.io.IOException;

import controler.Calibrator;
import controler.Mapper;
import controler.Planner;
import controler.Robot;
import exception.EmptyArenaException;
import exception.FinishException;
import exception.InstructionException;
import lejos.robotics.Color;
import motor.Graber;
import motor.Propulsion;
import sensor.Bumper;
import sensor.Camera;
import sensor.ColorSensor;
import sensor.UltraSon;
import utils.Point;
import utils.PointCalculator;
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
	public static void main(String[] args) throws FinishException, EmptyArenaException, IOException, InstructionException {

		Robot robot = new Robot(new Point(0,0), false,new ColorSensor(),
				new Propulsion(), new Graber(), new Bumper(), new UltraSon());
		float z = 3.3960876f;
		Point p = new Point(50,30);
		Point p2 = new Point(0,90);
		robot.setP(p);
		robot.setZ(z);
		float angle = z-p.angle(p2);
		robot.rotate(angle);
		/*Camera.init_camera();
		Point p = new Point(50, 30);
		Planner.init(new Mapper(true, true));
		System.out.println(Planner.getPlan(Camera.getPalet(), p, true));*/
	}
}
