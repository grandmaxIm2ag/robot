package controler;

import utils.Move;
import utils.Point;
import utils.PointCalculator;
import vue.InputHandler;
import vue.Screen;

public class ExecFirstPick extends ExecPlan {

	public ExecFirstPick(Robot r, InputHandler i, Screen s) {
		super(r, i, s);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Visite un déplacement
	 * 
	 * @param m le mouvement à visiter
	 * 
	 * @return false si le palet n'est pas trouver, true sinon
	 */
	@Override
	public Boolean visit(Move m) throws Exception {
		//On cherche le palet
		Point point = (Point)m.getNext();
		float angle = robot.getP().angle(point);
		robot.orientate(angle);
		robot.run((Point) m.getNext(), true);
		robot.setP(PointCalculator.getPointFromAngle(robot.getP(), robot.
				getPropulsion().getTraveledDist()/10, robot.getZ()));
		System.out.println("Point du palet : "+robot.getP());
		return true;
	}
}
