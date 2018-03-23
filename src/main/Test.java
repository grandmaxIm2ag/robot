package main;

import motor.Graber;
import motor.Propulsion;
import sensor.Bumper;
import sensor.ColorSensor;
import sensor.UltraSon;
import utils.Point;
import controler.Robot;
import exception.FinishException;
import exception.InstructionException;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Robot robot = new Robot(new Point(0,0), false,new ColorSensor(),
				new Propulsion(), new Graber(), new Bumper(), new UltraSon());
		robot.setP(new Point(0,0));
		try {
			System.out.println("GO !!");
			//robot.getPression()
			while(robot.getGraber().isOpen()){
				System.out.println("coucou");
				if(robot.getPression().isPressed()){
					System.out.println("C'est partir");
					robot.getGraber().close();
					while(robot.getGraber().isRunning());
				}
			}
			
		} catch (Exception e /*FinishException | InstructionException e*/) {
			System.exit(0);
		}
	}

}
