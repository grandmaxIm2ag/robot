package utils;

import lejos.robotics.Color;

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
}
