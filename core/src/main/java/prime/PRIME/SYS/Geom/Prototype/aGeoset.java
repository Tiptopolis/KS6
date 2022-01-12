package prime.PRIME.SYS.Geom.Prototype;

import com.badlogic.gdx.utils.Array;

import prime.PRIME.C_O.Prototype.Transform;

public class aGeoset {

	public aVertex origin;
	public Array<aVertex> vertices;
	public Array<aLine> diag; // linesInner
	public Array<aLine> hull; // linesOutter
	public Array<aLine> lines;// allLines
	public Array<aFace> faces;

	public aGeoset() {
		this.origin = new aVertex();
		this.vertices = new Array<aVertex>(true, 0, aVertex.class);

		this.diag = new Array<aLine>(true, 0, aLine.class);
		this.hull = new Array<aLine>(true, 0, aLine.class);
		this.lines = new Array<aLine>(true, 0, aLine.class);

		this.faces = new Array<aFace>(true, 0, aFace.class);
	}

	public void clear() {
		this.vertices.clear();
		this.diag.clear();
		this.hull.clear();
		this.lines.clear();
		this.faces.clear();
	}

	public String whatLink(aLine l) {
		// indicates wether & how points are connected
		String result = "";
		String opn = "[";
		String cls = "]";

		int indexI = -1;
		int indexA = -1;
		int indexB = -1;
		String A = "?";
		String B = "?";
		String op = "_>";

		String isDiag = "D";
		String isShell = "S";
		String prefix = "G";

		boolean hasLine = false;
		// has a line
		if (this.lines.contains(l, true)) {
			hasLine = true;
			op = "=>";
			indexI = this.lines.indexOf(l, true);
			indexA = this.vertices.indexOf(l.from, true);
			indexB = this.vertices.indexOf(l.to, true);

		}
		// has line with same data as?
		else if (this.lines.contains(l, false)) {
			hasLine = true;
			op = "->";
			indexI = this.lines.indexOf(l, false);
			boolean hasA = this.vertices.contains(l.from, true);
			boolean hasB = this.vertices.contains(l.to, true);

			if (hasA)
				indexA = this.vertices.indexOf(l.from, true);
			if (hasB)
				indexB = this.vertices.indexOf(l.to, true);

		}

		if (hasLine) {
			if (this.diag.contains(l, true) || this.diag.contains(l, false))
				prefix = "{" + isDiag + this.diag.indexOf(l, false) + "}";
			if (this.hull.contains(l, true) || this.hull.contains(l, false))
				prefix = "{" + isShell + this.hull.indexOf(l, false) + "}";

			if (indexA != -1 && this.vertices.get(indexA) != null)
				A = this.vertices.get(indexA).get().toString();
			if (indexA == -1)
				A = this.origin.get().toString();
			if (indexB != -1 && this.vertices.get(indexB) != null)
				B = this.vertices.get(indexB).get().toString();
			if (indexB == -1)
				B = this.origin.get().toString();
			result += indexI + prefix + opn + indexA + op + indexB + cls + A + op + B + "\n";
			return result;
		}

		op = "_>_";

		if (this.vertices.contains(l.from, true)) {
			indexA = this.vertices.indexOf(l.from, true);
			opn = "[";
			A = l.from.get().toString();
		} else if (this.vertices.contains(l.from, false)) {
			indexA = this.vertices.indexOf(l.from, false);
			opn = "{*";
			A = l.from.get().toString();
		}

		if (this.vertices.contains(l.to, true)) {
			indexB = this.vertices.indexOf(l.to, true);
			cls = "]";
			B = l.to.get().toString();
		} else if (this.vertices.contains(l.to, false)) {
			indexB = this.vertices.indexOf(l.to, false);
			cls = "*}";
			B = l.to.get().toString();
		}

		result += indexI + opn + indexA + op + indexB + cls + A + op + B + "\n";
		return result;
	}

	public String toLog() {
		String log = "";

		log += "Verts: " + this.vertices.size + "\n";
		for (int v = 0; v < this.vertices.size; v++) {
			aVertex V = this.vertices.get(v);
			log += V.get() + "   |   " + V.getTransform().getLocalPosition() + "\n";
		}

		log += "Lines: " + this.lines.size + " :: " + this.diag.size + "/" + this.hull.size + "\n";

		for (int l = 0; l < this.lines.size; l++) {
			aLine L = this.lines.get(l);
			int a = this.vertices.indexOf(L.from, true);
			int b = this.vertices.indexOf(L.to, true);
			// log += "[" + a + "->" + b + "]" + L.toString() + "\n";
			log += this.whatLink(L);
		}
		log += "\n";
		log += "Faces: " + this.faces.size + "\n";
		for (int f = 0; f < this.faces.size; f++) {
			log += this.faces.get(f).toLog(this);

		}
		return log;
	}

	// lineLink
	private class LineLink {

		int indexA;
		int indexB;

		int indexI;
		int prefix = -1;

		public LineLink(int a, int b, int i, int p) {
			this.indexA = a;
			this.indexB = b;
			this.indexI = i;
			this.prefix = p;
		}

		@Override
		public String toString() {
			String s = "";

			s += indexI + ":[" + indexA + "->" + indexB + "]";

			return s;

		}
	}
}
