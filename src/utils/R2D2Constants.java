package utils;

import lejos.robotics.Color;

public class R2D2Constants {
	public static final String LEFT_WHEEL = "D";
	public static final String RIGHT_WHEEL= "A";
	public static final String PINCH      = "C";
	
	public static final int   QUARTER_SECOND        = 250;
	public static final int   HALF_SECOND           = 500;
	public static final int   THREE_QUARTER_S       = 750;
	public static final int   STEPS_PER_STAGE       = 3;
	public static final int   INIT_SEARCH_PIK_VALUE = -1;
	public static final int   INIT_NB_SEEK          = 1;
	public static final int   QUART_CIRCLE          = 90;
	public static final int   HALF_CIRCLE           = 180;
	public static final int   FULL_CIRCLE           = 360;
	public static final float WHEEL_DIAMETER        = 56;
	public static final float DISTANCE_TO_CENTER    = 62.525f;
	
	/*
	 * Runing parameters, may be changed
	 */
	public static final int   NORTH                      = 0;
	public static final int   WEST                       = HALF_CIRCLE + QUART_CIRCLE;
	public static final int   EAST                       = QUART_CIRCLE;
	public static final int   SOUTH                      = HALF_CIRCLE;
	public static final float ANGLE_START                = 10;
	public static final int   MAX_GRABING_TIME           = 2200;
	public static final int   ACTIVATE_SENSOR_AT_PERCENT = 20;
	public static final int   EMPTY_HANDED_STEP_FORWARD  = 1300;
	public static final float MAX_VISION_RANGE           = 0.70f;
	public static final float MIN_VISION_RANGE           = 0.20f;
	public static final float COLLISION_DISTANCE         = 0.20f;
	public static final int   GRAB_CALIBRATE_SPEED       = 200;
	public static final int   GRAB_RUNNING_SPEED         = 2000;
	public static final float LINEAR_ACCELERATION        = 0.1f;
	public static final int   VOLTE_FACE_ROTATION        = 80;
	public static final int   MAX_ROTATION_SPEED         = 25;
	public static final int   SEARCH_SPEED               = 30;
	public static final int   SLOW_SEARCH_SPEED          = 20;
	public static final int   ANGLE_CORRECTION           = 2;
	public static final float PR_ANGLE_CORRECTION        = ANGLE_CORRECTION/100f;
	public static final float SIZE_ARENA				 = 200f;
	public static final float ERROR						 = 10f;
	public static final float DIST_AVOID_OBSTACLE 		 = 50f;
	public static final float Y_SOUTH 					 = 30f;
	public static final float Y_NORTH 					 = 270f;
	public static final float X_RED						 = 150f;
	public static final float X_BLACK					 = 100f;
	public static final float X_YELLOW					 = 50f;
	public static final int PORT_CAMERA					 = 8888;
	public static final int WIDTH_ARENA					 = 200;
	public static final int LENGTH_ARENA				 = 300;
	public static final float STRENGTH_BARREL_CORRECTION = 1.4f;
	public static final boolean ADVANCED_CORRECTION      = true;
	public static final float ANGLE_ERROR				 = 1.1f;
	
	public static final int[] colors = {Color.YELLOW, Color.BLACK, Color.RED};
	public final static int YELLOW = 0;
	public final static int BLACK = 1;
	public final static int RED = 2;
	
	public final static float size_sonar = 15f;
	
}
