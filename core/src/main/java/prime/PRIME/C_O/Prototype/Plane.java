package prime.PRIME.C_O.Prototype;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;


public class Plane implements Comparable<Vector3> {

	public Vector3 origin = new Vector3(0, 0, 0);
	public Vector3 normal = new Vector3(0, 0, 1);
	public float D = 0;

	public Plane() {

	}

	public Plane(Vector3 normal, float d) {
		this.normal.set(normal).nor();
		this.D = d;
	}

	public Plane(Vector3 normal, Vector3 point) {
		this.origin = point;// ?maybe?
		this.normal.set(normal).nor();
		this.D = -this.normal.dot(point);
	}

	public Plane(Vector3 point1, Vector3 point2, Vector3 point3) {
		set(point1, point2, point3);
	}

	public float distance(Vector3 point) {
		return normal.dot(point) + D;
	}

	@Override
	public int compareTo(Vector3 o) {
		float dist = distance(o);
		// OnPlane
		if (dist == 0)
			return 0;
		// Back
		else if (dist < 0)
			return -1;
		// Front
		else
			return 1;

	}

	public void set(Vector3 point, Vector3 normal) {
		this.normal.set(normal);
		D = -point.dot(normal);
	}

	public void set(float pointX, float pointY, float pointZ, float norX, float norY, float norZ) {
		this.normal.set(norX, norY, norZ);
		D = -(pointX * norX + pointY * norY + pointZ * norZ);
	}

	public void set(Vector3 point1, Vector3 point2, Vector3 point3) {
		normal.set(point1).sub(point2).crs(point2.x - point3.x, point2.y - point3.y, point2.z - point3.z).nor();
		D = -point1.dot(normal);
	}

	public Vector3 ClosestPointOnPlane(Vector3 point) {
		float pointToPlaneDistance = (this.normal.cpy().dot(point.cpy())) + this.D;
		return point.sub(this.normal.cpy().scl(pointToPlaneDistance));
	}



}
