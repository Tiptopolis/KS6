package prime.PRIME.SYS.ECS;

import prime.PRIME.N_M._N.Type;
import prime.PRIME.SYS._Environment;
import prime.PRIME.SYS._Object;


public class aSystem extends _Environment {

	public _Object Domain;

	public aSystem(String name) {
		super(name);
		this.Domain = new _Object("SYS:" + name);
		this.Of = new Type(false, this.getClass().getSimpleName(), aSystem.class);
		this.Data = this.getClass();
	}

}
