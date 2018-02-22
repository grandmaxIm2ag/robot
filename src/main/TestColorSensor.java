package main;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;
import sensor.ColorSensor;

public class TestColorSensor {
	
	public static void main(String[] args){
		ColorSensor color = new ColorSensor();
		GraphicsLCD screen = LocalEV3.get().getGraphicsLCD();
		screen.drawString("Calibration", 2, 20, 0);
		Button.waitForAnyPress();
		screen.clear();
		if(true){
			
			color.lightOn();
			
			for(int i=1; i<=3; i++){
				//calibration gris
				screen.drawString("Gris "+i, 2, 20, 0);
				Button.waitForAnyPress();
				color.calibrateColor(Color.GRAY);
			}
			
			for(int i=1; i<=3; i++){
				//calibration blue
				screen.clear();
				screen.drawString("Bleu "+i, 2, 20, 0);
				Button.waitForAnyPress();
				color.calibrateColor(Color.BLUE);
			}
			
			for(int i=1; i<=3; i++){
				//calibration noir
				screen.clear();
				screen.drawString("Noir "+i, 2, 20, 0);
				Button.waitForAnyPress();
				color.calibrateColor(Color.BLACK);
			}
			
			for(int i=1; i<=3; i++){
				//calibration jaune
				screen.clear();
				screen.drawString("Jaune "+i, 2, 20, 0);
				Button.waitForAnyPress();
				color.calibrateColor(Color.YELLOW);
			}
			
			for(int i=1; i<=3; i++){
				//calibration green
				screen.clear();
				screen.drawString("Vert "+i, 2, 20, 0);
				Button.waitForAnyPress();
				color.calibrateColor(Color.GREEN);
			}
			
			for(int i=1; i<=3; i++){
				//calibration red
				screen.clear();
				screen.drawString("Rouge "+i, 2, 20, 0);
				Button.waitForAnyPress();
				color.calibrateColor(Color.RED);
			}
			
			for(int i=1; i<=3; i++){
				//calibration white
				screen.clear();
				screen.drawString("Blanc "+i, 2, 20, 0);
				Button.waitForAnyPress();
				color.calibrateColor(Color.WHITE);
			}
		}
		
		for(int i=0; i<7;i++){
			Button.waitForAnyPress();
			screen.clear();
			int c = color.getCurrentColor();
			if(c == Color.RED){
				screen.drawString("Rouge", 2, 20, 0);
			}else if(c == Color.BLUE){
				screen.drawString("Bleu", 2, 20, 0);
			}else if(c == Color.GRAY){
				screen.drawString("Gris", 2, 20, 0);
			}else if(c == Color.GREEN){
				screen.drawString("Vert", 2, 20, 0);
			}else if(c == Color.YELLOW){
				screen.drawString("Jaune", 2, 20, 0);
			}else if(c == Color.BLACK){
				screen.drawString("Noir", 2, 20, 0);
			}else if(c == Color.WHITE){
				screen.drawString("Blanc", 2, 20, 0);
			}else{
				screen.drawString("Inconnue", 2, 20, 0);
			}
		}
	}
}
