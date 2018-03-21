package utils;

import java.util.Map;

import exception.InstructionException;

/**
 * Fabrique d'intruction
 */
public class FactoryInstruction {
	/**
	 * Map reliant un string à un noeud
	 */
	private static Map<String, Node> map;
	/**
	 * Initialise map
	 * @param n la nouvelle map
	 */
	public static void init_map(Map<String, Node> m){
		map = m;
	}
	
	/**
	 * @param ins
	 * @return
	 * @throws InstructionException 
	 */
	public static Instruction create(String ins) throws InstructionException{
		String ins_tab[] = ins.split(" ");
		if(ins_tab[0].equals("move")){
			return new Move(map.get(ins_tab[1]),map.get(ins_tab[2]));
		}else if(ins_tab[0].equals("pick")){
			Palet p = new Palet(map.get(ins_tab[1]), true);
			return new Pick(p, map.get(ins_tab[2]));
		}else if(ins_tab[0].equals("deliver")){
			Palet p = new Palet(map.get(ins_tab[1]), true);
			return new Deliver(p);
		}else{
			throw new exception.InstructionException("Instruction "+ins_tab[0]+
					"inconnue");
		}
	}
}
