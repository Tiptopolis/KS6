package prime.PRIME.SYS.Graph;

import prime.PRIME.C_O.Prototype.Dictionary;
import prime.PRIME.C_O.Prototype.MultiMap;
import prime.PRIME.C_O.Prototype.Transform;
import prime.PRIME.N_M._N;
import prime.PRIME.N_M._N.Node;

public class aNode<T> extends Node<T> {

	public aNodeGraph of;
	public Transform transform;

	public Dictionary<String, aNodePort> ports = new Dictionary<String, aNodePort>(); // override connect methods, use Str lookup

	public aNode() {
		super();
		this.transform = new Transform();
	}

	public aNode(T data) {
		super(data);
		this.transform = new Transform();
	}

	public aNode(String label, T data) {
		super(label, data);
		this.transform = new Transform();
	}

	public aNode(_N data) {
		super(data);
		this.transform = new Transform();
	}

	public aNode(String label, _N data) {
		super(label, data);
		this.transform = new Transform();
	}

	public aNode(Type type) {
		super(type);
		this.transform = new Transform();
	}

	public aNode(String label, Type type) {
		super(label, type);
		this.transform = new Transform();
	}

	public aNode(String label, T data, Type type) {
		super(label, data, type);
		this.transform = new Transform();
	}

	public Object Data() {
		if (this.Data instanceof Node)
			return ((Node) this.Data).Data;
		else
			return this.Data;
	}

	@Override
	public boolean connect(String con, Node other) {
		// check if Node is aNode, look for available port of 'con'
		// expand convenience

		return super.connect(con, other);
	}
	
	@Override
	public String toString()
	{
		return "["+this.Label+"]:{"+super.toString() + "}";
	}

	@Override
	public String toLog() {

		return super.toLog();
	}
}
