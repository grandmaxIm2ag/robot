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
	 * @param m la nouvelle map
	 */
	public static void init_map(Map<String, Node> m){
		map = m;
	}
	/**
	 * 
	 */
	private static boolean south;
	public static void init_south(boolean s) {
		south = s;
	}
	/**
	 * Fabrique d'instruction
	 * @param ins un string représentant l'instruction
	 * @return l'instruction
	 * @throws InstructionException 
	 */
	public static Instruction create(String ins) throws InstructionException{
		String ins_tab[] = ins.split(" ");
		if(ins_tab[0].equals("move")){
			Node src = map.get(ins_tab[1]);
			Node dest;
			if(ins_tab[2].equals("dock")){
				dest = new Node(src.getI(), south ? 1 : 5);
			}else{
				dest = map.get(ins_tab[2]);
			}
			return new Move(src, dest);
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
