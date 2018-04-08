package main;

import java.io.IOException;
import java.util.Iterator;

import sensor.Camera;
import utils.Palet;
import utils.Point;

public class TestCamera2 {
	public static void main(String [] args) throws IOException{
		Camera.initArtificialCamera();
		Iterator<Palet> it0 = Camera.getArtificialPalets().iterator();
		Iterator<Point> it2 = Camera.getCorrectedPoints().iterator();
		Iterator<Point> it3 = Camera.getTheoreticalPoints().iterator();
		Iterator<Point> it1 = Camera.getArtificialPoints().iterator();
		while(it1.hasNext() && it2.hasNext() && it3.hasNext()){
			System.out.println("Before   : " + it1.next().toString());
			System.out.println("After    : " + it2.next().toString());
			System.out.println("Expected : " + it3.next().toString());
			System.out.println("###################################");
		}
	}
}
