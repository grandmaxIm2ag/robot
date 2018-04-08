package motor;

import java.util.Date;

import utils.R2D2Constants;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.Move;
import lejos.robotics.navigation.MoveListener;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.MoveProvider;

/**
 * 
 * Classe représentant les moteurs des roues
 */
public class Propulsion extends TimedMotor implements MoveListener{

	//chassis
	/**
	 * la roue droite
	 */
	private Wheel     left           = null;
	/**
	 * La roue gauche
	 */
	private Wheel     right          = null;
	/**
	 * Le chassis
	 */
	private Chassis   chassis        = null;
	/**
	 * Le pilote
	 */
	public MovePilot pilot          = null;
	//Constants
	/**
	 * L'orientation
	 */
	private float     orientation    = R2D2Constants.NORTH;
	
	/**
	 * Le diamètre des roues
	 */
	private float     DIAMETER       = R2D2Constants.WHEEL_DIAMETER;
	/**
	 * La valeur d'un demi tour
	 */
	private int       HALF_TURN      = R2D2Constants.HALF_CIRCLE;
	/**
	 * La valeur d'un quart de tour
	 */
	private int       MID_TURN       = R2D2Constants.QUART_CIRCLE;
	//state
	
	private long      lastAskRunning = -1;
	private int       time           = -1;
	private float     lastTurnedAngle= 0;
	private long      startTime      = 0;
	private long      stopTime       = 0;
	private long      lastRunTime    = 0;
	/**
	 * Indique que les moteurs sont en marche
	 */
	private boolean   running        = false;
	/**
	 * Une distance à parcourir
	 */
	private float 	  expected_dist  = 0;
	/**
	 * Distance parcourue
	 */
	private float 	  traveledDist   = 0f;
	
	/**
	 * Constructeur de la classe Propulsion
	 */
	public Propulsion(){
		// Change this to match your robot
		left      = WheeledChassis.modelWheel(new EV3LargeRegulatedMotor(LocalEV3.get().getPort(
				R2D2Constants.LEFT_WHEEL)), DIAMETER).offset(-1*R2D2Constants.DISTANCE_TO_CENTER);
		right     = WheeledChassis.modelWheel(new EV3LargeRegulatedMotor(LocalEV3.get().getPort(
				R2D2Constants.RIGHT_WHEEL)), DIAMETER).offset(R2D2Constants.DISTANCE_TO_CENTER);
		chassis   = new WheeledChassis(new Wheel[]{left, right},  WheeledChassis.TYPE_DIFFERENTIAL);
		pilot     = new MovePilot(chassis);
		pilot.addMoveListener(this);
		pilot.setLinearAcceleration(R2D2Constants.LINEAR_ACCELERATION);
		pilot.setLinearSpeed(200);
		pilot.setAngularSpeed(R2D2Constants.MAX_ROTATION_SPEED);
		
	}

	/**
	 * Vérifie la distance parcourue par le robot
	 */
	public void check_dist(){
		if (pilot.getMovement().getDistanceTraveled() > expected_dist){
			this.stopMoving();
		}
	}
	
	/**
	 * Vérifie la distance parcourue par le robot
	 */
	public boolean check_dist(float dist){
		if (pilot.getMovement().getDistanceTraveled() > dist*10){
			return true;
		}
		return false;
	}
	/**
	 * Vérifie que les moteurs sont coincé
	 * @return vrai si les moteurs sont coincé
	 */
	@Override
	public boolean isStall() {
		return chassis.isStalled();
	}
	/**
	 * Stop les moteurs des roues
	 */
	@Override
	public void stopMoving() {
		pilot.stop();
	}
	/**
	 * Met en route les moteurs des roues
	 */
	@Override
	public void run(boolean forward) {
		if(forward){
			pilot.forward();
		}else{
			pilot.backward();
		}
	}
	
	/**
	 * Effectue un demi tour
	 * @param left vrai si à gauche
	 */
	public void volteFace(boolean left){
		rotate(HALF_TURN, left, false);
	}
	
	/**
	 * Effectue un demi tour
	 * @param left vrai si à gauche
	 * @param speed vitesse de rotation
	 */
	public void volteFace(boolean left, double speed){
		pilot.setAngularSpeed(speed);
		volteFace(left);
	}
	
	/**
	 * Effectue un quart de tour
	 * @param left vrai si à gauche
	 * @param speed vitesse de rotation
	 */
	public void halfTurn(boolean left, double speed){
		pilot.setAngularSpeed(speed);
		halfTurn(left);
	}

	/**
	 * Effectue un quart de tour
	 * @param left vrai si à gauche
	 */
	public void halfTurn(boolean left){
		rotate(MID_TURN, left, true);
	}

	/**
	 * Effectue une rotation custom
	 * @param i degrés à tourner
	 * @param left vrai si à gauche
	 * @param speed vitesse de rotation
	 */
	public void rotate(int i, boolean left, double speed) {
		pilot.setAngularSpeed(speed);
		rotate(i, left, true);
	}

	/**
	 * Effectue une rotation à gauche
	 * @param i degrés à tourner
	 * @param left vrai si à gauche
	 * @param correction vrai si on doit appliquer une correction de 2%
	 */
	public void rotate(float i, boolean left, boolean correction) {
		if(correction)
			i = i - (i * R2D2Constants.PR_ANGLE_CORRECTION);
		if(left){
			pilot.rotate(i*-1, true);
		}else{
			pilot.rotate(i, true);	
		}
	}
	
	public void orientate(float d) {
		
		if(d>orientation) {
			if(d-orientation <= R2D2Constants.HALF_CIRCLE)
				rotate(d-orientation,false,true);
			else
				rotate(d-R2D2Constants.FULL_CIRCLE-orientation,false,true);
		}
		else {
			if(orientation-d > R2D2Constants.HALF_CIRCLE)
				rotate(R2D2Constants.FULL_CIRCLE-orientation+d,false,true);
			else
				rotate(d-orientation,false,true);
		}
	}
	/**
	 * oriente le robot au nord
	 */
	public void orientateNorth() {
		orientate(R2D2Constants.NORTH);
		/*double orientate = getRotateToNorth();
		if(orientate < 0)
			pilot.rotate(orientate, false);
		else
			pilot.rotate(orientate, true);*/
	}
	
	/**
	 * oriente le robot à l'ouest
	 */
	public void orientateWest(){
		orientate(R2D2Constants.WEST);
		/*double orientate = getRotateToNorth()+R2D2Constants.WEST;
		rotate( (int) orientate, false, false);*/
	}

	/**
	 * Orient le robot vers l'est
	 */
	public void orientateEast() {
		orientate(R2D2Constants.EAST);
		/*double orientate = getRotateToNorth()+R2D2Constants.EAST;
		rotate( (int) orientate, false, false);*/
	}
	/**
	 * Orient le robot vers le sud
	 */
	public void orientateSouth(){
		orientate(R2D2Constants.SOUTH);
		//orientateSouth( this.getOrientation() > utils.R2D2Constants.SOUTH);
	}
	
	/**
	 * Oriente le robot vers le sud
	 * @param left
	 */
	/*public void orientateSouth(boolean left) {
		/*double orientate = left ? getRotateToNorth()-R2D2Constants.SOUTH :
		                          getRotateToNorth()+R2D2Constants.SOUTH;
		if(orientate < 0)
			pilot.rotate(orientate, false);
		else
			pilot.rotate(orientate, true);
	}*/
	
	/**
	 * 
	 * @return le nombre de degrés pour aller au nord
	 */
	/*public double getRotateToNorth(){
		if(orientation<-R2D2Constants.HALF_CIRCLE){
			return Math.abs(orientation)-R2D2Constants.FULL_CIRCLE;
		}else if(orientation > R2D2Constants.HALF_CIRCLE){
			return R2D2Constants.FULL_CIRCLE-orientation;
		}else{
			return orientation*-1;
		}	
	}*/
	
	/**
	 * Met en route les moteurs des roues pour un temps défini
	 * 
	 * @param millis le temps
	 * @param forward le sens de rotation des moteur
	 */
	@Override
	public void runFor(int millis, boolean forward) {
		lastAskRunning = new Date().getTime();
		time           = millis;
		run(forward);
	}
	/**
	 * 
	 */
	@Override
	public boolean isTimeRunElapsed() {
		boolean timeElapsed = false;
		if(lastAskRunning > -1){
			long attendeeDate = lastAskRunning + time;
			Date d = new Date();
			timeElapsed =  d.getTime() > attendeeDate;
		}
		return timeElapsed;
	}

	/**
	 * @return vrai si le robot est en train de tourner
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Handler pour la lise en route des moteurs
	 * 
	 * @param event l'évennement
	 * @param mp
	 */
	public void moveStarted(Move event, MoveProvider mp) {
		running   = true;
		startTime = event.getTimeStamp();
	}
	/**
	 * Handler pour l'arrêt des moteurs
	 * 
	 * @param event l'évennement
	 * @param mp
	 */
	public void moveStopped(Move event, MoveProvider mp) {
		running        = false;
		stopTime       = event.getTimeStamp();
		lastRunTime    = stopTime - startTime;
		lastAskRunning = -1;
		pilot.setAngularSpeed(R2D2Constants.MAX_ROTATION_SPEED);

		orientation = orientation + (event.getAngleTurned()+
				(event.getAngleTurned()*R2D2Constants.PR_ANGLE_CORRECTION));
		orientation = orientation % R2D2Constants.FULL_CIRCLE;
		if(orientation < 0) {
			orientation = -1*orientation % R2D2Constants.FULL_CIRCLE;
			if(event.getAngleTurned()<0)
				orientation = R2D2Constants.FULL_CIRCLE - orientation;
		}
		traveledDist=event.getDistanceTraveled();
		lastTurnedAngle = event.getAngleTurned();
	}
	
	/**
	 * @param percentage le nombre de %
	 * @return vrai si le robot à parcouru pecentage pourcent de son dernier 
	 * voyage
	 */
	public boolean hasRunXPercentOfLastRun(int percentage){
		return System.currentTimeMillis() > startTime + 
				((lastRunTime*(percentage/2))/100);
	}
	
	/**
	 * Getteur de Orientation
	 * @return l'orientation par rapport au nord
	 */
	public float getOrientation(){
		return orientation;
	}

	/**
	 * Reinitialise les degrés au nord
	 * @param i
	 */
	public void seDegreeToNorth(int i) {
		orientation = i;
	}
	
	/**
	 * renvoie le dernier angle tourné
	 * @return lastTurnAngle
	 */
	public double getLastTurnedAngle(){
		return lastTurnedAngle;
	}
	/**
	 * Lance les moteur pour parcourir une distance définie
	 * 
	 * @param dist : la distance en centimetre
	 */
	public void runDist(float dist){
		expected_dist = dist * 10;
		run(true);
	}
	
	/**
	 * Lance les moteur pour parcourir une distance définie
	 * 
	 * @param dist : la distance en centimetre
	 * @param forward le sens de rotation des roues
	 */
	public void runDist(float dist, boolean forward){
		expected_dist = dist * 10;
		run(forward);
	}
	
	/**
	 * Setteur de Orientaton
	 * @param orientation the orientation to set
	 */
	public void setOrientation(float orientation) {
		this.orientation = orientation;
	}
	
	/**
	 * Getteur de traveledDist
	 * @return traveledDist
	 */
	public float getTraveledDist(){
		return traveledDist;
	}

}
