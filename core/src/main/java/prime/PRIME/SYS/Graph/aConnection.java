package prime.PRIME.SYS.Graph;

import prime.PRIME.SYS._Struct;

public class aConnection extends _Struct {

	//line lol
	public aNode from;
	public aNode to;

	public aConnection(aNode from, aNode to) {
		super("Port:");
		this.from = from;
		this.to = to;
	}
	
	public aConnection(String label, aNode from, aNode to)
	{
		super("Port:"+label);
		this.from = from;
		this.to = to;
	}
}
