package prime.PRIME.SYS.Geom.Prototype;

import com.badlogic.gdx.math.Vector3;

import prime.PRIME.C_O.Prototype.Transform;

public class aVertex {

	private Transform transform;

	protected aVertex() {
		this.transform = new Transform();
	}

	public aVertex(Vector3 at) {
		this(at, false);
	}

	public aVertex(Transform of, Vector3 at) {
		this.transform = new Transform();
		this.transform.position(at, true);
		this.transform.setParent(of);
	}
	public aVertex(Vector3 at, boolean local) {
		this.transform = new Transform();
		this.transform.position(at, local);
	}

	public aVertex(Transform of, Vector3 at, boolean local) {
		this.transform = new Transform();
		this.transform.position(at, local);
		this.transform.setParent(of);
	}
	
	public Vector3 get(boolean local) {
		if (local)
			return this.transform.getLocalPosition();
		else
			return this.transform.getPosition();
	}

	public Vector3 get() {
		return this.transform.getPosition();
	}

	public void set(Vector3 at, boolean local) {
		this.transform.position(at, local);
	}

	public void set(Vector3 at) {
		this.transform.position(at);
	}

	protected Transform getTransform() {
		return this.transform;
	}

	@Override
	public String toString() {
		return ">" + this.get().toString() + "<";
	}
}
