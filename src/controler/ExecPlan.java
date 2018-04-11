package controler;

import utils.Deliver;
import utils.Instruction;
import utils.Move;
import utils.Pick;
import utils.Point;
import utils.PointCalculator;
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
			float max_dist = 35;
			
			//On cherche le palet
			Point point = (Point)m.getNext();
			float angle = robot.getP().angle(point);
			//System.out.println("angle : "+angle);
			robot.rotate(angle);
			float d = 0;
			if(robot.getP().distance(point) > max_dist) {
				robot.run(robot.getP().distance(point)-max_dist, true);
				d = robot.getPropulsion().getTraveledDist()/10;
			}
			
			robot.search_palet((Point) m.getNext(), d);
		}catch(exception.InstructionException e){
			/*
			 * Si la recherche du palet plannifiée échoue
			 * alors l'exécution du plan échoue
			 */
			return false;
		}catch(Exception e){
			/*
			 * Pour toutes les autres exceptions, 
			 * on les fait remonter à l'appelant
			 */
			throw e;
		}
		robot.run((Point) m.getNext(), true);
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
			robot.orientate(false);
		}
		return b;
	}
	

}
