package main;

import java.io.IOException;
import java.util.Iterator;

import sensor.Camera;
import utils.Palet;

public class TestCamera {
	public static void main(String [] args) throws IOException{
		Camera.initCamera();
		Iterator<Palet> it = Camera.getPalets().iterator();
		while(it.hasNext()){
			System.out.println(it.next().toString());
		}
	}
}
