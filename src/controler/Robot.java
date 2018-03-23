package controler;

import exception.FinishException;
import exception.InstructionException;
import lejos.robotics.Color;
import motor.Graber;
import motor.Propulsion;
import sensor.Bumper;
import sensor.ColorSensor;
import sensor.UltraSon;
import utils.Point;
import utils.PointCalculator;
import vue.InputHandler;
import vue.Screen;

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
	
	protected Screen screen;
	protected InputHandler input;
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
		screen = new Screen();
		input = new InputHandler(screen);
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
		this.z = (south ? utils.R2D2Constants.SOUTH
				: utils.R2D2Constants.NORTH);
		this.propulsion.setOrientation(z);
		
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
	/**
	 * 
	 * @param color
	 */
	public void followLine(int c, float dist){
		float angle_search_color = 30f;
		while(color.getCurrentColor() != Color.WHITE){
			propulsion.runDist(dist);
			while(propulsion.isRunning()){
				//System.out.println(color.stringColor());
				propulsion.check_dist();
				if(color.getCurrentColor() == Color.WHITE){
					propulsion.stopMoving();
				}else if(color.getCurrentColor() != c && 
						color.getCurrentColor() != Color.BLACK){
					propulsion.stopMoving();
					boolean b = true;
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
					
				}
			}
			dist -= propulsion.getTraveledDist();
			dist += utils.R2D2Constants.ERROR;
		}
		p = PointCalculator.getWhiteLinePoint(south, c);
	}
	
	public void followLine(int c, float dist, boolean deliver) throws FinishException{
		followLine(c, dist);
		if(deliver){
			propulsion.runDist(10);
			while(propulsion.isRunning()){
				propulsion.check_dist();
				if(input.escapePressed()){
					propulsion.stopMoving();
					throw new exception.FinishException();
				}
			}
			graber.open();
			while(graber.isRunning()){
				graber.checkState();
			}
			propulsion.runDist(10, false);
			while(propulsion.isRunning()){
				propulsion.check_dist();
				if(input.escapePressed()){
					propulsion.stopMoving();
					throw new exception.FinishException();
				}
			}
		}
	}
	/**
	 * 
	 * @param p
	 * @return
	 */
	public int closestColor(){
		if(Math.abs(utils.R2D2Constants.X_RED - this.p.getX()) < 5){
			return Color.RED;
		}else if(Math.abs(utils.R2D2Constants.X_BLACK- this.p.getX()) < 5){
			return Color.BLACK;
		}else{
			return Color.YELLOW;
		}
	}
	
	/**
	 * 
	 * @param dist
	 * @param point
	 * @throws FinishException 
	 */
	public void run(Point point, boolean stop_palet) throws FinishException{
		float dist = p.distance(point);
		propulsion.runDist(dist);
		while(propulsion.isRunning() && !pression.isPressed()){
			System.out.println(pression.isPressed());
			propulsion.check_dist();
			if(input.escapePressed()){
				propulsion.stopMoving();
				throw new exception.FinishException();
			}
		}
		if(stop_palet){
			if(pression.isPressed()){
				propulsion.stopMoving();
				graber.close();
				while(graber.isRunning()){
					System.out.println("coucou je me ferme");
					graber.checkState();
				}
			}
		}
		p = point;
	}
	
	/**
	 * 
	 * @param point
	 * @throws FinishException
	 * @throws InstructionException
	 */
	public void search_palet(Point point)
			throws FinishException, InstructionException{
		float dist = this.getP().distance(point);
		float angle = p.angle(point);
		angle *= 1.5;
		angle = angle - z;
		boolean b = true && (angle!=0);
		propulsion.rotate(angle, false, false);
		while(propulsion.isRunning()){
			propulsion.checkState();
			if(input.escapePressed()){
				propulsion.stopMoving();
				throw new exception.FinishException();
			}
			if(Math.abs(dist - (vision.getRaw()[0]*100)) <= utils.R2D2Constants.ERROR ){
				propulsion.stopMoving();
				b = false;
			}
		}
		z  = propulsion.getOrientation();
		if(b){
			throw new exception.InstructionException();
		}
	}
	
	/**
	 * 
	 * @param angle
	 * @throws FinishException
	 */
	public void rotate(float angle) throws FinishException{
		propulsion.rotate(angle, false, false);
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
	 * 
	 * @param dist
	 * @param forward
	 * @throws FinishException
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
	 * 
	 * @param to_goal
	 * @throws FinishException
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
	 * 
	 * @param c
	 * @throws FinishException
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

}
