package utils;

public class R2D2Constants {
	public static final String LEFT_WHEEL = "B";
	public static final String RIGHT_WHEEL= "C";
	public static final String PINCH      = "A";
	
	public static final String COLOR_SENSOR = "S1";
	public static final String TOUCH_SENSOR = "S2";
	public static final String IR_SENSOR    = "S4";
	
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
	public static final int   WEST                       = -QUART_CIRCLE;
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
	public static final float LINEAR_ACCELERATION        = 0.2f;
	public static final int   VOLTE_FACE_ROTATION        = 80;
	public static final int   MAX_ROTATION_SPEED         = 70;
	public static final int   SEARCH_SPEED               = 30;
	public static final int   SLOW_SEARCH_SPEED          = 20;
	public static final int   ANGLE_CORRECTION           = 2;
	public static final float PR_ANGLE_CORRECTION        = ANGLE_CORRECTION/100f;
}
