package prime.PRIME.C_O.Prototype;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime.PRIME.C_O.Geom;
import prime.PRIME.C_O.VectorUtils;

public class ShapeFactory {

	public static BoundShape bindShape(iTransformController t, int n) {
		return bindShape((Transform) t.getTransform(), n);
	}

	public static BoundShape bindShape(Transform t, int n) {
		BoundShape newShape = new BoundShape(t);
		Array<Vector3> points = genPoly(t, n);
		for (int i = 0; i < n; i++) {
			newShape.vertices.add(points.get(i));
		}
		points.clear();
		return newShape;
	}

	public static BoundShape bindShape(Vector3 pos, Vector3 dir, Vector3 up, Vector3 scl, int n) {
		BoundShape newShape = new BoundShape();
		Array<Vector3> points = genPoly(pos, dir, up, scl, n);
		for (int i = 0; i < n; i++) {
			newShape.vertices.add(points.get(i));
		}
		points.clear();
		return newShape;
	}

	// Directed Line Segment, Ray
	public static BoundShape bindRadius(Vector3 at, Vector3 rotation, Vector3 length, Color color) {
		return bindShape(at, rotation, new Vector3(0, 0, 1), length, 2);

	}

	// Directed Line Segment, Ray
	public static BoundShape bindRadius(Vector3 at, Vector3 rotation, Vector3 up, Vector3 length, Color color) {
		return bindShape(at, rotation, up, length, 2);

	}

	// proper a-? line
	public static BoundShape bindRadian(Vector3 origin, Vector3 dir, Vector3 length, Color color) {

		return bindLine(origin, origin.cpy().add(dir.cpy().scl(length.cpy().scl(0.5f))));
	}

	public static BoundShape bindLine(Vector3 origin, Vector3 to) {
		return bindLine(origin, to, 0);
	}

	public static BoundShape bindLine(Vector3 origin, Vector3 to, int div) {
		BoundShape newShape = new BoundShape();
		Vector3 dir = VectorUtils.dir(origin.cpy(), to.cpy());
		Vector3 dst = to.cpy().sub(origin.cpy());
		newShape.scale(dst);
		newShape.rotation(VectorUtils.upcast(dir));
		newShape.position(origin.cpy());
		newShape.vertices = Geom.lineTo(origin.cpy(), to.cpy(), div);
		newShape.vertexNum = newShape.vertices.size();
		return newShape;
	}

	///////////////

	public Array<Vector3> genRadian(Vector3 at, Vector3 rotation, Vector3 length) {
		return genPoly(at, rotation, length, 1);
	}

	public Array<Vector3> genRadian(Vector3 at, Vector3 rotation, Vector3 up, Vector3 length) {
		return genPoly(at, rotation, up, length, 1);
	}

	public Array<Vector3> genRadius(Vector3 at, Vector3 rotation, Vector3 length) {
		return genPoly(at, rotation, length, 2);
	}

	public Array<Vector3> genRadius(Vector3 at, Vector3 rotation, Vector3 up, Vector3 length) {
		return genPoly(at, rotation, up, length, 2);
	}

	////////////////////////
	public static Array<Vector3> genPoly(Transform t, int n) {
		Vector3 unit = t.getScale();
		Array<Vector3> points = new Array<Vector3>(true, n, Vector3.class);
		float angle = MathUtils.PI2 / n;
		// Vector3 dir = VectorUtils.downcast(t.getLocalRotation());
		Vector3 dir = t.direction().cpy();
		for (int i = 0; i < n; i++) {

			Vector3 Dir = dir.cpy().rotate(t.getLocalNormal(), (angle * i) * MathUtils.radDeg);
			Vector3 pos = t.getPosition().cpy().add(Dir.cpy().scl(unit.cpy()));

			points.add(pos);

		}

		return points;

	}

	public static Array<Vector3> genPoly(Vector3 pos, Vector3 dir, Vector3 scl, int n) {

		Array<Vector3> points = new Array<Vector3>(true, n, Vector3.class);
		float angle = MathUtils.PI2 / n;

		for (int i = 0; i < n; i++) {

			Vector3 Dir = dir.cpy().rotate(new Vector3(0, 0, 1), (angle * i) * MathUtils.radDeg);
			Vector3 pt = pos.cpy().add(Dir.cpy().scl(scl.cpy()));

			points.add(pt);

		}

		return points;

	}

	public static Array<Vector3> genPoly(Vector3 pos, Vector3 dir, Vector3 up, Vector3 scl, int n) {

		Array<Vector3> points = new Array<Vector3>(true, n, Vector3.class);
		float angle = MathUtils.PI2 / n;

		for (int i = 0; i < n; i++) {

			Vector3 Dir = dir.cpy().rotate(up, (angle * i) * MathUtils.radDeg);
			Vector3 pt = pos.cpy().add(Dir.cpy().scl(scl.cpy()));

			points.add(pt);

		}

		return points;

	}

}
