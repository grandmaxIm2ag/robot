package controler;

import motor.Graber;
import motor.Propulsion;
import sensor.Bumper;
import sensor.ColorSensor;
import sensor.UltraSon;
import utils.Point;

public class Robot {
	/**
	 * 
	 */
	float z;
	/**
	 * 
	 */
	protected Point p;
	/**
	 * 
	 */
	protected boolean south;
	/**
	 * 
	 */
	protected ColorSensor    color      = null;
	/**
	 * 
	 */
	protected Propulsion     propulsion = null;
	/**
	 * 
	 */
	protected Graber         graber     = null;
	/**
	 * 
	 */
	protected Bumper pression   = null;
	/**
	 * 
	 */
	protected UltraSon   vision     = null;
	/**
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
		z = 0;
	}
	/**
	 * @return the p
	 */
	public Point getP() {
		return p;
	}
	/**
	 * @param p the p to set
	 */
	public void setP(Point p) {
		this.p = p;
	}
	/**
	 * @return the south
	 */
	public boolean isSouth() {
		return south;
	}
	/**
	 * @param south the south to set
	 */
	public void setSouth(boolean south) {
		this.south = south;
	}
	/**
	 * @return the color
	 */
	public ColorSensor getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(ColorSensor color) {
		this.color = color;
	}
	/**
	 * @return the propulsion
	 */
	public Propulsion getPropulsion() {
		return propulsion;
	}
	/**
	 * @param propulsion the propulsion to set
	 */
	public void setPropulsion(Propulsion propulsion) {
		this.propulsion = propulsion;
	}
	/**
	 * @return the graber
	 */
	public Graber getGraber() {
		return graber;
	}
	/**
	 * @param graber the graber to set
	 */
	public void setGraber(Graber graber) {
		this.graber = graber;
	}
	/**
	 * @return the pression
	 */
	public Bumper getPression() {
		return pression;
	}
	/**
	 * @param pression the pression to set
	 */
	public void setPression(Bumper pression) {
		this.pression = pression;
	}
	/**
	 * @return the vision
	 */
	public UltraSon getVision() {
		return vision;
	}
	/**
	 * @param vision the vision to set
	 */
	public void setVision(UltraSon vision) {
		this.vision = vision;
	}
	/**
	 * @return the z
	 */
	public float getZ() {
		return z;
	}
	/**
	 * @param z the z to set
	 */
	public void setZ(float z) {
		this.z = z;
	}
	
}
