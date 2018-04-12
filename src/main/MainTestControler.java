package main;

import lejos.utility.Delay;
import controler.TestControler;

/**
 * 
 * Classe pour les tests des différentes fonction de Robot
 */
public class MainTestControler {

	/**
	 * Programme de test
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
