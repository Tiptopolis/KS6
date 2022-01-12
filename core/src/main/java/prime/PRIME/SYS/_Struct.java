package prime.PRIME.SYS;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Array;

import prime.PRIME.C_O.Prototype.MultiMap;
import prime.PRIME.N_M._N.Node;
import prime.PRIME.N_M._N.Type;

public class _Struct extends Node<Object> {

	public static Type Struct = new Type("Struct", _Struct.class);
	
	public _Struct() {
		super("Struct",Struct);
		//this.Label = "Struct";
		//this.Of = new Type(false, this.getClass().getSimpleName(), _Struct.class);
		//this.Data = _Struct.class;
		this.Node = Struct;
		this.Data = this.getClass();
	}

	public _Struct(String name) {
		super(name, Struct);
		this.Node = Struct;
		this.Data = this.getClass();
	}

	@Override
	public Object get(String propName) {
		for (Node N : this.Properties) {
			if (N.Name().equals(propName))
				return N;
		}
		return propName;

	}

	@Override
	public void set(String propName, Object t) {
		Array<Node> matching = new Array<Node>(false, 0, Node.class);
		for (Node N : this.Properties) {
			if (N.Node.equals(propName) || N.Node.equals("<" + propName + ">") || N.Name().equals(propName)
					|| N.Name().equals("<" + propName + ">")) {
				matching.add(N);
				try {
					N.set(t);
				} finally {

				}
			}
		}
		matching.clear();
	}

	public void set(_Property prop, Object t) {
		try {
			prop.set(t);
		} finally {

		}
	}

	public void addProperty(_Property P) {
		if (!this.Properties.contains(P, true) && !this.Properties.contains(P, false)) {
			this.Properties.add(P);
		}
	}

	public void dispose() {
		this.Properties.clear();
		this.Connections.clear();
	}

	@Override
	public String toString() {
		return "[" + this.Name() + this.hashString() + ":" + this.Type() + "]|<" + this.Class().getSimpleName() + ">:"
				+ this.get() + "]";
	}

	@Override
	public String toLog() {
		String log = this.toString() + "\n";
		log += this.Node + "\n";
		log += this.get() + "\n";
		log += ":PROPERTIES:\n";
		for (int i = 0; i < this.Properties.size; i++) {
			Node N = this.Properties.get(i);
			log += i + "]:" + N + "\n";
		}
		log += "--------------\n";
		return log;
	}

}
