package controler;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import exception.EmptyArenaException;
import exception.InstructionException;

import lejos.hardware.Button;
import lejos.robotics.navigation.Move;
import motor.Graber;
import motor.Propulsion;
import motor.TimedMotor;
import sensor.Bumper;
import sensor.Camera;
import sensor.ColorSensor;
import sensor.UltraSon;
import vue.InputHandler;
import vue.Screen;
import utils.Instruction;
import utils.Palet;
import utils.Point;
import utils.R2D2Constants;
public class RobotControler {
	
	protected Robot robot;
	protected Screen screen;
	protected InputHandler input;
	protected List<Palet> palets;
	protected List<TimedMotor> motors;
	
	
	public RobotControler(){
		robot = new Robot(new Point(0,0), false,new ColorSensor(),
				new Propulsion(), new Graber(), new Bumper(), new UltraSon());
		screen     = new Screen();
		input      = new InputHandler(screen);
		motors = new ArrayList<TimedMotor>();
		motors.add(robot.getGraber());
		motors.add(robot.getPropulsion());
	}
	
	public void start() throws IOException, ClassNotFoundException{
		
		
		screen.drawText("Calibration", 
				"Appuyez sur OK pour commencer");
		input.waitAny();
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
			Calibrator.calibrateCoor(robot.color, 1);
			return true;
		}
		return false;
	}


	private void mainLoop(boolean initLeft) throws SocketException {
		/////////////////////////////////////////////////////////
		//////A MODIFIER LORS DE L'UTIISATION DE LA CAMERA///////
		/////////////////////////////////////////////////////////
		//Camera.init_camera();
		Planner.init(new Mapper(true, robot.isSouth()));
		boolean run = true;
		robot.setP(new Point(
				robot.isSouth() ? initLeft ? 150 : 50 : initLeft ? 50 : 150,
				robot.isSouth() ? R2D2Constants.Y_SOUTH : R2D2Constants.Y_NORTH 
				));
		//Boucle de jeu
		screen.clearDraw();
		screen.drawText("Lancement du robot");
		while(run){
			try{
				for(TimedMotor m : motors){
					m.checkState();
				}
				/////////////////////////////////////////////////////////
				//////A MODIFIER LORS DE L'UTIISATION DE LA CAMERA///////
				/////////////////////////////////////////////////////////
				//palets = Camera.getPalet();
				palets.clear();
				palets.add(new Palet(new Point(5,5), true));
				palets.add(new Palet(new Point(15,15), true));
				List<Instruction> plan = Planner.getPlan(palets, robot.getP(), robot.isSouth());
				if (plan == null){
					run = false;
				}else{
					Visitor<Boolean> v = new ExecPlan(robot, input, screen);
					accept(v, plan);
				}
				
			}catch(InstructionException e){
				//On recalcule le plan
				e.printStackTrace(System.err);
				run = false;
				continue;
			}catch(EmptyArenaException e){
				//Il n'y a plus aucun palet prenable sur le terrain
				screen.drawText("FIn", "Il n'y a plus aucun palet");
				run = false;
				return;
			}catch(exception.FinishException e){
				//Il n'y a plus aucun palet prenable sur le terrain
				screen.drawText("Fin", "Le robot va être stopé");
				run = false;
				return;
			}catch(Throwable t){
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
	public void accept(Visitor<Boolean> v, List<Instruction> plan) throws Exception{
		Iterator<Instruction> it = plan.iterator();
		int i=0;
		while(it.hasNext() && isFeasible(plan, i)){
			Instruction ins = it.next();
			if(! ins.accept(v)){
				throw new InstructionException("L'instruction a échouée");
			}
			i++;
		}
	}
	
	/**
	 * Verifie que les n-i dernières instructions d'un plan sont réalisables
	 * 
	 * @param plan
	 * @param i
	 * @return
	 */
	public boolean isFeasible(List<Instruction> plan , int i){
		boolean b = true;
		for(int j =i; j<plan.size(); j++){
			if(plan.get(j) instanceof utils.Pick){
				boolean b1 = false;
				for(Palet p : palets){
					b1 |= p.getP().equals(((utils.Pick)plan.get(j)).getCoord()); 
				}
				b &= b1;
			}
		}
		//////////////////////////A MODIFIER////////////////////////////
		b = true;
		return b;
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
