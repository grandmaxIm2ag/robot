package main;

import lejos.utility.Delay;
import controler.Controler;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Controler controler = new Controler();
		try{
			controler.start();
		}catch(Throwable e){
			e.printStackTrace();
			Delay.msDelay(10000);
		}
		System.exit(0);
	}
}
