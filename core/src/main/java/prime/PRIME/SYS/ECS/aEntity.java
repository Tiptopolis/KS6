package prime.PRIME.SYS.ECS;

import prime.METATRON.iMonad;
import prime.PRIME.N_M._N.Type;
import prime.PRIME.SYS._Environment;
import prime.PRIME.SYS._Object;

public class aEntity extends _Object implements iMonad {

	public static Type Entity = new Type("Entity", aEntity.class);
	
	public _Environment Environment;
	
	public aEntity(String name) {
		super(name);
		//this.Label = "Entity";
		//this.Of = new Type(false, this.getClass().getSimpleName(), aEntity.class);
		//this.Data = aEntity.class;
		//this.Node = Entity;
	}
	
	public aEntity(String name, _Environment e)
	{
		super(name);
		this.Environment = e;
		
	}
	

}
