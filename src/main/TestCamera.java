package main;

import java.io.IOException;
import java.util.Iterator;

import sensor.Camera;
import utils.Palet;

public class TestCamera {
	public static void main(String [] args) throws IOException{
		Iterator<Palet> it = Camera.getPalet().iterator();
		while(it.hasNext()){
			System.out.println(it.next().toString());
		}
	}
}