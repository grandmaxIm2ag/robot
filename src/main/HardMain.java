package main;

import lejos.utility.Delay;
import controler.HardRobotControler;
import controler.RobotControler;

public class HardMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RobotControler controler = new HardRobotControler();
		try{
			controler.start();
		}catch(Throwable e){
			e.printStackTrace();
			Delay.msDelay(10000);
		}
		System.exit(0);
	}
}