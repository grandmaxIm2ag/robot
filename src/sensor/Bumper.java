package sensor;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;

/**
 * 
 * Classe représentant le Bumper
 */
public class Bumper {
	/**
	 * port du bumper
	 */
	private Port port = null;
	/**
	 * le bumper
	 */
	private EV3TouchSensor touch = null;
	/**
	 * Le numéro de port
	 */
	public final static String TOUCH_SENSOR = "S3";
	/**
	 * Constructeur de la classe bumper
	 */
	public Bumper(){
		port = LocalEV3.get().getPort(TOUCH_SENSOR);
		touch= new EV3TouchSensor(port);
	}
	
	/**
	 * Vérifie que le bumper est préssé
	 * @return vrai si il est préssé
	 */
	public boolean isPressed(){
		float[] sample = raw();
		return sample[0] != 0;
	}

	/**
	 * Attends la pression sur le capteur.
	 * Attention, c'est une attente active !
	 * Il est possible de sortir de la boucle en appuyant sur echap
	 * 
	 * @return vrai si l'attente n'a pas été intérrompue par l'appui sur echap
	 */
	public boolean activePressWait() {
		while(!isPressed()){
			//sécurité anti boucle infinie
			if(Button.ESCAPE.isDown())
				return false;
		}
		return true;
	}

	/**
	 * Renvoie les valeur receuillis par le capteur
	 * @return les valeurs receuillis
	 */
	public float[] raw() {
		float[] sample = new float[1];
		touch.fetchSample(sample, 0);
		// TODO Auto-generated method stub
		return sample;
	}
}
