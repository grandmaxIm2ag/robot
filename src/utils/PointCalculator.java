package utils;

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
}
