package prime.PRIME.C_O.Prototype;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime.PRIME.C_O.VectorUtils;

public class BoundShape implements iTransformController, Iterable<Vector3>{
	public Transform transform;
	public Color fillColor = Color.WHITE;
	public Color lineColor = Color.GRAY;
	public int vertexNum = 0;
	public float vertexSize = 2;
	public float lineWidth = 1;

	public ArrayList<Vector3> vertices = new ArrayList<Vector3>();
	protected int OriginIndex = 0; // if 0, return position

	private boolean dirty = false;
	public boolean debug = true;
	public boolean fill = false;
	public boolean lines = true;
	public boolean linesOutter = true;
	public boolean linesInner = true;

	public BoundShape() {
		this.transform = new Transform();
	}
	
	public BoundShape(int n) {
		this.transform = new Transform();
		this.transform.setParent(transform);
	}

	public BoundShape(Transform transform) {
		this.transform = new Transform();
		this.transform.setParent(transform);
	}
	
	public BoundShape(Transform transform, int n) {
		this.transform = new Transform();
		this.transform.setParent(transform);
	}
	
//////
	// VERTICES
	public int size() {
		return this.vertices.size();
	}

	public Vector3 get(int index) {
		return this.vertices.get(index);
	}

	public boolean isEmpty() {
		return this.vertices.isEmpty();
	}

	public void clear() {
		this.vertices.clear();
	}
	
	public void dispose()
	{
		this.clear();
		this.transform.removeParent();
		this.transform = null;
	}

	public void add(Vector3... V) {
		for (Vector3 v : V) {
			this.vertices.add(v);
		}
	}

	public int indexOf(Vector3 v) {
		return this.vertices.indexOf(v);
	}
	// VERTICES
	//////
	
	public float perimeter() {
		float sum = 0f;
		for (int i = 0; i < this.vertexNum; i++) {
			sum = sum + this.vertices.get(i).dst(this.vertices.get(i + 1));
		}
		return sum;
	}

	// return signed area of polygon
	public double area() {
		double sum = 0.0;
		for (int i = 0; i < this.vertexNum; i++) {
			sum = sum + (this.vertices.get(i).x * this.vertices.get(i + 1).y)
					- (this.vertices.get(i).y * this.vertices.get(i + 1).x)
					- (this.vertices.get(i).z * this.vertices.get(i + 1).z);
		}
		return 0.5 * sum;
	}
	
	public boolean contains(float x, float y) {

		return (this.toPolygon().contains(x, y));

	}

	public boolean contains(Vector2 v) {

		return contains(v.x, v.y);
	}

	public boolean contains(Vector3 v) {
		return this.contains(v.x, v.y, v.z);
	}

	public boolean contains(float x, float y, float z) {
		// bandaid solution
		Vector3 v = new Vector3(x, y, z);
		Vector3 pos = this.position();

		Array<Vector3> pts = new Array<Vector3>(true, this.vertexNum * 2, Vector3.class);
		for (int i = 0; i < this.vertices.size(); i++) {
			int n = i + 1;
			if (i == this.vertices.size() - 1)
				n = 0;

			Vector3 P = this.vertices.get(i);
			Vector3 N = this.vertices.get(n);

			Line l = new Line(P, N);
			Vector3 M = l.getMidpoint();

			pts.add(P);
			pts.add(M);
		}

		for (Vector3 p : pts) {
			// rig up a counter, testPt dst is greater than 2 points on the same side, its
			// outside
			if (VectorUtils.dst(pos, v).len() > VectorUtils.dst(pos, p).len())
				return false;
		}

		return true;
	}
	
	////////////////

	public Polygon toPolygon() {

		ArrayList<Vector2> tmpVerts = new ArrayList<Vector2>();
		for (Vector3 v : this.vertices) {
			tmpVerts.add(VectorUtils.downcast(v.cpy()));
		}

		Polygon result = new Polygon(VectorUtils.disassembleVects(tmpVerts.toArray()));

		if (this.transform != null) {
			Vector3 scl = this.transform.getLocalScale();
			result.setOrigin(this.transform.getLocalPosition().x, this.transform.getLocalPosition().y);
			// result.setScale(scl.x, scl.y);
			Quaternion rot = this.transform.getLocalRotation();
			result.setRotation((float) Math.atan2(rot.x, rot.y));
			result.setPosition(this.transform.getLocalPosition().x, this.transform.getLocalPosition().y);
			// result.setOrigin(this.transform.GetLocalPosition().x,
			// this.transform.GetLocalPosition().y);
		}

		return result;
	}

	@Override
	public String toString() {
		String out = "";
		out += this.vertexNum + "";
		out += "\n";

		out += "P: " + this.position();
		out += "  L" + this.position(true);
		out += "\n";
		out += "R: " + this.rotation();
		out += "  L" + this.rotation(true);
		out += "\n";
		out += "S: " + this.scale();
		out += "  L" + this.scale(true);
		out += "\n";

		return out;
	}

	@Override
	public Iterator<Vector3> iterator() {
		Iterator<Vector3> it = new Iterator<Vector3>() {

			private int currentIndex = 0;

			@Override
			public boolean hasNext() {
				return currentIndex < vertices.size() && vertices.get(currentIndex) != null;
			}

			@Override
			public Vector3 next() {
				return vertices.get(currentIndex++);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
		return it;
	}

	@Override
	public Transform getTransform() {
		return this.transform;
	}
}
