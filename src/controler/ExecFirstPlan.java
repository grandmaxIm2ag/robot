package controler;

import lejos.robotics.Color;
import utils.Deliver;
import utils.Move;
import utils.Point;
import utils.PointCalculator;
import vue.InputHandler;
import vue.Screen;

public class ExecFirstPlan extends ExecPlan {

	public ExecFirstPlan(Robot r, InputHandler i, Screen s) {
		super(r, i, s);
	}

	/**
	 * 
	 */
	@Override
	public Boolean visit(Move m) throws Exception {
		//On va à la ligne blanche
		float angle = 30;
		float dist1 = 20;
		float dist2 = 120;
		robot.rotate(angle);
		robot.run(dist1, true);
		robot.rotate(-angle);
		robot.run(dist2, true);
		robot.rotate(-angle);
		robot.run_until_color(robot.closestColor());
		robot.followLine(robot.closestColor(), dist2, true);
		//On se retourne
		robot.orientate(false);
		return true;
	}

}
