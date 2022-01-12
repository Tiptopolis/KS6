package prime.PRIME.SYS;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;

import prime.PRIME.N_M._N.Node;

public class aGrouping extends Node<Array<Node>> {

	public Array<Node> Members;

	public aGrouping(String label) {
		super(label, new Array<Node>());

		this.Members = new Array<Node>(false, 0, Node.class);
		this.Data = this.Members;
	}
	
	@Override
	public String toString() {
		return "[" + this.Name() + ":" + this.get() + "]|<" + this.Class().getSimpleName() + ">:" + this.get()
				+ "]";
	}
	
	public<O,R> R hit(O obj)
	{
		return null;
	}

}
