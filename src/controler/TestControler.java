package controler;

import java.util.ArrayList;
import java.util.List;
import lejos.hardware.Button;
import lejos.robotics.Color;
import utils.Deliver;
import utils.Instruction;
import utils.Move;
import utils.Palet;
import utils.Pick;
import utils.Point;
import utils.PointCalculator;
import utils.R2D2Constants;

public class TestControler extends RobotControler{

	/**
	 * Constructeur de la classe TestControler
	 */
	public TestControler(){
		super();
		screen.drawText("Préambule", "Calibrer le robot se sortes qu'il se trouve"
				, "sur l'intersection blanche/jeune coté nord");
	}
	
	/**
	 * Boucle de jeu
	 *  
	 * @param initLeft Booléen indiquant si le robot comence à gauche ou à droite
	 * 
	 */
	public void mainLoop(boolean initLeft) {
		robot.setP(new Point(
				robot.isSouth() ? initLeft ? 150 : 50 : initLeft ? 50 : 150,
				robot.isSouth() ? R2D2Constants.Y_NORTH : R2D2Constants.Y_SOUTH
				));
		screen.clearDraw();
		screen.drawText("Lancement du robot");
		int tests = 8, current_test=0;
		try{
			/*
			 * Premier Test :
			 * 
			 *  On va tester la méthode orientate
			 *  
			 *  Suivez les instructions sur le robot, et vérifiez le bon 
			 *  déroulement du test
			 */
			
			robot.setSouth(true);
			robot.setZ(180);
			screen.drawText("Partie I, test I :", "le robot tourne de 30° vers la droite","Press ENTER");
			input.waitOkEscape(Button.ID_ENTER);
			robot.rotate(30);
			screen.drawText("Partie I, test I :", "Le robot retourne sur son "+
					"orientation précédente en tournant sur la gauche","Press ENTER");
			input.waitOkEscape(Button.ID_ENTER);
			robot.orientate(true);
			screen.drawText("Partie I, test I :", "le robot tourne de 30° vers la gauche","Press ENTER");
			input.waitOkEscape(Button.ID_ENTER);
			robot.rotate(-30);
			screen.drawText("Partie I, test I :", "Le robot retourne sur son "+
					"orientation précédente en tournant sur la droite","Press ENTER");
			input.waitOkEscape(Button.ID_ENTER);
			robot.orientate(true);
			
			screen.drawText("Partie I, test I :", "le robot tourne de 30° vers la droite","Press ENTER");
			input.waitOkEscape(Button.ID_ENTER);
			robot.rotate(30);
			screen.drawText("Partie I, test I :", "Le robot retourne à l'opposée de son "+
					"orientation précédente en tournant sur la droite","Press ENTER");
			input.waitOkEscape(Button.ID_ENTER);
			robot.orientate(false);
			screen.drawText("Partie I, test I :", "le robot tourne de 30° vers la gauche","Press ENTER");
			input.waitOkEscape(Button.ID_ENTER);
			robot.rotate(30);
			screen.drawText("Partie I, test I :", "Le robot retourne à l'opposé de son"+
					"orientation précédente en tournant sur la droite","Press ENTER");
			input.waitOkEscape(Button.ID_ENTER);
			robot.orientate(false);
			current_test++;
			
			/*
			 * Deuxième test :
			 * 
			 * on va tester les différente méthodes run
			 * 
			 *  Suivez les instructions sur le robot, et vérifiez le bon 
			 *  déroulement du test
			 */
			
			screen.drawText("Partie I test II : ", "Le robot va devoir avancer de"
					,"60 cm", "Press ENTER");
			input.waitOkEscape(Button.ID_ENTER);
			robot.run(60, true);
			
			screen.drawText("Partie I test II : ", "Le robot va devoir avancer de"
					,"60 pour attraper un palet","Placer le robot sur (50, 270) " +
							"et le palet sur (50, 210)","Press ENTER");
			input.waitOkEscape(Button.ID_ENTER);
			robot.run(60, true);
			robot.setP(new Point(50, 270));
			robot.run(new Point(50,210), true);
			robot.getGraber().open();
			while(robot.getGraber().isRunning()) {
				robot.getGraber().checkState();
			}
			
			screen.drawText("Partie I test II : "
					,"Le robot va devoir avancer jusqu'a la ligne rouge"
					,"Placer le robot face a cette ligne "
					,"Press ENTER");
			input.waitOkEscape(Button.ID_ENTER);
			robot.run_until_color(Color.RED);
			current_test++;
			
			/*
			 * Troisième test :
			 * 
			 * On va tester le suivi de ligne
			 *
			 *  Suivez les instructions sur le robot, et vérifiez le bon 
			 *  déroulement du test
			 */
			
			screen.drawText("Partie I test III : "
					,"Placer le robot sur la ligne"
					,"jaune et enlever tous les palets de cette ligne"
					,"Press ENTER");
			input.waitOkEscape(Button.ID_ENTER);
			robot.setP(new Point(50, 270));
			robot.setZ(180);
			robot.followLine(PointCalculator.closestColor((Point)robot.getP())
					, utils.R2D2Constants.LENGTH_ARENA);
			current_test++;
			
			
			/*
			 * Cinquième test :
			 * 
			 *  On va vérifier le bon comportement de la méthode go_to_line 
			 *  et de ses dépendances.
			 
			 *  Suivez les instructions sur le robot, et vérifiez le bon 
			 *  déroulement du test
			 */
			
			screen.drawText("Partie I test V : ",
					"Le robot va devoir tournr et avancer jusqu'à la ligne noire"
					,"Placer le robot entre la ligne noire et la ligne rouge"
							+"orienté vers le sud"
					,"Press ENTER");
			input.waitOkEscape(Button.ID_ALL);
			robot.setZ(180);
			robot.setP(new Point(utils.R2D2Constants.X_BLACK+20, 50));
			robot.go_to_line(Color.BLACK);
			
			screen.drawText("Partie I test V : "
					,"Placer le robot entre la ligne noire et la ligne jaune"+
							"orienté vers le nord"
					,"Press ENTER");
			input.waitOkEscape(Button.ID_ALL);
			robot.setZ(0);
			robot.setP(new Point(utils.R2D2Constants.X_BLACK-20, 50));
			robot.go_to_line(Color.BLACK);
			current_test++;
			
			/*
			 * Quatrième Test : 
			 * 
			 *  On va tester le méthode de recherche de palet
			 *
			 *  Suivez les instructions sur le robot, et vérifiez le bon 
			 *  déroulement du test
			 */
			
			screen.drawText("Partie I test IV : ",
					"Le robot va devoir avancer de"
					,"80 cm pour attraper un palet après s'être tourener"
					,"Placer le robot sur (50, 270) et le palet sur (100, 210)"
					,"Press ENTER");
			input.waitOkEscape(Button.ID_ENTER);
			Point pal = new Point(100,210);
			robot.setP(new Point(50, 270));
			robot.setZ(180);
			robot.run(10, true);
			robot.setP(new Point(50, 260));
			robot.go_to_line(PointCalculator.closestColor(pal));
			robot.orientate(false);
			float dist = Math.abs(robot.getP().getY() - pal.getY());
			if(dist > 30)	
				robot.followLine(PointCalculator.closestColor(pal), dist-30);
			robot.search_palet(new Point(100,210), 0);
			robot.run(new Point(100,210), true);
			robot.getGraber().open();
			while(robot.graber.isRunning());
				robot.getGraber().checkState();
			
			screen.drawText("FIN DES TESTS", "XP");
		}
		catch(Throwable t){
			screen.clearDraw();
			screen.drawText("ECHEC", current_test+"/"+tests);
			t.printStackTrace();
		}
		cleanUp();
	}
}
