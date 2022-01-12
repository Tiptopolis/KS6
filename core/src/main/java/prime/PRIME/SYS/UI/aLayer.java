package prime.PRIME.SYS.UI;

import com.badlogic.gdx.utils.Array;

import prime.PRIME.SYS._Environment;
import prime.PRIME.SYS._Shell;
import prime.PRIME.SYS.ECS.aEntity;


public class aLayer extends _Environment {

	// a Mask for containing environment
	public _Shell of;
	int IndexDepth;

	public Array<aEntity> Members = new Array<aEntity>();

	public aLayer(_Shell of, String name) {
		super(name);
		this.of = of;
		of.Layers.add(this);
		this.IndexDepth = of.Layers.indexOf(this);
	}

}
