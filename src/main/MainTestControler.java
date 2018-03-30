package main;

import lejos.utility.Delay;
import controler.TestControler;

public class MainTestControler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestControler controler = new TestControler();
		try{
			controler.start();
		}catch(Throwable e){
			e.printStackTrace();
			Delay.msDelay(10000);
		}
		System.exit(0);
	}
}
