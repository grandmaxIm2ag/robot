package controler;

import lejos.robotics.Color;
import utils.Deliver;
import utils.Instruction;
import utils.Move;
import utils.Pick;
import utils.Point;
import utils.PointCalculator;
import utils.R2D2Constants;
import utils.Visitor;
import vue.InputHandler;
import vue.Screen;

/**
 * 
 * Classe héritant de Visitor permettant d'exécuter un plan
 */
public class ExecPlan implements Visitor<Boolean> {
	/**
	 * Le robot à controller
	 */
	protected Robot robot;
	/**
	 * l'inputhandler
	 */
	protected InputHandler input;
	/**
	 * L'écran du robot
	 */
	protected Screen screen;
	/**
	 * Constructeur de la classe ExecPlan
	 * 
	 * @param r le robot
	 * @param i l'inputhandler
	 * @param s l'écran
	 */
	public ExecPlan(Robot r, InputHandler i, Screen s){
		robot = r;
		input = i;
		screen = s;
	}
	/**
	 * Visite une instruction
	 * 
	 * @param i l'instruction à visiter
	 * 
	 * @return true si l'instruction s'est normalement dérouler
	 * @throws exception Traitée par l'appelant
	 */
	@Override
	public Boolean visit(Instruction i) throws Exception {
		throw new exception.InstructionException("Instruction non traîtée");
	}
	
	/**
	 * Visite un déplacement
	 * 
	 * @param m le mouvement à visiter
	 * 
	 * @return false si le palet n'est pas trouver, true sinon
	 * 
	 * @throws exception Traitée par l'appelant, ce sont toutes les exceptions
	 * retourné par search_palet que ne sont pas des InstructionException
	 */
	@Override
	public Boolean visit(Move m) throws Exception {
		try{
			float max_dist = 55;
			
			//On cherche le palet
			Point point = (Point)m.getNext();
			float angle = robot.getP().angle(point);
			//System.out.println("angle : "+angle);
			System.out.println("z = "+robot.getZ());
			System.out.println(angle);
			robot.orientate(angle);
			float d = 0;
			if(robot.getP().distance(point) > max_dist) {
				robot.run(robot.getP().distance(point)-max_dist, true);
				d = robot.getPropulsion().getTraveledDist()/10;
			
				robot.setP(PointCalculator.getPointFromAngle(robot.getP(), d, robot.getZ()));
				point = (Point)m.getNext();
				angle = robot.getP().angle(point);
				robot.orientate(angle);
				System.out.println("z2 = "+robot.getZ());
				System.out.println(angle);
				System.out.println(robot.getP());
				float dd = robot.balayage((Point)m.getNext());
				System.out.println("palet a :"+dd);
				robot.search_palet(dd);
				robot.propulsion.change_rotation_speed(R2D2Constants.MAX_ROTATION_SPEED);
			}
		}catch(exception.InstructionException e){
			/*
			 * Si la recherche du palet plannifiée échoue
			 * alors l'exécution du plan échoue
			 */
			return true;
		}catch(Exception e){
			/*
			 * Pour toutes les autres exceptions, 
			 * on les fait remonter à l'appelant
			 */
			throw e;
		}
		robot.run((Point) m.getNext(), true);
		robot.setP((Point)m.getNext());
		return true;
	}
	
	/**
	 * Vérifie que le robot est bien arrivé à la ligne blanche pour déposer le 
	 * palet
	 * 
	 * @param d le dépot à visiter
	 * 
	 * @return true si les pinces sont ouvertes
	 * 
	 * @throws exception Traité par l'appelant
	 */
	@Override
	public Boolean visit(Deliver d) throws Exception {
		boolean b = robot.getGraber().isOpen();
		if(!b){
			robot.getGraber().open();
			while(robot.getGraber().isRunning())
				robot.getGraber().checkState();
			robot.orientate(false);
		}
		return b;
	}
	
	/**
	 * Vérifie qu'un palet a été attrapé
	 * 
	 * @param p
	 * 
	 * @return true si les pinces sont fermées
	 * 
	 * @throws exception Traité par l'appelant
	 */
	@Override
	public Boolean visit(Pick p) throws Exception {
		boolean b =robot.graber.isClose();
		if(!b){
			//robot.orientate(true);
			//robot.run(5, true);
			//robot.setP(PointCalculator.getPointFromAngle(robot.getP(), 5, robot.getZ()));
			int color = PointCalculator.closestColor(robot.getP()) == Color.BLACK
					? Color.RED : Color.BLACK;
			robot.go_to_line(color);
			robot.run(5, true);
			robot.orientate(true);
			
			robot.followLine(color, 210, false);
			robot.orientate(false);
		}else {
			robot.run(6.5f, true);
		}
		return b;
	}
	

}
