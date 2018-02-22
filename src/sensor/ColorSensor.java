package sensor;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;

public class ColorSensor {
	public final static String CAPTEUR = "S4";
	
	private float[][] colors;
	private Port port;
	private EV3ColorSensor colorSensor;
	private boolean alreadyCalibrate[];
	public ColorSensor(){
		port        = LocalEV3.get().getPort(CAPTEUR);
		colorSensor = new EV3ColorSensor(port);
		colors      = new float[16][0];
		alreadyCalibrate = new boolean[16];
	}
	
	/**
	 * Allume la led de capture de couleur d'une couleur blanche
	 */
	public void lightOn(){
		colorSensor.setFloodlight(Color.WHITE);
	}
	
	/**
	 * Eteins la led de capture de couleur
	 */
	public void lightOff(){
		colorSensor.setFloodlight(false);
	}

	/**
	 * Calibre la couleur passée en paramètre.
	 * Utiliser Color.EXAMPLE pour choisir la couleur à calibrer
	 * @param color
	 */
	public void calibrateColor(int color){
		SampleProvider average = new MeanFilter(colorSensor.getRGBMode(), 1);
		if(!alreadyCalibrate[color]){
			colors[color] = new float[average.sampleSize()];
			alreadyCalibrate[color] = true;
		}else{
			float[] tmp = new float[average.sampleSize()];
			for(int i=0; i<tmp.length; i++){
				colors[color][i] = (tmp[i]+colors[color][i])/2;
			}
			
		}
		average.fetchSample(colors[color], 0);
	}

	/**
	 * Renvoie la couleur connue la plus proche.
	 * Pour que cette fonction ne renvoie pas -1 il conviens de calibrer les 
	 * couleurs en amont.
	 * 
	 * @return la couleur (Color.EXAMPLE) ou -1 si aucune couleur n'a été
	 * calibrée
	 */
	public int getCurrentColor(){
		SampleProvider average = new MeanFilter(colorSensor.getRGBMode(), 1);
		float[]        sample  = new float[average.sampleSize()];
		double         minscal = Double.MAX_VALUE;
		int            color   = -1;

		average.fetchSample(sample, 0);

		for(int i= 0; i< 16; i++){
			if(colors[i].length > 0){
				double scalaire = scalaire(sample, colors[i]);
				if (scalaire < minscal) {
					minscal = scalaire;
					color = i;
				}
			}
		}
		return color;
	}
	
	/**
	 * Code pris des exemples. Calcule la distance entre deux couleurs.
	 * @param v1 la preière couleur
	 * @param v2 la seconde couleur
	 * @return la distance entre les deux couleurs.
	 */
	private double scalaire(float[] v1, float[] v2) {
		return Math.sqrt (Math.pow(v1[0] - v2[0], 2.0) +
				Math.pow(v1[1] - v2[1], 2.0) +
				Math.pow(v1[2] - v2[2], 2.0));
	}
	
	/**
	 * Charge une calibration depuis l'extérieur
	 * @param colors
	 */
	public void setCalibration(float[][] colors){
		this.colors = colors;
	}

	/**
	 * 
	 * @return la calibration
	 */
	public float[][] getCalibration() {
		return colors;
	}
}