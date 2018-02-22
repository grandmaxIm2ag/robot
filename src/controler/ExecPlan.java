package controler;

import utils.Deliver;
import utils.Instruction;
import utils.Move;
import utils.Point;

public class ExecPlan implements Visitor<Boolean> {
	/**
	 * 
	 */
	Robot robot;
	
	/**
	 * 
	 * @param r
	 */
	public ExecPlan(Robot r){
		robot = r;
	}
	/**
	 * 
	 */
	@Override
	public Boolean visit(Instruction i) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * 
	 */
	@Override
	public Boolean visit(Move m) throws Exception {
		// TODO Auto-generated method stub
		try{
			Point p = (Point)m.getNext();
			float a = robot.getP().angle(p);
			a = robot.getZ() - a;
			robot.getPropulsion().rotate(a, a<0, true);
			float dist = p.distance(robot.getP());
			//robot.getPropulsion().
			return true;
		}catch(Exception e){
			e.printStackTrace(System.err);
			return false;
		}
	}
	/**
	 * 
	 */
	@Override
	public Boolean visit(Deliver d) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
