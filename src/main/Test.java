package main;

import java.io.IOException;

import controler.Calibrator;
import controler.Mapper;
import controler.Planner;
import controler.Robot;
import controler.RobotControler;
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

	public static void main(String[] args) throws FinishException, EmptyArenaException, IOException, InstructionException {
		
		//RobotControler controler = new RobotControler();
		/*try {
			controler.robot.run(10, true);
		} catch (FinishException e) {
			e.printStackTrace();
		}*/
		//Calibrator.calibrateCoor(controler.robot.color, 2);
		Robot robot = new Robot(new Point(0,0), false,new ColorSensor(),
				new Propulsion(), new Graber(), new Bumper(), new UltraSon());	
		
		robot.setP(new Point(50,270f));
		robot.setSouth(true);
		robot.setZ(180);
		robot.rotate(30);
		robot.orientate(false);
		/*float z = 3.3960876f;
		Point p = new Point(50,30);
		Point p2 = new Point(-100,90);
		robot.setP(p);
		robot.setZ(z);
		float angle = p.angle(p2);
		robot.rotate(angle);
		System.out.println(angle);
		
		Point p = new Point(50, 30);
		Planner.init(new Mapper(true, true));
		System.out.println(Planner.getPlan(Camera.getPalet(), p, true));*/
	}
}
