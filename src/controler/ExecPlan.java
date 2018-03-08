package controler;

import lejos.robotics.Color;
import utils.Deliver;
import utils.Instruction;
import utils.Move;
import utils.Pick;
import utils.Point;
import utils.R2D2Constants;
import vue.InputHandler;
import vue.Screen;

public class ExecPlan implements Visitor<Boolean> {
	/**
	 * 
	 */
	Robot robot;
	/**
	 * 
	 */
	InputHandler input;
	/**
	 * 
	 */
	Screen screen;
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
		// TODO Auto-generated method stub
		try{
			Point p = (Point)m.getNext();
			System.out.println(robot.getP()+" "+p);
			float a = robot.getP().angle(p);
			a = robot.getZ() - a;
			a*=utils.R2D2Constants.ANGLE_ERROR;
			System.out.println("Angle : "+a+"");
			robot.getPropulsion().rotate(a, a<0, true);
			float dist = p.distance(robot.getP());
			System.out.println("Distance : "+dist+"");
			robot.getPropulsion().rotate(a, false, true);
			boolean b = true;
			while(robot.getPropulsion().isRunning()){
				if(input.escapePressed()){
					throw new exception.FinishException();
				}
				if( Math.abs(dist-robot.getVision().getRaw()[0]*100) > 
											utils.R2D2Constants.ERROR){
					robot.getPropulsion().stopMoving();
					b = false;
				}
				robot.getPropulsion().checkState();
			}
			robot.setZ(robot.getPropulsion().getOrientation());
			if(b){
				return false;
			}
			robot.getPropulsion().runDist(dist+utils.R2D2Constants.ERROR);
			while(robot.getPropulsion().isRunning()){
				if(robot.getPression().isPressed()){
					robot.getPropulsion().stopMoving();
					robot.getGraber().close();
					while(robot.getGraber().isRunning()){
						robot.getGraber().checkState();
					}
				}
				if(input.escapePressed()){
					throw new exception.FinishException();
				}
				robot.getPropulsion().chech_dist();
			}
			robot.setP((Point)m.getNext());
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
		float dist;
		if(robot.isSouth()){
			robot.getPropulsion().orientateSouth(true);
			while(robot.getPropulsion().isRunning()){
				if(input.escapePressed()){
					throw new exception.FinishException();
				}
				robot.getPropulsion().checkState();
			}
			dist = robot.getP().getY() - utils.R2D2Constants.Y_SOUTH + utils.R2D2Constants.ERROR;
			
		}else{
			robot.getPropulsion().orientateNorth();
			while(robot.getPropulsion().isRunning()){
				if(input.escapePressed()){
					throw new exception.FinishException();
				}
				robot.getPropulsion().checkState();
			}
			dist = robot.getP().getY() - utils.R2D2Constants.Y_NORTH + utils.R2D2Constants.ERROR;
		} 
		if(dist > robot.getVision().getRaw()[0]*100){
			robot.getPropulsion().rotate(90, true, true);
			while(robot.getPropulsion().isRunning()){
				if(input.escapePressed()){
					throw new exception.FinishException();
				}
				robot.getPropulsion().checkState();
			}
			robot.getPropulsion().runDist(utils.R2D2Constants.DIST_AVOID_OBSTACLE);
			while(robot.getPropulsion().isRunning()){
				if(input.escapePressed()){
					throw new exception.FinishException();
				}
				robot.getPropulsion().chech_dist();
			}
			robot.getPropulsion().rotate(90, true, true);
			while(robot.getPropulsion().isRunning()){
				if(input.escapePressed()){
					throw new exception.FinishException();
				}
				robot.getPropulsion().checkState();
			}
		}
		robot.getPropulsion().runDist(dist);
		while(robot.getPropulsion().isRunning()){
			if(robot.getColor().getCurrentColor() == Color.WHITE){
				robot.getPropulsion().stopMoving();
			}
			robot.getPropulsion().chech_dist();
			if(input.escapePressed()){
				throw new exception.FinishException();
			}
		}
		robot.getGraber().open();
		while(robot.getGraber().isRunning()){
			robot.getGraber().checkState();
			if(input.escapePressed())
				throw new exception.FinishException();
		}
		robot.propulsion.runFor(R2D2Constants.QUARTER_SECOND, false);
		while(robot.propulsion.isRunning()){
			robot.propulsion.checkState();
			if(input.escapePressed())
				throw new exception.FinishException();
		}
		robot.propulsion.halfTurn(true);
		while(robot.propulsion.isRunning()){
			robot.propulsion.checkState();
			if(input.escapePressed())
				throw new exception.FinishException();
		}
		robot.setZ(robot.getPropulsion().getOrientation());
		robot.setP(new Point(robot.getP().getX(),robot.isSouth() ? 
				utils.R2D2Constants.Y_SOUTH + 5: utils.R2D2Constants.Y_NORTH - 5));
		return true;
	}
	
	@Override
	public Boolean visit(Pick p) throws Exception {
		return robot.graber.isClose();
	}
	

}
