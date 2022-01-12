package prime.PRIME.C_O.Prototype;

import static prime.PRIME.uAppUtils.*;
import static prime.PRIME.uSketcher.*;
import static prime.METATRON.Metatron.*;
import static prime.PRIME.C_O.VectorUtils.*;
import static prime.PRIME.C_O.Maths.*;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public interface iTransformController {

	// Position
	public default Vector3 position() {
		return this.position(false);
	}

	public default Vector3 position(boolean local) {
		Transform T = this.getTransform();
		if (local)
			return T.getLocalPosition();
		else
			return T.getPosition();
	}

	public default void position(Vector3 position) {
		this.position(position, false);
	}

	public default void position(Vector3 position, boolean local) {
		Transform T = this.getTransform();
		if (local)
			T.setLocalPosition(position);
		else
			T.setPosition(position);
	}

	// Rotation

	public default Quaternion rotation() {
		return this.rotation(false);
	}

	public default Quaternion rotation(boolean local) {
		Transform T = this.getTransform();

		if (local)
			return T.getLocalRotation();
		else
			return T.getRotation();
	}

	public default void rotation(Quaternion rotation) {
		this.rotation(rotation, false);
	}

	public default void rotation(Quaternion rotation, boolean local) {
		Transform T = this.getTransform();
		if (local)
			T.setLocalRotation(rotation);
		else
			T.setRotation(rotation);
	}

	public default void rotation(Vector3 direction) {
		this.rotation(direction, true);
	}

	public default void rotation(Vector3 direction, boolean local) {
		this.rotation(upcast(direction), local);
	}

	public default void normal(Vector3 normal) {
		this.getTransform().setLocalNormal(normal);
	}

	public default Vector3 normal() {
		return this.getTransform().getLocalNormal();
	}

	public default Vector3 direction() {
		Vector3 d = downcast(this.rotation(true));
		Vector3 s = downcast(Transform.SpatialBasis.getLocalRotation());
		if (isEqual(d, new Vector3(0, 0, 0), 0.0001f))
			return s;
		else
			return d;

	}

	// Scale
	public default Vector3 scale() {
		return this.scale(false);
	}

	public default Vector3 scale(boolean local) {
		Transform T = this.getTransform();
		if (local)
			return T.getLocalScale();
		else
			return T.getScale();
	}

	public default void scale(Vector3 scale) {
		this.scale(scale, false);
	}

	public default void scale(Vector3 scale, boolean local) {
		Transform T = this.getTransform();
		if (local)
			T.setLocalScale(scale);
		else
			T.setScale(scale);
	}

	//////////////////////////////////////////////

	public default void translate(Vector3 by) {
		// relative to world
		Transform t = this.getTransform();
		t.setPosition(t.getLocalPosition().add(by));
	}

	public default void move(Vector3 by) {
		// relative to self, (Right,Forward,Up);
		Transform t = this.getTransform();
		Vector3 result = new Vector3(rR(by.x).add(rF(by.y).add(rU(by.z))));
		Vector3 scl = div(this.scale(true), this.scale(false)).scl(0.5f);
		t.setLocalPosition(t.getLocalPosition().add(result.nor().scl(scl)));

	}

	default Vector3 rF(float deltaForward) {

		Vector3 tmp = new Vector3(this.direction().nor().scl(deltaForward));

		return tmp;
	}

	default Vector3 rU(float deltaUp) {

		Vector3 tmp = new Vector3(this.normal().nor().scl(deltaUp));

		return tmp;
	}

	default Vector3 rR(float deltaRight) {

		Vector3 tmp = new Vector3(this.direction().crs(this.normal()).nor().scl(deltaRight));

		return tmp;
	}

	public default void rotate(Vector3 axis, float degrees) {
		Vector3 d = downcast(this.getTransform().getLocalRotation());

		if (isEqual(d, Vector3.Zero, 0.001f))
			d.set(downcast(Transform.SpatialBasis.getLocalRotation()));

		d.rotate(axis, degrees);
		this.getTransform()._localNormal.rotate(axis, degrees);
		this.getTransform().setLocalRotation(upcast(d));
		for (Transform c : this.getTransform().getChildren()) {
			c.rotate(axis, degrees);
			rotOutter(c, axis, degrees);
		}

	}

	static void rotOutter(Transform c, Vector3 axis, float degrees) {
		Vector3 T = c.getLocalPosition();
		T.rotate(axis, degrees);
		c.setLocalPosition(T);
	}

	public Transform getTransform();
}
