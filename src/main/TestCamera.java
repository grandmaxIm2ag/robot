package main;

import java.io.IOException;
import java.util.Iterator;

import sensor.Camera;
import utils.Palet;

/**
 * 
 *Class pour le réglage de la caméra
 */
public class TestCamera {
	/**
	 * Pregramme de rglagle de la caméra
	 * @param args
	 * @throws IOException
	 */
	public static void main(String [] args) throws IOException{
		Camera.initCamera();
		Iterator<Palet> it = Camera.getPalets().iterator();
		while(it.hasNext()){
			System.out.println(it.next().toString());
		}
	}
}
