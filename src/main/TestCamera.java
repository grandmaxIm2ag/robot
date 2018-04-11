package main;

import java.io.IOException;
import java.util.Iterator;

import sensor.Camera;
import sensor.Camera2;
import utils.Palet;

public class TestCamera {
	public static void main(String [] args) throws IOException{
		Camera2.init_camera();
		Iterator<Palet> it = Camera2.getPalets().iterator();
		while(it.hasNext()){
			System.out.println(it.next().toString());
		}
	}
}
