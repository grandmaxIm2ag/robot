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
import motor.TimedMotor;
import sensor.Bumper;
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
import utils.R2D2Constants;
import utils.Visitor;
public class RobotControler {
	
	protected Robot robot;
	protected Screen screen;
	protected InputHandler input;
	protected List<Palet> palets;
	protected List<TimedMotor> motors;
	protected int nb_calibration = 1;
	private boolean first_move = true;
	private boolean deliver_move = false;
	
	
	public RobotControler(){
		robot = new Robot(new Point(0,0), false,new ColorSensor(),
				new Propulsion(), new Graber(), new Bumper(), new UltraSon());
		screen     = new Screen();
		input      = new InputHandler(screen);
		motors = new ArrayList<TimedMotor>();
		motors.add(robot.getGraber());
		motors.add(robot.getPropulsion());
		palets = new ArrayList<Palet>();
	}
	
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
	 * 
	 * @param initLeft Booléen indiquant si le robot comence à gauche ou à droite
	 * 
	 */
	public void mainLoop(boolean initLeft) {
		/////////////////////////////////////////////////////////
		//////A MODIFIER LORS DE L'UTIISATION DE LA CAMERA///////
		/////////////////////////////////////////////////////////
		//Camera.init_camera();
		Planner.init(new Mapper(true, robot.isSouth()));
		boolean run = true;
		robot.setP(new Point(
				robot.isSouth() ? initLeft ? 150 : 50 : initLeft ? 50 : 150,
				robot.isSouth() ? R2D2Constants.Y_NORTH : R2D2Constants.Y_SOUTH
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
				//////				ET DU PLANNER				  ///////
				/////////////////////////////////////////////////////////
				//palets.clear();
				//palets = Camera.getPalet();
				//List<Instruction> plan = Planner.getPlan(palets, robot.getP(), robot.isSouth());
				List<Instruction> plan = new ArrayList<Instruction>();
				plan.add(new Move(robot.getP(), new Point(50, 210)));
				plan.add(new Pick(new Palet(new Point(50, 210), true), new Point(50, 210)));
				plan.add(new Move(new Point(50,210), new Point(50,30)));
				plan.add(new Deliver(new Palet(new Point(50, 210), true)));
				plan.add(new Move(new Point(50, 30), new Point(50, 90)));
				plan.add(new Pick(new Palet(new Point(50, 90), true), new Point(50, 90)));
				plan.add(new Move(new Point(50,90), new Point(50,30)));
				plan.add(new Deliver(new Palet(new Point(50, 90), true)));
				plan.add(new Move(new Point(50, 30), new Point(100, 90)));
				plan.add(new Pick(new Palet(new Point(100, 90), true), new Point(100, 90)));
				plan.add(new Move(new Point(100,90), new Point(100,30)));
				plan.add(new Deliver(new Palet(new Point(100, 90), true)));
				plan.add(new Move(new Point(150, 30), new Point(150, 90)));
				plan.add(new Pick(new Palet(new Point(150, 90), true), new Point(150, 90)));
				plan.add(new Move(new Point(150,90), new Point(150,30)));
				plan.add(new Deliver(new Palet(new Point(150, 90), true)));
				accept(plan);
				
				//A modifier !!!!!!!!
				run = false;
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
	public void accept(List<Instruction> plan) throws Exception{
		Iterator<Instruction> it = plan.iterator();
		int i=0;
		Visitor<Boolean> plan_pick = new ExecPlan(robot, input, screen);
		Visitor<Boolean> plan_first = new ExecFirstPlan(robot, input, screen);
		Visitor<Boolean> plan_deliver = new ExecPlanDeliver(robot, input, screen);
		while(it.hasNext() && isFeasible(plan, i)){
			Instruction ins = it.next();
			boolean move = (ins instanceof Move);
			String trace = ins.toString()+"\n";
			trace += deliver_move && move ? (first_move? "plan_first": 
				"plan_deliver") : "plan_pick";
			System.out.println(trace);
			Visitor<Boolean> v = deliver_move && move ? (first_move? plan_first: 
				plan_deliver) : plan_pick;
			if(! ins.accept(v)){
				throw new InstructionException("L'instruction a échouée");
			}
			if(deliver_move && move){
				first_move = false;
			}
			if(move)
				deliver_move = !deliver_move;
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
