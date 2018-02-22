package main;

import controler.Calibrator;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import motor.Graber;
import sensor.Bumper;
import vue.InputHandler;
import vue.Screen;

public class TestBumper {
	static Bumper b = new Bumper();
	static InputHandler input;
	static Screen screen;
	public static void main(String[] args){
		Graber g = new Graber();
		Calibrator.celibrateGrapber(g, true);
		for(int i=0; i<2; i++){
			Button.waitForAnyPress();
			if(b.isPressed() ){
				g.close();
			}else{
				g.open();
			}
			while(g.isRunning()){
				g.checkState();
			}
		}
		Button.waitForAnyPress();
	}
}
