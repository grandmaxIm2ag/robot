package controler;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import exception.EmptyArenaException;
import exception.InstructionException;

import lejos.hardware.Button;
import motor.Graber;
import motor.Propulsion;
import sensor.Bumper;
import sensor.Camera;
import sensor.ColorSensor;
import sensor.UltraSon;
import vue.InputHandler;
import vue.Screen;
import utils.Deliver;
import utils.Instruction;
import utils.Palet;
import utils.Move;
import utils.Pick;
import utils.Point;
import utils.PointCalculator;
import utils.R2D2Constants;
import utils.Visitor;

/**
 * 
 * Classe permettant le contrôle d'un robot
 */
public class RobotControler {
	
	/**
	 * Le robot
	 */
	protected Robot robot;
	/**
	 * L'écran
	 */
	protected Screen screen;
	/**
	 * L'inouthandler
	 */
	protected InputHandler input;
	/**
	 * La liste des palets sur la table
	 */
	protected List<Palet> palets;
	/**
	 * Le nombre de calibration pour les couleurs
	 */
	protected int nb_calibration = 3;
	/**
	 * Booléen indiquant que le robot ramène le premier palet
	 */
	protected boolean first_move = true;
	/**
	 * Booléen indiquant que le robot doit ramener un palet attrapé
	 */
	protected boolean deliver_move = false;
	
	/**
	 * Constructeur de la classe RobotControler
	 */
	public RobotControler(){
		robot = new Robot(new Point(0,0), false,new ColorSensor(),
				new Propulsion(), new Graber(), new Bumper(), new UltraSon());
		screen     = new Screen();
		input      = new InputHandler(screen);
		palets = new ArrayList<Palet>();
	}
	
	/**
	 * Démarre la partie
	 * 
	 * @throws IOException Traitée par l'appelant
	 * @throws ClassNotFoundException Traitée par l'appelant
	 */
	public void start() throws IOException, ClassNotFoundException{
		if(calibration()){
			screen.drawText("Calibration Placement", 
					"Appuyez sur OK si","vous êtes au sud",
					"Appuyez sur toute autre si","vous êtes au nord");
			robot.setSouth(input.isThisButtonPressed(input.waitAny(), Button.ID_ENTER));
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
	public void cleanUp() {
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
	public boolean calibration() {
		return calibrationGrabber() && calibrationCouleur();
	}

	/**
	 * Calibre la pince
	 * 
	 * @return vrai si tout c'est bien passé.
	 */
	public boolean calibrationGrabber() {
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
	public boolean calibrationCouleur() {
		screen.drawText("Calibration", 
						"Préparez le robot à la ","calibration des couleurs",
						"Appuyez sur le bouton central ","pour continuer");
		if(input.waitOkEscape(Button.ID_ENTER)){
			Calibrator.calibrateCoor(robot.color, nb_calibration);
			return true;
		}
		return false;
	}

	/**
	 * Boucle de jeu
	 *  
	 * @param initLeft Booléen indiquant si le robot comence à gauche ou à droite
	 * @throws SocketException 
	 * 
	 */
	public void mainLoop(boolean initLeft) throws SocketException {
		Camera.init_camera();
		Planner.init(new Mapper(true, robot.isSouth()));
		boolean run = true;
		robot.setP(new Point(
				robot.isSouth() ? initLeft ? 150 : 50 : initLeft ? 50 : 150,
				robot.isSouth() ? R2D2Constants.Y_NORTH : R2D2Constants.Y_SOUTH
				));
		
		//Boucle de jeu
		screen.clearDraw();
		screen.drawText("Lancement du robot");
		input.waitAny();
		Visitor<Boolean> plan_norm = new ExecPlan(robot, input, screen);
		Visitor<Boolean> plan_first_pick = new ExecFirstPick(robot, input, screen);
		Visitor<Boolean> plan_first = new ExecFirstPlan(robot, input, screen);
		Visitor<Boolean> plan_deliver = new ExecPlanDeliver(robot, input, screen);
		while(run){
			try{
				palets.clear();
				palets = Camera.getPalet();
				screen.clearDraw();
				screen.drawText("En attente d'un plan", "Pos : "+robot.getP(),
						"south : "+robot.isSouth());
				List<Instruction> plan = Planner.getPlan(palets, robot.getP(), robot.isSouth());
				accept(plan, first_move ? plan_first_pick : plan_norm,
						first_move ? plan_first : plan_deliver);
				first_move = false;
			}catch(InstructionException e){
				//On recalcule le plan
				e.printStackTrace(System.err);
				run = false;
				continue;
			}catch(EmptyArenaException e){
				//Il n'y a plus aucun palet prenable sur le terrain
				screen.drawText("FIn", "Il n'y a plus aucun palet");
				run = false;
			}catch(exception.FinishException e){
				//Il n'y a plus aucun palet prenable sur le terrain
				screen.drawText("Fin", "Le robot va être stopé");
				run = false;
			}catch(SocketException se){
				screen.drawText("Problème :", "Connexion à la caméra impossible");
				run = false;
			}
			catch(Throwable t){
				t.printStackTrace();
				run = false;
			}
		}
		cleanUp();
	}
	
	
	/**
	 * Accepte un visteur renvoyant des booléens
	 * 
	 * @param v
	 * @param plan
	 * @throws Exception
	 */
	public void accept(List<Instruction> plan, Visitor<Boolean> pick, 
			Visitor<Boolean> deliver) throws Exception{
		Iterator<Instruction> it = plan.iterator();
		
		while(it.hasNext()){
			Instruction ins = it.next();
			boolean move = (ins instanceof Move);
			if(move) {
				Point p = (Point)((Move)ins).getNext();
				deliver_move = PointCalculator.is_on_goal(p, robot.isSouth());
			}else {
				deliver_move = false;
			}
			String trace = ins.toString()+"\n";
			trace+=move && deliver_move ? deliver : pick;
			if(! ins.accept(move && deliver_move ? deliver : pick)){
				throw new InstructionException("L'instruction a échouée");
			}
		}
		
	}

	/**
	 * @return the palets
	 */
	public List<Palet> getPalets() {
		return palets;
	}

	/**
	 * @param palets the palets to set
	 */
	public void setPalets(List<Palet> palets) {
		this.palets = palets;
	}

}
