package prime.PRIME.SYS.Graph;

import java.util.ArrayList;

import prime.PRIME.C_O.Prototype.MultiMap;
import prime.PRIME.N_M._N.Node;

public class aNodeGraph extends Node<aNodeGraph> {

	public ArrayList<aNode> nodes;

	public aNodeGraph(String title) {
		this.nodes = new ArrayList<aNode>();
	}

	public void addNode(aNode n) {
		this.nodes.add(n);
	}

	public void addNode(Node n) {
		this.nodes.add(new aNode(n));
	}

	public void removeNode(aNode n) {
		if (this.nodes.contains(n))
			this.nodes.remove(n);
	}

	public void removeNode(Node n) {
		for (aNode N : this.nodes) {
			if (N.get() == n || n.get().equals(n)) {
				this.removeNode(N);
			}
		}
	}

	public void clear() {
		this.nodes.clear();
		this.Connections.clear();
		this.Properties.clear();
	}

}
