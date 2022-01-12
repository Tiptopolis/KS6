package prime.PRIME.SYS.Graph;

import prime.PRIME.N_M._N.Type;

public class aNodeBuilder {

	public static <T> aNode createNode(String label, T of, Type type) {
		return new aNode(label, of, type);
	}

}
