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
			float a = robot.getP().angle(p);
			a = robot.getZ() - a;
			screen.drawText("Angle : ",a+"");
			robot.getPropulsion().rotate(a, a<0, true);
			float dist = p.distance(robot.getP());
			screen.drawText("Distance : ",dist+"");
			robot.getPropulsion().rotate(a*0.85f, false, true);
			robot.setZ(robot.getZ()+(a*0.85f));
			while(robot.getPropulsion().isRunning()){
				if(input.escapePressed()){
					throw new exception.FinishException();
				}
			}
			while(Math.abs(dist-robot.getVision().getRaw()[0]*100) > 
							utils.R2D2Constants.ERROR){
				robot.getPropulsion().rotate((float)a/10, false, true);
				while(robot.getPropulsion().isRunning()){
					if(input.escapePressed()){
						throw new exception.FinishException();
					}
				}
			}
			robot.setZ(robot.getPropulsion().getOrientation());
			robot.getPropulsion().runDist(dist+utils.R2D2Constants.ERROR);
			while(robot.getPropulsion().isRunning()){
				if(robot.getPression().isPressed()){
					robot.getPropulsion().stopMoving();
					robot.getGraber().close();
				}
				if(input.escapePressed()){
					throw new exception.FinishException();
				}
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
			}
			dist = robot.getP().getY() - utils.R2D2Constants.Y_SOUTH + utils.R2D2Constants.ERROR;
			
		}else{
			robot.getPropulsion().orientateNorth();
			while(robot.getPropulsion().isRunning()){
				if(input.escapePressed()){
					throw new exception.FinishException();
				}
			}
			dist = robot.getP().getY() - utils.R2D2Constants.Y_NORTH + utils.R2D2Constants.ERROR;
		} 
		if(dist > robot.getVision().getRaw()[0]*100){
			robot.getPropulsion().rotate(90, true, true);
			while(robot.getPropulsion().isRunning()){
				if(input.escapePressed()){
					throw new exception.FinishException();
				}
			}
			robot.getPropulsion().runDist(utils.R2D2Constants.DIST_AVOID_OBSTACLE);
			while(robot.getPropulsion().isRunning()){
				if(input.escapePressed()){
					throw new exception.FinishException();
				}
			}
			robot.getPropulsion().rotate(90, true, true);
			while(robot.getPropulsion().isRunning()){
				if(input.escapePressed()){
					throw new exception.FinishException();
				}
			}
		}
		robot.getPropulsion().runDist(dist);
		robot.getPropulsion().rotate(90, true, true);
		while(robot.getPropulsion().isRunning()){
			if(robot.getColor().getCurrentColor() == Color.WHITE){
				robot.getPropulsion().stopMoving();
			}
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
