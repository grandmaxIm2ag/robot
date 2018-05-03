package controler;

import lejos.robotics.Color;
import utils.Move;
import utils.PointCalculator;
import vue.InputHandler;
import vue.Screen;

/**
 * Visiteur exécutant le déplacement permettant de déposer un palet
 */
public class ExecPlanDeliver extends ExecPlan {

	/**
	 * Constructeur de la classe ExecPlanDeliver
	 * 
	 * @param r le robot
	 * @param i l'inputhandler
	 * @param s l'écran
	 */
	public ExecPlanDeliver(Robot r, InputHandler i, Screen s) {
		super(r, i, s);
	}

	/**
	 * Visite un dépalcement
	 * 
	 * @param m le déplacement
	 * 
	 * @return true si le déplacement s'est ien déroulé
	 * 
	 * @throws Exception Traitée par l'appelant
	 */
	@Override
	public Boolean visit(Move m) throws Exception {
		
		/*
		 * Si on ne se trouve pas sur une ligne verticale, on se déplace sur 
		 * la ligne noire
		 */
		if(!utils.PointCalculator.is_on_vertical_line(robot.getP())) {
			robot.go_to_line(Color.BLACK);
			robot.run(10, true);
		}
		
		//On se retourne vers la ligne de but
		robot.orientate(true);
		
		//On roule jusqu'à la ligne blanche et on dépose le palet
		boolean c = true;
		while(c)
			try{
				robot.followLine(PointCalculator.closestColor(robot.getP()), Math.abs(
						robot.getP().getY() - (robot.isSouth() ? utils.R2D2Constants.
								Y_SOUTH : utils.R2D2Constants.Y_NORTH)), true);
				c = false;
			}catch(exception.LostLineException el){
				int couleur = PointCalculator.closestColor(robot.getP()) == Color.BLACK ?
						Color.RED : Color.BLACK;
				robot.go_to_line(couleur);
				robot.run(5, true);
				robot.orientate(true);
			}
		
		//On se retourne
		robot.orientate(false);
		
		return true;
	}

}
