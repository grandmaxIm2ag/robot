package controler;

import utils.Deliver;
import utils.Instruction;
import utils.Move;
import utils.Pick;
import utils.Point;
import utils.Visitor;
import vue.InputHandler;
import vue.Screen;

public class ExecPlan implements Visitor<Boolean> {
	/**
	 * 
	 */
	protected Robot robot;
	/**
	 * 
	 */
	protected InputHandler input;
	/**
	 * 
	 */
	protected Screen screen;
	/**
	 * 
	 * @param r
	 */
	public ExecPlan(Robot r, InputHandler i, Screen s){
		robot = r;
		input = i;
		screen = s;
	}
	/**
	 * 
	 */
	@Override
	public Boolean visit(Instruction i) throws Exception {
		throw new exception.InstructionException("Instruction non traîtée");
	}
	/**
	 * 
	 */
	@Override
	public Boolean visit(Move m) throws Exception {
		try{
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
	 * 
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
	 * 
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
