package prime.PRIME.SYS;

import static prime.PRIME.uAppUtils.*;
import static prime.PRIME.uSketcher.*;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime.METATRON.iSpace;
import prime.PRIME.N_M._N.Type;
import prime.PRIME.SYS.ECS.aEntity;
import prime.PRIME.SYS.Event._EventManager;
import prime.PRIME.SYS.Graph.aNodeGraph;

public class _Environment extends _EventManager implements iSpace {

	public Array<aEntity> Members;
	public aNodeGraph Graph;
	
	

	public _Environment(String name) {
		super(name);
		this.Members = new Array<aEntity>(false, 0, aEntity.class);
		this.Of = new Type(false, this.getClass().getSimpleName(), _Environment.class);
		this.Data = this.getClass();
		// this.Data = this.Entities;
		this.Graph = new aNodeGraph("Nodes");
	}
	
	

	@Override
	public Vector3 getUnit() {
		// TODO Auto-generated method stub
		return new Vector3(1, 1, 1);
	}

	@Override
	public Vector3 getSize() {
		return new Vector3(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
	}

	@Override
	public void dispose() {
		this.Members.clear();
		this.Graph.clear();;
	}
}
