package controler;

import lejos.robotics.Color;
import utils.Move;
import utils.Point;
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
		if(!robot.is_on_vertical_line()) {
			robot.go_to_line(Color.BLACK);
		}
		
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
