package prime.PRIME.C_O.Prototype;

import static prime.PRIME.C_O.VectorUtils.*;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime.PRIME.C_O.Geom;
import prime.PRIME.C_O.VectorUtils;

public class Transform implements iTransformController {

	public static Transform SpatialBasis = new Transform('3');

	private Vector3 _localPosition;
	private Quaternion _localRotation;
	private Vector3 _localScale;
	public Vector3 _localNormal;// localUp

	private Transform _Parent;
	private Array<Transform> _Children;

	private Transform(char l) {
		this._Children = new Array<Transform>();
		if (l == ' ')
			l = 3;

		if (l == 1) {
			this._localPosition = new Vector3(0, 0, 0);
			this._localRotation = new Quaternion(0, 0, 0, 0);
			this._localNormal = new Vector3(1, 0, 0);
			this._localScale = new Vector3(1, 1, 1);
		}
		if (l == 2) {
			this._localPosition = new Vector3(0, 0, 0);
			this._localRotation = new Quaternion(1, 0, 0, -1);
			this._localNormal = new Vector3(0, 1, 0);
			this._localScale = new Vector3(1, 1, 1);
		}
		if (l == '3') {
			this._localPosition = new Vector3(0, 0, 0);
			this._localRotation = new Quaternion(0, 1, 0, -1);
			this._localNormal = new Vector3(0, 0, 1);
			this._localScale = new Vector3(1, 1, 1);
		}

	}

	public Transform() {
		this._localPosition = new Vector3(0, 0, 0);
		this._localRotation = new Quaternion(0, 0, 0, -1);
		this._localNormal = new Vector3(0, 0, 1);
		this._localScale = new Vector3(1, 1, 1);
		this._Children = new Array<Transform>();
		this.setParent(SpatialBasis);

	}

	public Vector3 getPosition() {

		// return this.getLocalPosition();
		if (this._Parent != null) {

			Vector3 pPos = _Parent.getPosition().cpy();
			Vector3 pDir = downcast(_Parent.getLocalRotation().cpy());
			Vector3 pRot = downcast(_Parent.getRotation().cpy());
			Vector3 scl = this.getScale().cpy();
			Vector3 up = _Parent._localNormal.cpy();

			return (pPos.add(this.getLocalPosition().scl(scl)));

		} else
			return this.getLocalPosition();
	}

	public Vector3 getLocalPosition() {

		return this._localPosition.cpy();

	}

	//broken, do not use yet
	public void setPosition(Vector3 position) {
		Vector3 pPos = _Parent.getPosition().cpy();
		Vector3 pDir = downcast(_Parent.getLocalRotation().cpy());
		Vector3 pRot = downcast(_Parent.getRotation().cpy());
		Vector3 scl = this.getScale().cpy();
		Vector3 up = _Parent._localNormal.cpy();
		
		if (this._Parent == null)
			this.setLocalPosition(div(pPos.sub(position),(scl)));
			//this.setLocalPosition(dst(this._Parent.getPosition(), position).scl(downcast(_Parent.getLocalRotation())));
		else {
			this.setLocalPosition(position);
			
		}
	}

	public void setLocalPosition(Vector3 position) {

		this._localPosition.set(position);
		this.updateMatrices();
	}

	public Quaternion getRotation() {

		if (this._Parent != null) {

			if (this._Parent.getRotation().cpy().mul(this.getLocalRotation()).len() == 0)
				return this._Parent.getRotation();
			else
				return this._Parent.getRotation().cpy().mul(this.getLocalRotation());
		} else {
			return this._localRotation.cpy();
		}
	}

	public Quaternion getLocalRotation() {

		return this._localRotation.cpy();
	}

	public void setRotation(Quaternion rotation) {
		if (this._Parent == null)
			this.setLocalRotation(rotation);
		else {
			this.setLocalRotation(this._Parent.getRotation().mul(rotation));
		}
	}

	public void setLocalRotation(Quaternion rotation) {
		this._localRotation.set(rotation);
	}

	public Vector3 getLocalNormal() {
		return this._localNormal.cpy();
	}

	public void setLocalNormal(Vector3 normal) {
		this._localNormal.set(normal);
	}

	public Vector3 getScale() {

		if (this._Parent != null) {
			return this._Parent.getScale().cpy().scl(this.getLocalScale());
		} else
			return this.getLocalScale();
	}

	public Vector3 getLocalScale() {

		return this._localScale.cpy();
	}

	public void setScale(Vector3 scale) {
		if (this._Parent == null)
			this.setLocalScale(scale);
		else {
			this.setLocalScale(this._Parent.getScale().scl(scale));
		}
	}

	public void setLocalScale(Vector3 scale) {
		this._localScale.set(scale);
		this.updateMatrices();
	}

	public void removeParent() {
		if (this._Parent != null) {
			this._Parent._Children.removeValue(this, true);
			this._Parent = null;
		}
		this.updateMatrices();
	}

	public void setParent(Transform t) {
		if (t == this)
			return;
		if (this._Parent != null)
			this.removeParent();
		
		if(t instanceof iTransformController && !(t instanceof Transform))
			t=t.getTransform();

		if (t._Children == null)
			t._Children = new Array<Transform>();
		t._Children.add(this);
		this._Parent = t;
		this.updateMatrices();
		this._Parent.updateMatrices();
	}

	public Transform getParent() {
		if (this._Parent != null)
			return this._Parent;
		else
			return null;
	}

	public void updateMatrices() {
		Object o = this;
		o = this.getScale();
		o = this.getRotation();
		o = this.getPosition();
		for (Transform T : this._Children)
			T.updateMatrices();
	}

	public Array<Transform> getChildren() {
		return this._Children;
	}

	@Override
	public Transform getTransform() {

		return this;
	}

	@Override
	public String toString() {
		String s = "";
		s += "W:" + this.getPosition() + "::" + this.getRotation() + "::" + this.getScale() + "\n";
		s += "L" + this.getLocalPosition() + "::" + this.getLocalRotation() + "::" + this.getLocalScale() + "\n";
		if (this._Parent != null) {
			s += " PW" + this._Parent.getPosition() + "::" + this._Parent.getRotation() + "::" + this._Parent.getScale()
					+ "\n";
			s += " PL" + this._Parent.getLocalPosition() + "::" + this._Parent.getLocalRotation() + "::"
					+ this._Parent.getLocalScale() + "\n";
		}

		return s;
	}

}
