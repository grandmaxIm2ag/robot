package controler;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import exception.EmptyArenaException;
import exception.InstructionException;
import utils.*;
import sensor.Camera;
import utils.Instruction;
import utils.Palet;
import utils.Point;
import utils.R2D2Constants;
import utils.Visitor;

public class HardRobotControler extends RobotControler {

	/**
	 * Boucle de jeu
	 *  
	 * @param initLeft Booléen indiquant si le robot comence à gauche ou à droite
	 * @throws SocketException 
	 * 
	 */
	public void mainLoop(boolean initLeft) throws SocketException {
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
		int iter= 1;
		first_move = false;
		while(run){
			try{
				System.out.println(robot.isSouth());
				first_move = false;
				robot.setP(new Point(60,260));
				robot.setZ(140);
				palets = new ArrayList<Palet>();
				screen.clearDraw();
				screen.drawText("En attente d'un plan", "Pos : "+robot.getP(),
						"south : "+robot.isSouth());
				List<Instruction> plan = new ArrayList<Instruction>();
				
				//plan.add(new Move(new Point(150,210), new Point(50,30)));
				plan.add(new Pick(new Palet(new Point(50,210), true), new Point(50,210)));
				/*plan.add(new Move(new Point(50,210), new Point(50,270)));
				plan.add(new Deliver(new Palet(new Point(50,210), true)));
				
				plan.add(new Move(new Point(50,270), new Point(100,210)));
				plan.add(new Pick(new Palet(new Point(100,210), true), new Point(100,210)));
				plan.add(new Move(new Point(100,210), new Point(100,270)));
				plan.add(new Deliver(new Palet(new Point(100,210), true)));
				
				plan.add(new Move(new Point(100,270), new Point(150,210)));
				plan.add(new Pick(new Palet(new Point(150,210), true), new Point(150,210)));
				plan.add(new Move(new Point(150,210), new Point(150,270)));
				plan.add(new Deliver(new Palet(new Point(50,210), true)));
				
				plan.add(new Move(new Point(150,270), new Point(150,150)));
				plan.add(new Pick(new Palet(new Point(150,150), true), new Point(150,150)));
				plan.add(new Move(new Point(150,150), new Point(150,270)));
				plan.add(new Deliver(new Palet(new Point(150,150), true)));
				
				plan.add(new Move(new Point(150, 270), new Point(100,150)));
				plan.add(new Pick(new Palet(new Point(100,150), true), new Point(100,150)));
				plan.add(new Move(new Point(100,150), new Point(100,270)));
				plan.add(new Deliver(new Palet(new Point(100,150), true)));
				
				plan.add(new Move(new Point(100, 270), new Point(50,150)));
				plan.add(new Pick(new Palet(new Point(50,150), true), new Point(50,150)));
				plan.add(new Move(new Point(50,150), new Point(50,270)));
				plan.add(new Deliver(new Palet(new Point(50,150), true)));
				*/
				accept(plan, first_move ? plan_first_pick : plan_norm,
						first_move ? plan_first : plan_deliver);
				
				run = false;
			}catch(InstructionException e){
				//On recalcule le plan
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
}
