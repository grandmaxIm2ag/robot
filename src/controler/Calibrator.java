package controler;

import lejos.hardware.Button;
import lejos.robotics.Color;
import motor.Graber;
import sensor.ColorSensor;
import vue.InputHandler;
import vue.Screen;

/**
 * 
 *Classe permettant de calibrer le robot avant chaque partie
 */
public class Calibrator {
	/**
	 * L'écran du robot
	 */
	static public Screen screen = new Screen();
	/**
	 * 
	 */
	static public InputHandler input = new InputHandler(screen);
	
	
	/**
	 * Lance la calibration pour la pince, la calibration fini avec les pinces
	 * ouvertes
	 * 
	 * @param g : la pince
	 * @param isOpen : booléen indiquant si la pince est ouverte ou fermée
	 */
	public static void celibrateGrapber(Graber g, boolean isOpen){
		screen.drawText("Calibration", 
				"Appuyez sur ok","pour lancer et arrêter");
		input.waitAny();
		if(isOpen){ 
			g.startCalibrate(false);
			input.waitAny();
			g.stopCalibrate(false);
		}
		screen.drawText("Calibration", 
				"Appuyer sur Entree", "pour commencer la",
				"calibration de l'ouverture");
		input.waitAny();
		screen.drawText("Calibration", 
				"Appuyer sur Entree", "Quand la pince est ouverte");
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
				screen.drawText("Gris "+i+"/"+nbIter);
				Button.waitForAnyPress();
				color.calibrateColor(Color.GRAY);
			}
			
			
			for(int i=1; i<=nbIter; i++){
				//calibration noir
				screen.clearDraw();
				screen.drawText("Noir "+i+"/"+nbIter);
				Button.waitForAnyPress();
				color.calibrateColor(Color.BLACK);
			}
			
			for(int i=1; i<=nbIter; i++){
				//calibration jaune
				screen.clearDraw();
				screen.drawText("Jaune "+i+"/"+nbIter);
				Button.waitForAnyPress();
				color.calibrateColor(Color.YELLOW);
			}
			
			
			for(int i=1; i<=nbIter; i++){
				//calibration red
				screen.clearDraw();
				screen.drawText("Rouge "+i+"/"+nbIter);
				Button.waitForAnyPress();
				color.calibrateColor(Color.RED);
			}
			
			for(int i=1; i<=nbIter; i++){
				//calibration white
				screen.clearDraw();
				screen.drawText("Blanc "+i+"/"+nbIter);
				Button.waitForAnyPress();
				color.calibrateColor(Color.WHITE);
			}
			
			for(int i=1; i<=nbIter; i++){
				//calibration vert
				screen.clearDraw();
				screen.drawText("Vert "+i+"/"+nbIter);
				Button.waitForAnyPress();
				color.calibrateColor(Color.GREEN);
			}
			
			for(int i=1; i<=nbIter; i++){
				//calibration bleu
				screen.clearDraw();
				screen.drawText("Bleu "+i+"/"+nbIter);
				Button.waitForAnyPress();
				color.calibrateColor(Color.BLUE);
			}
		}
	}

}
