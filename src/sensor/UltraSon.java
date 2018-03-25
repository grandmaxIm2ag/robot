package sensor;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
/**
 * Classe représentant le sonar
 *
 */
public class UltraSon {
	/**
	 * le sonar
	 */
	private EV3UltrasonicSensor sonar   = null;
	/**
	 * le port
	 */
	private Port                port    = null;
	/**
	 * Le numéro de port
	 */
	public final static String IR_SENSOR = "S2";
	
	/**
	 * Constructeur de la classe UltraSon
	 */
	public UltraSon(){
		port  = LocalEV3.get().getPort(IR_SENSOR);
		sonar = new EV3UltrasonicSensor(port);
	}

	/**
	 * Les données receuillies par le capteur
	 * @return la distance lue par le capteur ultrason
	 */
	public float[] getRaw() {
		float[] sample = new float[1];
		sonar.getDistanceMode().fetchSample(sample, 0);
		return sample;
	}
}
