package controler;

import utils.Move;
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
	 * Méthode visit pour un move
	 * 
	 * @param m le mouvement à effectuer
	 */
	@Override
	public Boolean visit(Move m) throws Exception {
		//On va à la ligne blanche
		float angle = -30;
		float dist1 = 40;
		float dist2 = 100;
		
		//On se décale à la droite de la ligne contenant le palet
		robot.rotate(angle);
		robot.run(dist1, true);
		robot.rotate((-angle)*1.3f);
		
		//On avant sur un metre
		robot.run(dist2, true);
		
		//On se déplace jusqu'à la ligne à suivre
		robot.rotate(-angle);
		robot.run_until_color(robot.closestColor());
		robot.orientate(true);
		
		//On suit la ligne
		robot.followLine(robot.closestColor(), dist2, true);
		
		//On se retourne
		robot.orientate(false);
		return true;
	}

}
