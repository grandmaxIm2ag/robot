package utils;

import lejos.robotics.Color;
import java.lang.Math;
import java.util.Arrays;

/**
 * 
 *
 */
public class PointCalculator {

	/**
	 * 
	 * @param isSouth
	 * @param x
	 * @return
	 */
	public static Point getWhiteLinePoint(boolean isSouth, float x){
		return new Point(x, isSouth ? R2D2Constants.Y_SOUTH : R2D2Constants.Y_NORTH);
	}
	
	/**
	 * 
	 * @param isSouth
	 * @param color
	 * @return
	 */
	public static Point getWhiteLinePoint(boolean isSouth, int color){
		float x = color == Color.RED ? R2D2Constants.X_RED : color == Color.YELLOW ?
			R2D2Constants.X_YELLOW : R2D2Constants.X_BLACK;
		float y = isSouth ? R2D2Constants.Y_SOUTH : R2D2Constants.Y_NORTH;
		return new Point(x,y);
	}
	
	/**
	 * 
	 * @param p
	 * @param s
	 * @return
	 */
	public static boolean is_on_goal(Point p, boolean s) {
		if(s) {
			return p.getY() <= R2D2Constants.Y_SOUTH;
		} else {
			return p.getY() >= R2D2Constants.Y_NORTH;
		}
	}
	
	/**
	 * Vérifie que le robot se trouve sur une des trois lignes verticales.
	 * 
	 * @param p
	 * @return vrai si le robot se trouve sur une des lignes verticales.
	 */
	public static boolean is_on_vertical_line(Point p){
		return
				(Math.abs(p.getX() - utils.R2D2Constants.X_BLACK) <= 5) ||
				(Math.abs(p.getX() - utils.R2D2Constants.X_RED) <= 5) ||
				(Math.abs(p.getX() - utils.R2D2Constants.X_YELLOW) <= 5);
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public static int closestColor(Point p){
 		int [] dist = new int[3];
		dist[utils.R2D2Constants.RED] = (int)Math.abs(p.getX() - 
				utils.R2D2Constants.X_RED);
		dist[utils.R2D2Constants.YELLOW] = (int)Math.abs(p.getX() - 
				utils.R2D2Constants.X_YELLOW);
		dist[utils.R2D2Constants.BLACK] = (int)Math.abs(p.getX() - 
				utils.R2D2Constants.X_BLACK);
		
		int i_min=0;
		for(int i =1; i<dist.length; i++) {
			if(dist[i] < dist[i_min])
				i_min = i;
		}
		
		System.out.println(Arrays.toString(dist));
		string_color(utils.R2D2Constants.colors[i_min]);
		return utils.R2D2Constants.colors[i_min];
	}
	
	public static float degreesToRadians(float t) {
		float c = (float)Math.PI/180;
		return t*c;
	}
	
	
	public static Point getPointFromAngle(Point p, float dist, float currentDirection, float a) {
		//Angle en degrés, convertir en radians
		double angle = (currentDirection + a) % 360;
		angle = Math.toRadians(90 - angle);
		
		float x = p.getX();
		float y = p.getY();
		float cos = (float)Math.cos(angle);
		float sin = (float)Math.sin(angle);
	
		System.out.println("cos = " + cos + " & sin = " + sin + " & angle = " + angle);
		Point r = new Point(x - cos*dist, y + sin*dist);
		System.out.println(r);
		return r;
	}
	
	public static void string_color(int c){
		if(c == utils.R2D2Constants.colors[utils.R2D2Constants.RED])
			System.out.println("rouge");
		if(c == utils.R2D2Constants.colors[utils.R2D2Constants.BLACK])
			System.out.println("noir");
		if(c == utils.R2D2Constants.colors[utils.R2D2Constants.YELLOW])
			System.out.println("jaune");
	}
}
