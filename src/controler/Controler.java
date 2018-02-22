package controler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import motor.Graber;
import motor.TimedMotor;
import motor.Propulsion;
import sensor.ColorSensor;
import sensor.Bumper;
import sensor.UltraSon;
import utils.R2D2Constants;
import vue.InputHandler;
import vue.Screen;

import lejos.hardware.Button;
import lejos.robotics.Color;

public class Controler {

	protected ColorSensor    color      = null;
	protected Propulsion     propulsion = null;
	protected Graber         graber     = null;
	protected Bumper pression   = null;
	protected Screen screen;
	protected InputHandler input;
	protected UltraSon   vision     = null;
	
	private ArrayList<TimedMotor> motors     = new ArrayList<TimedMotor>();


	public Controler(){
		propulsion = new Propulsion();
		graber     = new Graber();
		color      = new ColorSensor();
		pression   = new Bumper();
		vision     = new UltraSon();
		screen     = new Screen();
		input      = new InputHandler(screen);
		motors.add(propulsion);
		motors.add(graber);
	}
	
	/**
	 * Lance le robot.
	 * Dans un premier temps, effectue une calibration des capteurs.
	 * Dans un second temps, lance des tests
	 * Dans un troisième temps, démarre la boucle principale du robot pour la 
	 * persycup
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void start() throws IOException, ClassNotFoundException{
		loadCalibration();
		screen.drawText("Calibration", 
				"Appuyez sur echap ","pour skipper");
		boolean skip = input.waitOkEscape(Button.ID_ESCAPE);
		if(skip || calibration()){
			if(!skip){
				saveCalibration();
			}
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
	 * Charge la calibration du fichier de configuration si elle existe
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void loadCalibration() throws FileNotFoundException, IOException, ClassNotFoundException {
		File file = new File("calibration");
		if(file.exists()){
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			color.setCalibration((float[][])ois.readObject());
			graber.setOpenTime((Long)ois.readObject());
			ois.close();
		}
	}

	/**
	 * Sauvegarde la calibration
	 * @throws IOException
	 */
	private void saveCalibration() throws IOException {
		screen.drawText("Sauvegarde", 
				"Appuyez sur le bouton central ","pour valider id",
				"Echap pour ne pas sauver");
		if(input.waitOkEscape(Button.ID_ENTER)){
			File file = new File("calibration");
			if(!file.exists()){
				file.createNewFile();
			}else{
				file.delete();
				file.createNewFile();
			}
			ObjectOutputStream str = new ObjectOutputStream(new FileOutputStream(file));
			str.writeObject(color.getCalibration());
			str.writeObject(graber.getOpenTime());
			str.flush();
			str.close();
		}
	}

	/**
	 * Effectue l'ensemble des actions nécessaires à l'extinction du programme
	 */
	private void cleanUp() {
		if(!graber.isOpen()){
			graber.open();
			while(graber.isRunning()){
				graber.checkState();
			}
		}
		propulsion.runFor(500, true);
		while(propulsion.isRunning()){
			propulsion.checkState();
		}
		color.lightOff();
	}

	
	/**
	 * Lance la boucle de jeu principale
	 * 
	 * Toutes les opérations dans la boucle principale doivent être le plus
	 * atomique possible.
	 * Cette boucle doit s'executer très rapidement.
	 */
	enum States {
		firstMove,
		step2,
		step22,
		playStart,
		isCatching,
		needToRelease,
		isReleasing,
		needToSeek,
		isSeeking,
		needToGrab,
		isGrabing,
		needToRotateEast,
		isRotatingToEast,
		needToRotateWest,
		isRotatingToWest,
		needToGoBackHome,
		isRunningBackHome,
		needToResetInitialSeekOrientation,
		isResetingInitialSeekOrientation,
		needToTurnBackToGoBackHome,
		isTurningBackToGoBackHome,
		needToOrientateNorthToRelease,
		isOrientatingNorthToRealease,
		isAjustingBackHome,
		isGoingToOrientateN}
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
				switch (state) {
				/*
				 * Routine de démarrage du robot :
				 *    Attraper un palet
				 *    Emmener le palet dans le but adverse les roues à cheval
				 *    sur la ligne noire.
				 *    Et passer dans l'état needToResetInitialSeekOrientation
				 */
				case firstMove :
					propulsion.run(true);
					state = States.playStart;
					break;
				case playStart:
					while(propulsion.isRunning()){
						if(pression.isPressed()){
							propulsion.stopMoving();
							graber.close();
						}
					}
					propulsion.rotate(R2D2Constants.ANGLE_START, seekLeft, false);
					while(propulsion.isRunning() || graber.isRunning()){
						propulsion.checkState();
						graber.checkState();
						if(input.escapePressed())
							return;
					}
					propulsion.run(true);
					while(propulsion.isRunning()){
						propulsion.checkState();
						if(input.escapePressed())
							return;
						if(color.getCurrentColor() == Color.WHITE){
							propulsion.stopMoving();
						}
					}
					graber.open();
					while(graber.isRunning()){
						graber.checkState();
						if(input.escapePressed())
							return;
					}
					propulsion.runFor(R2D2Constants.QUARTER_SECOND, false);
					while(propulsion.isRunning()){
						propulsion.checkState();
						if(input.escapePressed())
							return;
					}
					propulsion.halfTurn(seekLeft);
					while(propulsion.isRunning()){
						propulsion.checkState();
						if(input.escapePressed())
							return;
					}
					propulsion.run(true);
					while(propulsion.isRunning()){
						propulsion.checkState();
						if(input.escapePressed())
							return;
						if(color.getCurrentColor() == Color.BLACK){
							propulsion.stopMoving();
						}
					}
					/*
					propulsion.orientateSouth(seekLeft);
					while(propulsion.isRunning()){
						propulsion.checkState();
						if(input.escapePressed())
							return;
					}
					state = States.needToGrab;
					*/
					state = States.needToSeek;
				break;
				/*
				 * Le bsoin de chercher un objet nécessite d'avoir le robot
				 * orienté face à l'ouest du terrain. Le nord étant face au camp
				 * adverse
				 * Le robot va lancer une rotation de 180° en cherchant si un
				 * pic de distances inférieure à 70cm apparait.
				 * Dans ce cas, il fera une recherche du centre de l'objet et
				 * ira l'attraper
				 *
				 * TODO faire en sorte que le robot n'avance pas pour une durée
				 * indeterminée, mais qu'il avance sur un temps de référence
				 * pour 70 cm de trajet au maximum. Comme ça, si l'objet a été
				 * attrapé pendant ce temps ou à disparu, alors il ne roulera
				 * pas dans le vide pour rien
				 */
				case needToSeek:
					state = States.isSeeking;
					searchPik   = R2D2Constants.INIT_SEARCH_PIK_VALUE;
					propulsion.volteFace(seekLeft, R2D2Constants.SEARCH_SPEED);
					isAtWhiteLine = false;
					break;
				case isSeeking:
					float newDist = vision.getRaw()[0];
					//Si la nouvelle distance est inférieure au rayonMaximum et
					//et supérieure au rayon minimum alors
					//on a trouvé un objet à rammaser.
					if(newDist < R2D2Constants.MAX_VISION_RANGE
					   && newDist >= R2D2Constants.MIN_VISION_RANGE){
						if(searchPik == R2D2Constants.INIT_SEARCH_PIK_VALUE){
							if(unique2){
								unique2 = false;
							}else{
								propulsion.stopMoving();
								//TODO, ces 90° peuvent poser problème.
								//Genre, dans le cas où le dernier palet de la recherche
								//a déclenché la recherche du searchPik,
								//du coup on risque de voir le mur.
								//Il serait plus intéressant de faire un rotate
								//west ou east en fonction.
								//Mais bon, on a jamais eu le bug alors ...
								propulsion.rotate(R2D2Constants.QUART_CIRCLE, 
								                  seekLeft, 
								                  R2D2Constants.SLOW_SEARCH_SPEED);
								searchPik = newDist;
							}
						}else{
							if(newDist <= searchPik){
								searchPik = newDist;
							}else{
								propulsion.stopMoving();
								unique2 = true;
								state = States.needToGrab;
							}
						}
					}else{
						searchPik = R2D2Constants.INIT_SEARCH_PIK_VALUE;
					}
					if(!propulsion.isRunning() && state != States.needToGrab){
						nbSeek   += R2D2Constants.STEPS_PER_STAGE;
						if(nbSeek > 10){
							run = false;
						}
						state    = States.needToOrientateNorthToRelease;
						seekLeft = System.currentTimeMillis() % 2 == 0;
					}
					break;
				/*
				 * Le besoin d'attraper un objet correspond au besoin de rouler
				 * sur l'objet pour l'attraper dans les pinces.
				 */
				case needToGrab:
					propulsion.runFor(R2D2Constants.MAX_GRABING_TIME, true);
					state    = States.isGrabing;
					seekLeft = !seekLeft;
					break;
				/*
				 * Le robot est dans l'état isGrabing tant qu'il roule pour
				 * attraper l'objet.
				 */
				case isGrabing:
					//si le temps de roulage est dépassé, s'arrêter aussi
					if(vision.getRaw()[0] < R2D2Constants.COLLISION_DISTANCE ||
					   pression.isPressed()                                  ||
					   !propulsion.isRunning()){
						propulsion.stopMoving();
						state = States.isCatching;
						graber.close();
					}
					break;
				/*
				 * Is catching correspond à l'état où le robot est en train
				 * d'attraper l'objet.
				 * Cet état s'arrête quand les pinces arrêtent de tourner, temps
				 * fonction de la calibration
				 */
				case isCatching:
					if(!graber.isRunning()){
						state = States.needToTurnBackToGoBackHome;
					}
					break;
				/*
				 * Ce état demande au robot de rentrer avec un palet.
				 * Dans un premier temps il effectue un demi tour pour repartir
				 * sur la trajectoire d'où il viens
				 */
				case needToTurnBackToGoBackHome:
					propulsion.volteFace(true, R2D2Constants.VOLTE_FACE_ROTATION);
					state = States.isTurningBackToGoBackHome;
					break;
				case isTurningBackToGoBackHome:
					if(!propulsion.isRunning()){
						state = States.needToGoBackHome;
					}
					break;
				/*
				 * Dans un second temps, le robot va aller en ligne droite pour
				 * rentrer.
				 * Le temps de trajet aller a été mesuré. Nous utilisons cette
				 * mesure pour "prédire" à peux prêt quand est-ce que le robot
				 * va arriver à destination.
				 * Nous allumerons les capteurs de couleurs dans les environs
				 * pour détecter la présence d'une ligne blanche ou d'une ligne
				 * noire et agir en conséquence.
				 *
				 * Si une ligne noire est détectée, alors le robot va s'orienter
				 * face au nord et continuer sa route en direction du camp
				 * adverse.
				 *
				 * Celà permet d'assurer que le robot restera au centre du
				 * terrain.
				 *
				 * Si une ligne blanche est détectée, alors le robot sait qu'il
				 * est arrivé et l'état isRunningBackHome sera évacué
				 */
				case needToGoBackHome:
					propulsion.run(true);
					state = States.isRunningBackHome;
					break;
				case isRunningBackHome:
					if(!propulsion.isRunning()){
						state = States.needToOrientateNorthToRelease;
					}
					if(propulsion.hasRunXPercentOfLastRun(R2D2Constants.ACTIVATE_SENSOR_AT_PERCENT)){
						if(color.getCurrentColor() == Color.WHITE){
							propulsion.stopMoving();
							isAtWhiteLine = true;
							unique        = true;
						}
						if(unique && color.getCurrentColor() == Color.BLACK){
							propulsion.stopMoving();
							unique = false;
							state  = States.isAjustingBackHome;
						}
					}
					break;
				/*
				 * Cet état permet de remettre le robot dans la direction du
				 * nord avant de reprendre sa route
				 */
				case isAjustingBackHome:
					if(!propulsion.isRunning()){
						propulsion.orientateNorth();
						state = States.isGoingToOrientateN;
					}
					break;
				/*
				 * Cet état correspond à l'orientation du robot face au camp
				 * adverse pour continuer sa route.
				 *
				 * Il y a cependant un cas particulier, dans le cas où quand le
				 * robot tourne, si il voit la couleur blanche, c'est qu'il est
				 * arrivé. Dans ce cas, terminer la rotation dans l'état
				 * isOrientatingNorthToRealease.
				 */
				case isGoingToOrientateN:
					if(color.getCurrentColor() == Color.WHITE){
						state = States.isOrientatingNorthToRealease;
					}
					if(!propulsion.isRunning()){
						state = States.needToGoBackHome;
					}
					break;
				/*
				 * Correspond à l'état où le robot s'oriente au nord pour
				 * relâcher l'objet
				 */
				case needToOrientateNorthToRelease:
					state = States.isOrientatingNorthToRealease;
					propulsion.orientateNorth();
					break;
				case isOrientatingNorthToRealease:
					if(!propulsion.isRunning()){
						if(graber.isClose()){
							state = States.needToRelease;
						}else{
							state = States.needToResetInitialSeekOrientation;
						}
					}
					break;
				/*
				 * Ce état correspond, au moment où le robot a besoin de déposer
				 * le palet dans le cap adverse.
				 */
				case needToRelease:
					graber.open();
					state = States.isReleasing;
					break;
				case isReleasing:
					if(!graber.isRunning()){
						state = States.needToResetInitialSeekOrientation;
					}
					break;
				/*
				 * Une fois l'objet rammassé, il faut se remettre en position de
				 * trouver un autre objet.
				 * Le robot fait une marcher arrière d'un certain temps.
				 * Puis fera une mise en face de l'ouest
				 */
				case needToResetInitialSeekOrientation:
					state = States.isResetingInitialSeekOrientation;
					if(isAtWhiteLine){
						propulsion.runFor(R2D2Constants.HALF_SECOND*nbSeek, false);
					}else{
						propulsion.runFor(R2D2Constants.EMPTY_HANDED_STEP_FORWARD, false);
					}
					break;
				case isResetingInitialSeekOrientation:
					if(!propulsion.isRunning()){
						if(seekLeft){
							state = States.needToRotateWest;
						} else {
							state = States.needToRotateEast;
						}
						if(color.getCurrentColor()== Color.WHITE)//fin de partie
							return;
					}
					break;
				/*
				 * Remet le robot face à l'ouest pour recommencer la recherche.
				 * Le robot doit avoir suffisamment reculé pour être dans une
				 * zone où il y aura des palets à ramasser.
				 */
				case needToRotateWest:
					propulsion.orientateWest();
					state = States.isRotatingToWest;
					break;
				case isRotatingToWest:
					if(!propulsion.isRunning()){
						state = States.needToSeek;
					}
					break;
				/*
				 * Remet le robot face à l'est pour recommencer la recherche.
				 * Le robot doit avoir suffisamment reculé pour être dans une
				 * zone où il y aura des palets à ramasser.
				 */
				case needToRotateEast:
					propulsion.orientateEast();
					state = States.isRotatingToWest;
					break;
				case isRotatingToEast:
					if(!propulsion.isRunning()){
						state = States.needToSeek;
					}
					break;
				//Évite la boucle infinie
				}
				if(input.escapePressed())
					run = false;
			}catch(Throwable t){
				t.printStackTrace();
				run = false;
			}
		}
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
			screen.drawText("Calibration", 
						"Appuyez sur ok","pour lancer et arrêter");
			input.waitAny();
			graber.startCalibrate(false);
			input.waitAny();
			graber.stopCalibrate(false);
			screen.drawText("Calibration", 
						"Appuyer sur Entree", "pour commencer la",
						"calibration de l'ouverture");
			input.waitAny();
			screen.drawText("Calibration", 
						"Appuyer sur Entree", "Quand la pince est ouverte");
			graber.startCalibrate(true);
			input.waitAny();
			graber.stopCalibrate(true);

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
			color.lightOn();

			//calibration gris
			screen.drawText("Gris", 
					"Placer le robot sur ","la couleur grise");
			input.waitAny();
			color.calibrateColor(Color.GRAY);

			//calibration rouge
			screen.drawText("Rouge", "Placer le robot ","sur la couleur rouge");
			input.waitAny();
			color.calibrateColor(Color.RED);

			//calibration noir
			screen.drawText("Noir", "Placer le robot ","sur la couleur noir");
			input.waitAny();
			color.calibrateColor(Color.BLACK);

			//calibration jaune
			screen.drawText("Jaune", 
					"Placer le robot sur ","la couleur jaune");
			input.waitAny();
			color.calibrateColor(Color.YELLOW);

			//calibration bleue
			screen.drawText("BLeue", 
					"Placer le robot sur ","la couleur bleue");
			input.waitAny();
			color.calibrateColor(Color.BLUE);

			//calibration vert
			screen.drawText("Vert", "Placer le robot ","sur la couleur vert");
			input.waitAny();
			color.calibrateColor(Color.GREEN);

			//calibration blanc
			screen.drawText("Blanc", "Placer le robot ","sur la couleur blanc");
			input.waitAny();
			color.calibrateColor(Color.WHITE);

			color.lightOff();
			return true;
		}
		return false;
	}
}
