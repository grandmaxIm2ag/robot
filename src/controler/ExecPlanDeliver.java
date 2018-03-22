package controler;

import utils.Move;
import utils.Point;
import vue.InputHandler;
import vue.Screen;

public class ExecPlanDeliver extends ExecPlan {

	public ExecPlanDeliver(Robot r, InputHandler i, Screen s) {
		super(r, i, s);
	}

	/**
	 * 
	 */
	@Override
	public Boolean visit(Move m) throws Exception {
		//On se retourne vers la ligne de but
		robot.orientate(true);
		//On roule jusqu'à la ligne blanche et on dépose le palet
		robot.followLine(robot.closestColor(), robot.getP().distance((Point)
				m.getNext()), true);
		//On se retourne
		robot.orientate(false);
		return true;
	}

}
