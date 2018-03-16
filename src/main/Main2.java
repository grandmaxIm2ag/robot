package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

import controler.Mapper;

import utils.*;

public class Main2 {

	public static void main(String[] args) throws IOException {
		DatagramSocket dsocket=new DatagramSocket(8888);
		byte[]buffer=new byte[2048];
		DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
		dsocket.receive(packet);
		String msg=new String(buffer,0,packet.getLength());/*
		String msg = "0;198;24\n" + 
				"1;202;85\n" + 
				"2;206;145\n" + 
				"3;208;213\n" + 
				"4;208;278";*/
		String []trames=msg.split("\n");
		
		boolean south = true;
		
		List<Node> pallets = new ArrayList<>();
		Mapper m = new Mapper(true, south);
		Point pt;
		for(String s:trames) {
			String []pl=s.split(";");
			pt = new Point(Integer.parseInt(pl[1]), Integer.parseInt(pl[2]));
			//System.out.println(pt);
			pallets.add((Node) m.pointToNode(pt));
		}
		
		ArrayList<Point> points = new ArrayList<Point>();

		for (Node n : pallets) {
			System.out.println(n);
			pt = m.nodeToPoint(n);
			points.add(pt);
		}
		for (Point p : points) {
			//System.out.println(p);
		}
	}

}
