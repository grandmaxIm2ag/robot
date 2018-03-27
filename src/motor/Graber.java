//
package motor;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;

/**
 * 
 * Classe représentant la pince
 */
public class Graber extends TimedMotor{

	/**
	 * Vitesse pour la calibration
	 */
	public final static int GRAB_CALIBRATE_SPEED = 300;
	/**
	 * Vitesse normale
	 */
	public final static int GRAB_RUNNING_SPEED = 600;
	/**
	 * Moteur de la pince
	 */
	private EV3LargeRegulatedMotor graber = null;
	/**
	 * Port de le pince
	 */
	private Port port                     = null;
	/**
	 * 
	 */
	private long startCalibrateOpen       = 0;
	/**
	 * 
	 */
	private long movementTimeOpen         = 0;
	/**
	 * 
	 */
	private long lastAskedRunning         = -1;
	/**
	 * Booléen indiquant que les pinces sont fermés
	 */
	private boolean isClose = false;
	/**
	 * Booléen indiquant que les pinces sont ouvertes
	 */
	private boolean isOpen  = true;
	/**
	 * Booléen indiquant que les moteurs sont en marche
	 */
	private boolean isRunin = false;

	/**
	 * Contructeur de la clase Graber
	 */
	public Graber(){
		port   = LocalEV3.get().getPort(utils.R2D2Constants.PINCH);
		graber = new EV3LargeRegulatedMotor(port);
	}

	/**
	 * Lance la calibration
	 * @param open vrai pour l'ouverture
	 */
	public void startCalibrate(boolean open){
		graber.setSpeed(GRAB_CALIBRATE_SPEED);
		if(open){
			graber.forward();
			startCalibrateOpen = System.currentTimeMillis();
		}else{
			graber.backward();
		}
	}

	/**
	 * Arrête la calibration
	 * @param open
	 */
	public void stopCalibrate(boolean open){
		stopMoving();
		long stoping = System.currentTimeMillis();
		if(open){
			movementTimeOpen = stoping - startCalibrateOpen;
			isOpen  = true;
			isClose = false;
		}else{
			isOpen  = false;
			isClose = true;
		}
	}

	/**
	 * ferme la pince en fonction de la calibration
	 * Le mouvement sera lancé uniquement si aucun mouvement n'est en cours
	 */
	public void close(){
		if(!isRunin){
			graber.setSpeed(GRAB_RUNNING_SPEED);
			graber.backward();
			isRunin = true;
			lastAskedRunning = System.currentTimeMillis();
		}
	}
	
	/**
	 * ferme la pince en fonction de la calibration
	 * Le mouvement sera lancé uniquement si aucun mouvement n'est en cours
	 */
	public void open(){
		if(!isRunin){
			graber.setSpeed(GRAB_RUNNING_SPEED);
			graber.forward();
			isRunin = true;
			lastAskedRunning = System.currentTimeMillis();
		}
	}

	/**
	 * Stop la fermeture/ouverture des pinces
	 */
	@Override
	public void stopMoving() {
		lastAskedRunning = -1;
		graber.stop();
		isRunin = false;
		if(isClose){
			isOpen  = true;
			isClose = false;
		}else{
			isOpen  = false;
			isClose = true;
		}
	}

	/**
	 * Vérifie qu'une pince est coincée ou non
	 * @return vrai si les pinces sont coincées
	 */
	@Override
	public boolean isStall() {
		return graber.isStalled();
	}

	/**
	 * Met en route les pinces pour un temps défini
	 * 
	 * @param millis le temps
	 * @param forward le sens pour le moteur
	 */
	@Override
	public void runFor(int millis, boolean forward) {
		if(forward){
			open();
		}else{
			close();
		}
	}
	/**
	 * @return
	 */
	@Override
	public boolean isTimeRunElapsed() {
		if(lastAskedRunning != -1){
			long wait = 0;
			wait = movementTimeOpen / (GRAB_RUNNING_SPEED / GRAB_CALIBRATE_SPEED);

			long attendeeDate = lastAskedRunning + wait;
			return System.currentTimeMillis() > attendeeDate;
		}
		return false;
	}

	/**
	 * Vérifie que les pinces sont fermées
	 * @return vrai si la pince est fermée
	 */
	public boolean isClose() {
		return isClose;
	}

	/**
	 * Vérifie que les pinces sont ouvertes
	 * @return vrai si la pince est ouverte
	 */
	public boolean isOpen() {
		return isOpen;
	}
	
	/**
	 * Vérifie que le moteur des pinces est en route
	 * @return vrai si la pince est en train de bouger
	 */
	public boolean isRunning(){
		return isRunin;
	}

	/**
	 * Met en route le moteur des pinces
	 */
	@Override
	public void run(boolean forward) {
		if(forward){
			open();
		}else{
			close();
		}
	}
	/**
	 * 
	 * @return
	 */
	public long getOpenTime() {
		return movementTimeOpen;
	}
	/**
	 * 
	 * @param mvt
	 */
	public void setOpenTime(long mvt){
		this.movementTimeOpen = mvt;
	}
}