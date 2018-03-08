package main;
import lejos.robotics.Color;
import motor.Graber;
import motor.Propulsion;
import sensor.Bumper;
import sensor.ColorSensor;
import sensor.UltraSon;
import utils.Point;
import vue.InputHandler;
import vue.Screen;
import controler.Calibrator;
import controler.Robot;
public class TestFollowLine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Screen screen     = new Screen();
		InputHandler input      = new InputHandler(screen);
		Robot robot = new Robot(new Point(0,0), false,new ColorSensor(),
				new Propulsion(), new Graber(), new Bumper(), new UltraSon());
		Calibrator.calibrateCoor(robot.getColor(), 2);
		screen.clearDraw();
		input.waitAny();
		robot.followLine(Color.YELLOW, 390);
	}

}
