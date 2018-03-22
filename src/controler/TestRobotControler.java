package controler;

import java.net.SocketException;
import java.util.List;

import lejos.robotics.Color;
import motor.TimedMotor;
import utils.Instruction;
import utils.Palet;
import utils.Point;
import utils.R2D2Constants;
import exception.EmptyArenaException;
import exception.FinishException;
import exception.InstructionException;

/**
 * 
 */
public class TestRobotControler extends RobotControler{

	/**
	 * 
	 */
	public TestRobotControler(){
		super();
		screen.drawText("Information Pour la calibration : ", 
				"Calibrez le robot à south",
				"Calibrez la ligne noire à droite du robot",
				"A la fin de la calibration, positionnez le robot à l'intersection" +
				" rouge/blanc coté nord, c'est à dire à la position (50, 240)");
		input.waitAny();
	}
	
	/**
	 * 
	 */
	public void mainLoop(boolean initLeft) {
		robot.setP(new Point(
				robot.isSouth() ? initLeft ? 150 : 50 : initLeft ? 50 : 150,
				robot.isSouth() ? R2D2Constants.Y_NORTH : R2D2Constants.Y_SOUTH
				));
		System.out.println(robot.getP());
		//Boucle de jeu
		screen.clearDraw();
		screen.drawText("Lancement du robot");
		input.waitAny();
		
		/*
		 * Nous commencons par test le fait que le robot soit capable de prendre 
		 * un palet en face de lui
		 */
		Point point;
		/*screen.drawText("Premier test : ", "On va chercher le palet en face");
		input.waitAny();
		point = new Point(50f, 210f);
		try {
			robot.run(point, true);
		} catch (FinishException e) {
			screen.drawText("Premier test : ", "ÉCHEC");
			e.printStackTrace();
		}
		if(robot.getGraber().isOpen()){
			screen.drawText("Premier test : ", "ÉCHEC, le palet n'a pas été attrapé");
		}else{
			robot.getGraber().open();
			while(robot.getGraber().isRunning())
			robot.getGraber().checkState();
		}
		*/
		/*
		 * Nous voulons maintenant que le robot se tourne ver le premiers palet
		 * à sa droite
		 
		screen.drawText("Second test : ", "On va chercher le palet à droite", 
				"Veuillez positionner le robot sur l'intersection rouge blance, " +
				"aligné sur a ligne rouge");
		input.waitAny();
		point = new Point(100f, 210f);
		try {
			robot.search_palet(point);
			robot.run(point, true);
		} catch (FinishException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			screen.drawText("Second test : ", "ÉCHEC");
		} catch (InstructionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			screen.drawText("Second test : ", "ÉCHEC");
		}
		
		/*
		 * Nous allons mainenant tester le suivi de ligne
		 */
		screen.drawText("Troisième test : ", "On va maintenat suivre la ligne rouge", 
				"Veuillez positionner le robot sur l'intersection rouge blance, " +
				"aligné sur a ligne rouge",
				"Veuillez aussi enlever tous les palets de la ligne rouge");
		input.waitAny();
		robot.followLine(Color.RED, utils.R2D2Constants.LENGTH_ARENA);
		cleanUp();
	}
}