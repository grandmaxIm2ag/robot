package sensor;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class UltraSon {
	
	private EV3UltrasonicSensor sonar   = null;
	private Port                port    = null;
	public final static String IR_SENSOR = "S2";
	
	public UltraSon(){
		port  = LocalEV3.get().getPort(IR_SENSOR);
		sonar = new EV3UltrasonicSensor(port);
	}

	/**
	 * 
	 * @return la distance lue par le capteur ultrason
	 */
	public float[] getRaw() {
		float[] sample = new float[1];
		sonar.getDistanceMode().fetchSample(sample, 0);
		return sample;
	}
}
