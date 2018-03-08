package utils;

import controler.Visitor;

public abstract class Instruction {

	public Instruction() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract boolean accept(Visitor<Boolean> v) throws Exception;
}
