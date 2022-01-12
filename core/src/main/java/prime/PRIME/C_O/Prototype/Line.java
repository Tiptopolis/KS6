package prime.PRIME.C_O.Prototype;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import prime.PRIME.C_O.Geom;
import prime.PRIME.C_O.Maths;
import prime.PRIME.C_O.VectorUtils;
import prime.PRIME.C_O.Prototype.BoundShape;

public class Line extends BoundShape {

	public Line(Vector3 origin, Vector3 to) {
		this(origin, to, false);
	}

	public Line(Vector3 origin, Vector3 to, boolean norm) {
		// norm true: origin is at midPT, to is direction & length of side,degments,
		// like a plane

		Vector3 dir = VectorUtils.dir(origin.cpy(), to.cpy());
		Vector3 dst = to.cpy().sub(origin.cpy());

		this.scale(dst);
		this.rotation(dir);
		this.position(origin);
		this.vertexNum = 2;
		if (!norm) {
			this.vertices = Geom.lineTo(origin, to, (int) ((dst.len() / 3) * 2));
		} else
			this.vertices = Geom.genShape(origin, dir, new Vector3(0, 0, 1), dst, 1);
	}

	public Vector3 getOrigin() {
		return this.vertices.get(0);
	}

	public Vector3 getEnd() {
		return this.vertices.get(this.vertices.size() - 1);
	}

	public Vector3 getMidpoint() {
		int ind = this.vertices.size();
		int mid = 1;
		if (Maths.isEven(ind)) {
			mid = ind / 2;
			return this.vertices.get(mid);
		} else {
			mid = ((ind - 1) / 2);
			return this.vertices.get(mid);
		}
	}

	public float len() {
		return VectorUtils.dst(this.getOrigin(), this.getEnd()).len();
	}

	public Vector3 intersectAt(Line other) {
		return intersectAt(other, true);
	}

	public Vector3 intersectAt(Line other, boolean toFrom) {

		for (int i = 0; i < this.vertices.size(); i++) {
			Vector3 v = this.vertices.get(i);
			for (Vector3 k : other.vertices) {

				if (v.epsilonEquals(k, other.vertexSize + 1f)) {
					if (toFrom)
						return k;
					else
						return v;
				}
			}
		}
		return null;
	}

	public Array<Vector3> intersectsAt(Line other) {
		return intersectsAt(other, true);
	}

	public Array<Vector3> intersectsAt(Line other, boolean toFrom) {
		Array<Vector3> res = new Array<Vector3>(true, 0, Vector3.class);

		for (int i = 0; i < this.vertices.size(); i++) {
			Vector3 v = this.vertices.get(i);
			for (Vector3 k : other.vertices) {

				if (v.epsilonEquals(k, other.vertexSize + 1f)) {

					res.add(k);
					res.add(v);
				}
			}
		}
		if (res.isEmpty())
			return null;
		else
			return res;
	}

}
