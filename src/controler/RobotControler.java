package controler;

import java.io.IOException;

import lejos.hardware.Button;
import motor.Graber;
import motor.Propulsion;
import sensor.Bumper;
import sensor.ColorSensor;
import sensor.UltraSon;
import vue.InputHandler;
import vue.Screen;
import utils.Point;
public class RobotControler {
	
	Robot robot;
	protected Screen screen;
	protected InputHandler input;
	
	public RobotControler(){
		robot = new Robot(new Point(0,0), false,new ColorSensor(),
				new Propulsion(), new Graber(), new Bumper(), new UltraSon());
		screen     = new Screen();
		input      = new InputHandler(screen);
	}
	
	public void start() throws IOException, ClassNotFoundException{
		screen.drawText("Calibration", 
				"Appuyez sur entrer pour commencer");
		input.waitAny();
		if(calibration()){
			screen.drawText("Lancer", 
				"Appuyez sur OK si la","ligne noire est à gauche",
				"Appuyez sur tout autre", "elle est à droite");
			if(input.isThisButtonPressed(input.waitAny(), Button.ID_ENTER)){
				mainLoop(true);
			}else{
				mainLoop(false);
			}
		}
		cleanUp();
	}
	
	/**
	 * Effectue l'ensemble des actions nécessaires à l'extinction du programme
	 */
	private void cleanUp() {
		if(!robot.graber.isOpen()){
			robot.graber.open();
			while(robot.graber.isRunning()){
				robot.graber.checkState();
			}
		}
		robot.propulsion.runFor(500, true);
		while(robot.propulsion.isRunning()){
			robot.propulsion.checkState();
		}
		robot.color.lightOff();
	}

	/**
	 * S'occupe d'effectuer l'ensemble des calibrations nécessaires au bon
	 * fonctionnement du robot.
	 * 
	 * @return vrai si tout c'est bien passé.
	 */
	private boolean calibration() {
		return calibrationGrabber() && calibrationCouleur();
	}

	private boolean calibrationGrabber() {
		screen.drawText("Calibration", 
						"Calibration de la fermeture de la pince",
						"Appuyez sur le bouton central ","pour continuer");
		if(input.waitOkEscape(Button.ID_ENTER)){
			Calibrator.celibrateGrapber(robot.graber, true);

		}else{
			return false;
		}
		return true;
	}

	/**
	 * Effectue la calibration de la couleur
	 * @return renvoie vrai si tout c'est bien passé
	 */
	private boolean calibrationCouleur() {
		screen.drawText("Calibration", 
						"Préparez le robot à la ","calibration des couleurs",
						"Appuyez sur le bouton central ","pour continuer");
		if(input.waitOkEscape(Button.ID_ENTER)){
			Calibrator.calibrateCoor(robot.color, 5);
			return true;
		}
		return false;
	}

	public void mainLoop(boolean b){
		
	}
}
