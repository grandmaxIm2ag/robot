package utils;

public abstract class Instruction {

	public Instruction() {
		// TODO Auto-generated constructor stub
	}

	public abstract <K> K accept(Visitor<K> v) throws Exception;
	
}
