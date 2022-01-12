package prime.PRIME.SYS.Graph;

import prime.PRIME.C_O.Prototype.StateMap;
import prime.PRIME.N_M._N.Node;

public class aNodePort extends Node<aNodePort> {

	public aNode node;
	public ConnectionState state = ConnectionState.CLOSED;
	public Direction direction;

	@Override
	public boolean connect(String con, Node other) {
		// check if Node is aNode, look for available port of 'con'
		// expand convenience

		return super.connect(con, other);
	}

	public enum ConnectionState {
		OPEN, CLOSED, CONNECTED;
	}

	public enum Direction {
		INPUT, OUTPUT;
	}
}
