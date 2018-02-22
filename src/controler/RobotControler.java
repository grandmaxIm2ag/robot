package controler;

import java.io.IOException;
import java.util.List;

import controler.Controler.States;

import lejos.hardware.Button;
import lejos.robotics.Color;
import motor.Graber;
import motor.Propulsion;
import motor.TimedMotor;
import sensor.Bumper;
import sensor.ColorSensor;
import sensor.UltraSon;
import vue.InputHandler;
import vue.Screen;
import utils.Instruction;
import utils.Point;
import utils.R2D2Constants;
public class RobotControler {
	
	Robot robot;
	protected Screen screen;
	protected InputHandler input;
	List<TimedMotor> motors;
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


	private void mainLoop(boolean initLeft) {
		States state          = States.firstMove;
		boolean run           = true;
		boolean unique        = true;
		boolean unique2       = true;
		float   searchPik     = R2D2Constants.INIT_SEARCH_PIK_VALUE;
		boolean isAtWhiteLine = false;
		int     nbSeek        = R2D2Constants.INIT_NB_SEEK;
		boolean seekLeft      = initLeft;
		//Boucle de jeu
		while(run){
			/*
			 * - Quand on part chercher un palet, on mesure le temps de trajet
			 * - Quand on fait le demi tour on parcours ce même temps de trajet
			 * - Si on croise une ligne noire vers la fin du temps de trajet
			 *     S'orienter au nord
			 *     vérifier pendant l'orientation la présence d'une ligne blanche
			 *     si on voit une ligne blanche alors le prochain état sera 
			 *     arrivé à la maison
			 *     sinon le prochain état sera aller à la maison.
			 */
			try{
				for(TimedMotor m : motors){
					m.checkState();
				}
				
			}catch(Throwable t){
				t.printStackTrace();
				run = false;
			}
		}
	}
	
	public List<Instruction> getPlan(){
		throw new java.lang.UnsupportedOperationException("Not yet implemanted");
	}

}
