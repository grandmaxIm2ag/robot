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
			
			//On avance de 10 centimètres
			robot.run(10,  true);
			
			//On va jusqu'à la ligne du palet
			robot.go_to_line(PointCalculator.closestColor((Point)m.getNext()));
			robot.run(10, true);
			robot.orientate(false);
			float dist = Math.abs(robot.getP().getY()-((Point)m.getNext()).getY
					());
			
			//On se rapproche du palet à attraper
			if(dist > 30) {
				dist-=30;
				robot.followLine(PointCalculator.closestColor((Point)m.getNext
						()), dist);
				robot.setP(new Point(PointCalculator.getWhiteLinePoint(true, 
						PointCalculator.closestColor((Point)m.getNext())).getX(),
						robot.isSouth() ? robot.getP().getY() + dist : robot.
								getP().getY() - dist));
			}
			
			
			//On cherche le palet
			robot.search_palet((Point) m.getNext());
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
