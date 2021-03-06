package controler;

import lejos.robotics.Color;
import utils.Move;
import utils.Point;
import utils.PointCalculator;
import vue.InputHandler;
import vue.Screen;

/**
 * 
 * Classe héritant de Visitor permettant d'exécuter le move
 * pour le dépot du premier palet
 */
public class ExecFirstPlan extends ExecPlan {
	
	/**
	 * Constructeur de la classe ExecFirstPlan
	 * 
	 * @param r Le robot
	 * @param i L'inputhandler
	 * @param s M'écran
	 */
	public ExecFirstPlan(Robot r, InputHandler i, Screen s) {
		super(r, i, s);
	}

	/**
	 * Méthode visit pour un move précédent le premier deliver
	 * 
	 * @param m le mouvement à effectuer
	 */
	@Override
	public Boolean visit(Move m) throws Exception {
		
		//On se décale à la droite de la ligne contenant le palet
		robot.orientate_east();
		robot.run(20, true);
		robot.orientate(true);
		robot.setP(new Point(robot.getP().getX() - 5,  robot.getP().getY()));
		
		//On avance jusqu'à la dernière ligne horizontale
		robot.run_until_color(robot.isSouth() ? Color.GREEN : Color.BLUE, 100);
		robot.run(20, true);
		
		//On se déplace jusqu'à la ligne à suivre
		robot.go_to_line(PointCalculator.closestColor(robot.getP()));
		robot.run(5, true);
		robot.orientate(true);
		
		//On suit la ligne
		robot.followLine(PointCalculator.closestColor(robot.getP()), 60,true);
		
		//On se retourne
		System.out.println("angle avant rotation : "+robot.getZ());
		robot.orientate(false);
		return true;
	}
}
