package controler;

import exception.FinishException;
import exception.InstructionException;
import exception.LostLineException;
import lejos.robotics.Color;
import motor.Graber;
import motor.Propulsion;
import sensor.Bumper;
import sensor.ColorSensor;
import sensor.UltraSon;
import utils.Point;
import utils.PointCalculator;
import utils.R2D2Constants;
import vue.InputHandler;
import vue.Screen;

public class Robot {
	/**
	 * L'orientation du robot
	 */
	float z;
	/**
	 * La position du robot
	 */
	protected Point p;
	/**
	 * Indique si la zone d'en-but est au sud 
	 */
	protected boolean south;
	/**
	 * Le capteur de couleur
	 */
	protected ColorSensor    color      = null;
	/**
	 * Les moteurs des roues
	 */
	protected Propulsion     propulsion = null;
	/**
	 * La pince
	 */
	protected Graber         graber     = null;
	/**
	 * Le capteur de pression
	 */
	protected Bumper pression   = null;
	/**
	 * Le sonar
	 */
	protected UltraSon   vision     = null;
	/**
	 * L'écran du robot
	 */
	protected Screen screen;
	/**
	 * l'inputhandler
	 */
	protected InputHandler input;
	/**
	 * 
	 */
	private boolean pressed;
	/**
	 * Contructeur de la classe Robot
	 * 
	 * @param p
	 * @param south
	 * @param color
	 * @param propulsion
	 * @param graber
	 * @param pression
	 * @param vision
	 */
	public Robot(Point p, boolean south, ColorSensor color,
			Propulsion propulsion, Graber graber, Bumper pression,
			UltraSon vision) {
		super();
		this.p = p;
		this.south = south;
		this.color = color;
		this.propulsion = propulsion;
		this.graber = graber;
		this.pression = pression;
		this.vision = vision;
		screen = new Screen();
		input = new InputHandler(screen);
		z = 0;
	}
	/**
	 * Getteur de p
	 * @return the p
	 */
	public Point getP() {
		return p;
	}
	/**
	 * Getteur de screen
	 */
	public Screen getS() {
		return screen;
	}
	/**
	 * Setteur de p
	 * @param p the p to set
	 */
	public void setP(Point p) {
		this.p = p;
	}
	/**
	 * Getteur de south
	 * @return the south
	 */
	public boolean isSouth() {
		return south;
	}
	/**
	 * Setter de south
	 * @param south the south to set
	 */
	public void setSouth(boolean south) {
		this.south = south;
		this.z = (south ? utils.R2D2Constants.SOUTH
				: utils.R2D2Constants.NORTH);
		this.propulsion.setOrientation(z);	
	}
	/**
	 * Getteur de color
	 * @return the color
	 */
	public ColorSensor getColor() {
		return color;
	}
	/**
	 * Setteur de color
	 * @param color the color to set
	 */
	public void setColor(ColorSensor color) {
		this.color = color;
	}
	/**
	 * Getteur de propulsion
	 * @return the propulsion
	 */
	public Propulsion getPropulsion() {
		return propulsion;
	}
	/**
	 * Setteur de propulsion
	 * @param propulsion the propulsion to set
	 */
	public void setPropulsion(Propulsion propulsion) {
		this.propulsion = propulsion;
	}
	/**
	 * Getteur de graber
	 * @return the graber
	 */
	public Graber getGraber() {
		return graber;
	}
	/**
	 * Setteur de graber
	 * @param graber the graber to set
	 */
	public void setGraber(Graber graber) {
		this.graber = graber;
	}
	/**
	 * Getteur de pression
	 * @return the pression
	 */
	public Bumper getPression() {
		return pression;
	}
	/**
	 * Setter de pression
	 * @param pression the pression to set
	 */
	public void setPression(Bumper pression) {
		this.pression = pression;
	}
	/**
	 * Getteur de vision
	 * @return the vision
	 */
	public UltraSon getVision() {
		return vision;
	}
	/**
	 * Setteur de vision
	 * @param vision the vision to set
	 */
	public void setVision(UltraSon vision) {
		this.vision = vision;
	}
	/**
	 * Getteur de z
	 * @return the z
	 */
	public float getZ() {
		return z;
	}
	/**
	 * Setteur de z
	 * @param z the z to set
	 */
	public void setZ(float z) {
		this.z = z;
		propulsion.setOrientation(z);
	}
	
	/**
	 * Suit un ligne
	 * 
	 * @param c la couleur de la ligne suivie
	 * @param dist la distance maximale à parcourir sur la ligne
	 * @throws LostLineException 
	 */
	public void followLine(int c, float dist) throws LostLineException{
		propulsion.change_rotation_speed(R2D2Constants.COLOR_SPEED);
		float angle_search_color = 30f;
		while(color.getCurrentColor() != Color.WHITE && dist > 0){
			propulsion.runDist(dist);
			while(propulsion.isRunning()){
				propulsion.check_dist();
				if(color.getCurrentColor() == Color.WHITE){
					propulsion.stopMoving();
					dist = 0;
				}
				else if(color.getCurrentColor() != c){
					propulsion.stopMoving();
					if(color.getCurrentColor() == Color.BLACK) {
						propulsion.runDist(5);
						while(propulsion.isRunning())
							propulsion.check_dist();
					}
					boolean b = true;
					propulsion.change_rotation_speed(R2D2Constants.COLOR_SPEED);
					propulsion.rotate(angle_search_color, false, false);
					while(propulsion.isRunning()){
						propulsion.checkState();
						if(color.getCurrentColor() == c || 
								color.getCurrentColor() == Color.WHITE){
							propulsion.stopMoving();
							b = false;
						}
					}
					if(b){
						propulsion.change_rotation_speed(R2D2Constants.COLOR_SPEED);
						propulsion.rotate(angle_search_color*3, true, false);
						while(propulsion.isRunning()){
							propulsion.checkState();
							if(color.getCurrentColor() == c || 
									color.getCurrentColor() == Color.WHITE){
								propulsion.stopMoving();
								b = false;
							}
						}
						
					}
					if(b)
						throw new exception.LostLineException();
					
				}
			}
			dist -= propulsion.getTraveledDist()/10;
		}
		propulsion.stopMoving();
		p = PointCalculator.getWhiteLinePoint(south, c);
		p.setY(isSouth() ? p.getY() + 10 : p.getY() - 10);
		setZ(propulsion.getOrientation());
		propulsion.change_rotation_speed(R2D2Constants.MAX_ROTATION_SPEED);
	}
	
	/**
	 * 
	 * Suit une ligne et dépose le palet si deliver est à true
	 * 
	 * @param c la couleur 
	 * @param dist la distance maximale
	 * @param deliver booléen indiquant s'il faut lacher un palet
	 * 
	 * @throws FinishException Traîtée par l'appelant
	 * @throws LostLineException 
	 */
	public void followLine(int c, float dist, boolean deliver) throws FinishException, LostLineException{
		followLine(c, dist);
		if(deliver){
			//On ouvre a pince
			graber.open();
			while(graber.isRunning()){
				graber.checkState();
			}
			//On recule de 6 centimètres
			propulsion.runDist(6, false);
			while(propulsion.isRunning()){
				propulsion.check_dist();
				if(input.escapePressed()){
					propulsion.stopMoving();
					throw new exception.FinishException();
				}
			}
		}
		setZ(south ? 180 : 0);
		//On se retourne
		orientate(false);
		//On avance de 7 centimètres
		/*propulsion.runDist(10, false);
		while(propulsion.isRunning()){
			propulsion.check_dist();
			if(input.escapePressed()){
				propulsion.stopMoving();
				throw new exception.FinishException();
			}
		}*/
	}
	
	
	/**
	 * Déplace le robot jusqu'à un point p
	 * 
	 * @param stop_palet booélen indiquant s'il y a un palet à attraper
	 * @param point la position à atteindre
	 * @throws FinishException 
	 */
	public void run(Point point, boolean stop_palet) throws FinishException{
		//On calcul la distance à parcourir
		float dist = p.distance(point);
		//On lance les moteurs
		pressed =false;
		propulsion.runDist(dist);
		while(propulsion.isRunning() && !pressed){
			propulsion.check_dist();
			pressed = pression.isPressed();
			if(input.escapePressed()){
				propulsion.stopMoving();
				throw new exception.FinishException();
			}
			if(pressed)
				propulsion.stopMoving();
			//Si il y a un palet à attraper, on vérifie que pression est préssé
		}
		if(stop_palet ){
			graber.close();
			while(graber.isRunning()){
				graber.checkState();
			}
		}
	}
	
	/**
	 * Cherche un paletaux alentours d'une position
	 * 
	 * @param dist la distance à laquelle se trouve le palet
	 * 
	 * @throws FinishException Traitée par l'appelant
	 * @throws InstructionException Traitée par l'appelant
	 */
	public void search_palet(float dist)
			throws FinishException, InstructionException{
		float angle = 15;
		boolean b = true;
		propulsion.change_rotation_speed(R2D2Constants.SEARCH_SPEED);
		propulsion.rotate(angle, false, false);
		while(propulsion.isRunning()){
			propulsion.checkState();
			if(input.escapePressed()){
				propulsion.stopMoving();
				throw new exception.FinishException();
			}
			float diff = Math.abs(dist - (vision.getRaw()[0]*100 + utils.R2D2Constants.
					size_sonar));
			System.out.println("diff :"+diff);
			if(diff <= utils.R2D2Constants.ERROR ){
				propulsion.stopMoving();
				b = false;
			}
		}
		if(b) {
			propulsion.change_rotation_speed(R2D2Constants.SEARCH_SPEED);
			propulsion.rotate(-2*angle, false, false);
			while(propulsion.isRunning()){
				propulsion.checkState();
				if(input.escapePressed()){
					propulsion.stopMoving();
					throw new exception.FinishException();
				}
				float diff = Math.abs(dist - (vision.getRaw()[0]*100 + utils.R2D2Constants.
						size_sonar));
				System.out.println("diff :"+diff);
				if(diff <= utils.R2D2Constants.ERROR ){
					propulsion.stopMoving();
					b = false;
				}
			}
		}
		z  = propulsion.getOrientation();
		if(b){
			throw new exception.InstructionException();
		}
	}
	
	/**
	 * Tourne le robot
	 * 
	 * @param angle l'angle à parcourir
	 * 
	 * @throws FinishException Traitée par l'appelant
	 */
	public void rotate(float angle) throws FinishException{
		System.out.println(propulsion.pilot.getAngularSpeed());
		propulsion.rotate(angle,false,true);
		while(propulsion.isRunning()){
			propulsion.checkState();
			if(input.escapePressed()){
				propulsion.stopMoving();
				throw new exception.FinishException();
			}
		}
		z = propulsion.getOrientation();
	}
	
	/**
	 * Tourne le robot
	 * 
	 * @param angle l'angle à parcourir
	 * 
	 * @throws FinishException Traitée par l'appelant
	 */
	public void orientate(float angle) throws FinishException{
		propulsion.orientate(angle);
		while(propulsion.isRunning()){
			propulsion.checkState();
			if(input.escapePressed()){
				propulsion.stopMoving();
				throw new exception.FinishException();
			}
		}
		setZ(propulsion.getOrientation());
	}
	/**
	 * Avance le robot
	 * @param dist la distance à parcourir
	 * @param forward cooléen indiquant si on avance ou recule
	 * @throws FinishException Traitée par l'appelant
	 */
	public void run(float dist, boolean forward) throws FinishException{
		propulsion.runDist(dist, forward);
		while(propulsion.isRunning()){
			propulsion.check_dist();
			if(input.escapePressed()){
				propulsion.stopMoving();
				throw new exception.FinishException();
			}
		}
	}
	/**
	 * Oriente le robot
	 * @param to_goal indiquant si le robot doit être orienté vers sa zone 
	 * d'en-but si la valeur est true, de l'autre coté sinon
	 * 
	 * @throws FinishException Traitée par l'appelant
	 */
	public void orientate(boolean to_goal) throws FinishException{
		if(south){
			if(to_goal){
				propulsion.orientateSouth();
			}else{
				propulsion.orientateNorth();
			}
				
		}else{
			if(!to_goal){
				propulsion.orientateSouth();
			}else{
				propulsion.orientateNorth();
			}
		}
			
		while(propulsion.isRunning()){
			propulsion.checkState();
			if(input.escapePressed()){
				propulsion.stopMoving();
				throw new exception.FinishException();
			}
		}
		z = propulsion.getOrientation();
	}

	/**
	 * Déplace le robot jusqu'à qu'une couleur soit atteinte
	 * @param c la couleur à atteindre
	 * @throws FinishException Traitée par l'appelant
	 */
	public void run_until_color(int c) throws FinishException{
		propulsion.runDist(80f);
		while(propulsion.isRunning()){
			propulsion.check_dist();
			if(color.getCurrentColor() == c){
				propulsion.stopMoving();
			}
			if(input.escapePressed()){
				propulsion.stopMoving();
				throw new exception.FinishException();
			}
		}
	}

	/**
	 * Déplace le robot jusqu'à qu'une couleur soit atteinte
	 * @param c la couleur à atteindre
	 * @param min_dist distance minimale à parcourire
	 * @throws FinishException Traitée par l'appelant
	 */
	public void run_until_color(int c, float min_dist) throws FinishException{
		System.out.println("black : "+(PointCalculator.closest_line(getP()) == Color.BLACK ));
		propulsion.runDist(min_dist + 50);
		while(propulsion.isRunning()){
			propulsion.check_dist();
			if(propulsion.check_dist(min_dist) && color.getCurrentColor()==c){
				// 
				propulsion.stopMoving();
			}
			System.out.println("check_dist : "+propulsion.check_dist(min_dist));
			if(input.escapePressed()){
				propulsion.stopMoving();
				throw new exception.FinishException();
			}
		}
	}
	
	/**
	 * Déplace le robot jusqu'à une lige horizontale noire.
	 * @throws FinishException 
	 */
	public void go_to_line(int c) throws FinishException{
		orientate_to_line(c);
		run_until_color(c);
		p =  new Point(x_line(c), p.getY());
	}
	
	/**
	 * Oriente le robot dans la direction de la ligne noire
	 */
	public void orientate_to_line(int c){
		if(p.getX() > x_line(c)){
			propulsion.orientateEast();
			while(propulsion.isRunning()){
				propulsion.checkState();
			}
		}else{
			propulsion.orientateWest();
			while(propulsion.isRunning()){
				propulsion.checkState();
			}
		}
		z = propulsion.getOrientation();
	}
	
	/**
	 * Renvoie l'absice d'une couleur
	 * 
	 * @param c le couleur
	 * @return l'abcise de c
	 */
	public float x_line(int c){
		if(c == Color.BLACK)
			return utils.R2D2Constants.X_BLACK;
		else if(c == Color.RED)
			return utils.R2D2Constants.X_RED;
		else if(c == Color.YELLOW)
			return utils.R2D2Constants.X_YELLOW;
		else
			throw new IllegalArgumentException();
	}
	
	public void orientate_east() {
		propulsion.orientate(R2D2Constants.EAST);
		//propulsion.orientateEast();
		while(propulsion.isRunning())
			propulsion.checkState();
		z = propulsion.getOrientation();
	}
	
	public void orientate_west() {
		propulsion.orientate(R2D2Constants.WEST);
		//propulsion.orientateWest();
		while(propulsion.isRunning())
			propulsion.checkState();
		setZ(propulsion.getOrientation());
	}
	
	public void orientate_north() {
		propulsion.orientate(R2D2Constants.NORTH);
		while(propulsion.isRunning())
			propulsion.checkState();
		setZ(propulsion.getOrientation());
	}
	
	public void orientate_south() {
		propulsion.orientate(R2D2Constants.SOUTH);
		while(propulsion.isRunning())
			propulsion.checkState();
		setZ(propulsion.getOrientation());
	}
	
	public float balayage(Point p2) throws Exception {
		float angle = 25;
		float min_value = Float.MAX_VALUE;
		float dist = p.distance(p2);
		propulsion.change_rotation_speed(R2D2Constants.SEARCH_SPEED);
		propulsion.rotate(angle, false, false);
		while(propulsion.isRunning()){
			propulsion.checkState();
			if(input.escapePressed()){
				propulsion.stopMoving();
				throw new exception.FinishException();
			}
			float diff = vision.getRaw()[0]*100 + utils.R2D2Constants.size_sonar;
			System.out.println("diff_balayage = "+dist);
			if(diff <= min_value ){
				min_value = diff;
			}
		}
		propulsion.change_rotation_speed(R2D2Constants.SEARCH_SPEED);
		propulsion.rotate(-2*angle, false, false);
		while(propulsion.isRunning()){
			propulsion.checkState();
			if(input.escapePressed()){
				propulsion.stopMoving();
				throw new exception.FinishException();
			}
			float diff = vision.getRaw()[0]*100 + utils.R2D2Constants.size_sonar;
			System.out.println("diff_balayage = "+dist);
			if(diff <= min_value ){
				min_value = diff;
			}
		}
		
		if(Math.abs(min_value - dist) > 45)
			throw new exception.InstructionException("Pas de palet");
		
		rotate(angle);
		z  = propulsion.getOrientation();
		return min_value;
	}
	
	public void homologation(){
		input.waitAny();
		propulsion.runDist(utils.R2D2Constants.LENGTH_ARENA);
		while(propulsion.isRunning()){
			propulsion.checkState();
			if(color.getCurrentColor() == Color.WHITE)
				propulsion.stopMoving();
		}
	}
}
