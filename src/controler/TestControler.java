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
			 * Les tests vont être effectués en deux parties :
			 * 
			 * 1- tester les différentes méthodes de le classe robot
			 * 2- tester différent plans simples
			 */
			
			///////////////////////////////////////////////////////////////////
			///////						PARTIE I						///////
			///////////////////////////////////////////////////////////////////
						
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
					,"rouge et enlever tous les palets de cette ligne"
					,"Press ENTER");
			input.waitOkEscape(Button.ID_ENTER);
			robot.setP(new Point(50, 270));
			robot.setZ(180);
			robot.followLine(robot.closestColor(), utils.R2D2Constants.LENGTH_ARENA);
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
					,"60 cm pour attraper un palet après s'être tourener"
					,"Placer le robot sur (50, 270) et le palet sur (100, 210)"
					,"Press ENTER");
			input.waitOkEscape(Button.ID_ENTER);
			robot.setP(new Point(50, 270));
			robot.setZ(180);
			robot.search_palet(new Point(100,210));
			robot.run(new Point(100,210), true);
			robot.getGraber().open();
			
			/*
			 * Cinquièm test :
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
			robot.setZ(180);
			
			robot.go_to_line(Color.BLACK);
			
			screen.drawText("Partie I test V : "
					,"Placer le robot entre la ligne noire et la ligne jaune"+
							"orienté vers le nord"
					,"Press ENTER");
			robot.setZ(0);
			robot.setP(new Point(utils.R2D2Constants.X_BLACK+20, 50));
			robot.go_to_line(Color.BLACK);
			current_test++;
			
			///////////////////////////////////////////////////////////////////
			///////						PARTIE II						///////
			///////////////////////////////////////////////////////////////////
			/*
			 * Premier Test :
			 * 
			 * On va vérifier que le robot est capable d'effectuer un Move
			 * pour récupérer un palet
			 * 
			 * Nous allons donc créer un plan contenant seulement un Move et 
			 * un Pick, et allons tester que ce plan se déroule correctement.
			 * Le palet se trouveras en face du robot.
			 * 
			 * Suivre les instructions sur le robot pour la préparation :
			 */
			screen.drawText("Partie II Test I"
					,"Placer le robot à l'intersection blanc/jaune"
					,"orienté vers le sud");
			input.waitOkEscape(Button.ID_ENTER);
			robot.setP(new Point(utils.R2D2Constants.X_YELLOW,
					utils.R2D2Constants.Y_NORTH));
			this.deliver_move = false;
			robot.setZ(utils.R2D2Constants.SOUTH);
			input.waitOkEscape(Button.ID_ENTER);
			List<Instruction> plan = new ArrayList<Instruction>();
			Point p = new Point(utils.R2D2Constants.X_RED,
					utils.R2D2Constants.Y_NORTH-60);
			plan.add(new Move(robot.getP(), p));
			plan.add(new Pick(new Palet(p,true), p));
			accept(plan);
			current_test++;
			
			
			/*
			 * Deuxième Test : 
			 * 
			 * On va vérifier que le robot est capable de rammener le 
			 * palet précédement récupéré.
			 * 
			 * On va donc créer un plan plannifiant un déplacement et un deliver
			 */
			
			screen.drawText("Partie II Test II");
			robot.setP(new Point(utils.R2D2Constants.X_YELLOW,
					utils.R2D2Constants.Y_NORTH-60));
			this.deliver_move = true;
			robot.setZ(utils.R2D2Constants.SOUTH);
			input.waitOkEscape(Button.ID_ENTER);
			plan = new ArrayList<Instruction>();
			p = new Point(utils.R2D2Constants.X_YELLOW,
					utils.R2D2Constants.Y_NORTH);
			plan.add(new Move(robot.getP(), p));
			plan.add(new Deliver(new Palet(p,true)));
			accept(plan);
			current_test++;
			
			/*
			 * troisème Test : 
			 * 
			 * On va vérifier que le robot est capable de rammener le 
			 * palet précédement récupéré.
			 * 
			 * On va donc créer un plan plannifiant un déplacement et un deliver
			 */
			
			screen.drawText("Partie III Test 1"
					,"Placer le robot à l'intersection blanc/jaune"
					,"orienté vers le sud et un palet en face");
			robot.setP(new Point(utils.R2D2Constants.X_YELLOW,
					utils.R2D2Constants.Y_NORTH));
			robot.run(new Point(50, 210), true);
			this.deliver_move = true;
			this.first_move = true;
			robot.setZ(utils.R2D2Constants.SOUTH);
			input.waitOkEscape(Button.ID_ENTER);
			plan = new ArrayList<Instruction>();
			p = new Point(utils.R2D2Constants.X_YELLOW,
					utils.R2D2Constants.Y_SOUTH);
			plan.add(new Move(robot.getP(), p));
			plan.add(new Deliver(new Palet(robot.getP(),true)));
			accept(plan);
			current_test++;
			
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
