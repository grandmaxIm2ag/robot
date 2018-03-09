package controler;

import utils.Deliver;
import utils.Instruction;
import utils.Move;
import utils.Pick;

public interface Visitor<E> {
	
	public abstract E visit(Instruction i) throws Exception; 
	public abstract E visit(Move m) throws Exception; 
	public abstract E visit(Deliver d) throws Exception; 
	public abstract E visit(Pick p) throws Exception; 
	
}
