package main;

import lejos.utility.Delay;
import controler.Controler;
import controler.RobotControler;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RobotControler controler = new RobotControler();
		try{
			controler.start();
		}catch(Throwable e){
			e.printStackTrace();
			Delay.msDelay(10000);
		}
		System.exit(0);
	}
}
