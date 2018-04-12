package utils;

import lejos.robotics.Color;
import java.lang.Math;
import java.util.Arrays;

/**
 * 
 * Classe utilitaire pour le calcul de position
 */
public class PointCalculator {

	/**
	 * Renvoie le point d'un absice  sur la ligne d'en-but
	 * 
	 * @param isSouth la ligne d'en-but
	 * @param x l'absice
	 * 
	 * @return la position
	 */
	public static Point getWhiteLinePoint(boolean isSouth, float x){
		return new Point(x, isSouth ? R2D2Constants.Y_SOUTH : R2D2Constants.Y_NORTH);
	}
	
	/**
	 * Renvoie le point d'embranchement sur la ligne d'en-but
	 * 
	 * @param isSouth la ligne d'en-but
	 * @param color la ligne verticale
	 * 
	 * @return la position de l'embranchement
	 */
	public static Point getWhiteLinePoint(boolean isSouth, int color){
		float x = color == Color.RED ? R2D2Constants.X_RED : color == Color.YELLOW ?
			R2D2Constants.X_YELLOW : R2D2Constants.X_BLACK;
		float y = isSouth ? R2D2Constants.Y_SOUTH : R2D2Constants.Y_NORTH;
		return new Point(x,y);
	}
	
	/**
	 * Vérifie que le point se trouve sur la ligne d'en-but
	 * @param p la position
	 * @param s le coté de la ligne d'en-but
	 * 
	 * @return vrai si la position se trouve sur la ligne d'en-but
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
				(Math.abs(p.getX() - utils.R2D2Constants.X_BLACK) <= 10) ||
				(Math.abs(p.getX() - utils.R2D2Constants.X_RED) <= 10) ||
				(Math.abs(p.getX() - utils.R2D2Constants.X_YELLOW) <= 10);
	}
	
	/**
	 * Renvoie la couleur de la ligne verticale la plus proche
	 * @param p
	 * @return la couleur la plus de la position p
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
		
		string_color(utils.R2D2Constants.colors[i_min]);
		return utils.R2D2Constants.colors[i_min];
	}
	
	public static float closest_column(Point p){
 		int r=0, y=1, b=2;
 		float dist [] = new float[3];
 		
 		dist[r] = Math.abs(p.getX()-R2D2Constants.X_RED);
 		dist[y] = Math.abs(p.getX()-R2D2Constants.X_YELLOW);
 		dist[b] = Math.abs(p.getX()-R2D2Constants.X_BLACK);
 		
 		int i_min=0;
		for(int i =1; i<dist.length; i++) {
			if(dist[i] < dist[i_min])
				i_min = i;
		}
		
		if(i_min==r)
			return R2D2Constants.X_RED;
		if(i_min==y)
			return R2D2Constants.X_YELLOW;
		if(i_min==b)
			return R2D2Constants.X_BLACK;
		
		return 0;
		
	}
	
	public static float closest_line(Point p){
 		int r=0, y=1, b=2;
 		float dist [] = new float[3];
 		
 		dist[r] = Math.abs(p.getY()-R2D2Constants.Y_GREEN);
 		dist[y] = Math.abs(p.getY()-R2D2Constants.Y_BLUE);
 		dist[b] = Math.abs(p.getY()-R2D2Constants.Y_BLACK);
 		
 		int i_min=0;
		for(int i =1; i<dist.length; i++) {
			if(dist[i] < dist[i_min])
				i_min = i;
		}
		
		if(i_min==r)
			return R2D2Constants.Y_GREEN;
		if(i_min==y)
			return R2D2Constants.Y_BLUE;
		if(i_min==b)
			return R2D2Constants.Y_BLACK;
		
		return 0;
		
	}
	
	public static float degreesToRadians(float t) {
		float c = (float)Math.PI/180;
		return t*c;
	}
	
	
	public static Point getPointFromAngle(Point p, float dist, float currentDirection) {
		//Angle en degrés, convertir en radians
		double angle = currentDirection % 360;
		angle = Math.toRadians(90 - angle);
		
		float x = p.getX();
		float y = p.getY();
		float cos = (float)Math.cos(angle);
		float sin = (float)Math.sin(angle);
		Point r = new Point(x - cos*dist, y + sin*dist);
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
