package prime.PRIME.SYS;

import java.util.ArrayList;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

import prime.METATRON._SYS._VTS;
import prime.PRIME.SYS.ECS.aSystem;
import prime.PRIME.SYS.UI.aLayer;


public class _Shell extends aSystem {

	public _VTS VTS;
	// VFS
	// ECS

	// public Array<aLayer> Layers = new Array<aLayer>(true,0,_Environment.class);
	public ArrayList<aLayer> Layers = new ArrayList<aLayer>();

	public _Shell(String name) {
		super(name);
		this.Domain = new _Object("SHELL:" + name);
		this.Of = new Type(false, this.getClass().getSimpleName(), _Shell.class);
		this.Data = this.getClass();
	}

	public void create() {

	}

	public void create(Object... args) {

	}

}
