package controler;

import lejos.hardware.Button;
import lejos.robotics.Color;
import motor.Graber;
import sensor.ColorSensor;
import vue.InputHandler;
import vue.Screen;

public class Calibrator {
	
	static public Screen screen = new Screen();
	static public InputHandler input = new InputHandler(screen);
	

	public static void InitCalibrator() {}
	
	/**
	 * Lance la calibration pour la pince, la calibration fini avec les pinces
	 * ouvertes
	 * 
	 * @param g : la pince
	 * @param b : booléen indiquant si la pince est ouverte ou fermée
	 */
	public static void celibrateGrapber(Graber g, boolean isOpen){
		if(isOpen){
			g.startCalibrate(false);
			input.waitAny();
			g.stopCalibrate(false);
		}
		g.startCalibrate(true);
		input.waitAny();
		g.stopCalibrate(true);
	}

	/**
	 * Lance la calibration pour le capteur de couleur
	 * 
	 * @param color : le capteur de couleur
	 * @param nbIter : le nombre d'itération pour chaque couleur
	 */
	public static void calibrateCoor(ColorSensor color, int nbIter){
		screen.drawText("Calibration");
		Button.waitForAnyPress();
		screen.clearDraw();
		if(true){
			
			color.lightOn();
			
			for(int i=1; i<=nbIter; i++){
				//calibration gris
				screen.drawText("Gris ");
				Button.waitForAnyPress();
				color.calibrateColor(Color.GRAY);
			}
			
			for(int i=1; i<=nbIter; i++){
				//calibration blue
				screen.clearDraw();
				screen.drawText("Bleu ");
				Button.waitForAnyPress();
				color.calibrateColor(Color.BLUE);
			}
			
			for(int i=1; i<=nbIter; i++){
				//calibration noir
				screen.clearDraw();
				screen.drawText("Noir ");
				Button.waitForAnyPress();
				color.calibrateColor(Color.BLACK);
			}
			
			for(int i=1; i<=nbIter; i++){
				//calibration jaune
				screen.clearDraw();
				screen.drawText("Jaune ");
				Button.waitForAnyPress();
				color.calibrateColor(Color.YELLOW);
			}
			
			for(int i=1; i<=nbIter; i++){
				//calibration green
				screen.clearDraw();
				screen.drawText("Vert ");
				Button.waitForAnyPress();
				color.calibrateColor(Color.GREEN);
			}
			
			for(int i=1; i<=nbIter; i++){
				//calibration red
				screen.clearDraw();
				screen.drawText("Rouge ");
				Button.waitForAnyPress();
				color.calibrateColor(Color.RED);
			}
			
			for(int i=1; i<=nbIter; i++){
				//calibration white
				screen.clearDraw();
				screen.drawText("Blanc ");
				Button.waitForAnyPress();
				color.calibrateColor(Color.WHITE);
			}
		}
	}

}
