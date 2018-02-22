package main;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import sensor.UltraSon;

public class TestUltraSon {
	static UltraSon ultra = new UltraSon();
	public static void main(String[] args){
		GraphicsLCD screen = LocalEV3.get().getGraphicsLCD();
		screen.drawString("Test Ultrason", 2, 20, 0);
		for(int i=0; i<5; i++){
			Button.waitForAnyPress();
			float[] r = ultra.getRaw();
			String rr = "";
			for(int j=0; j<r.length; j++){
				rr += r[j]+" ";
			}
			screen.clear();
			screen.drawString(rr, 2, 20, 0);
		}
		Button.waitForAnyPress();
	}
}
