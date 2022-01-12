package prime.TEST.zT2;

import static prime.PRIME.uAppUtils.*;
import static prime.PRIME.uSketcher.*;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime.EXT.v3d.ext.quickhull3d.Point3d;
import prime.EXT.v3d.ext.quickhull3d.QuickHull3D;
import prime.PRIME.C_O.Prototype.OroborosList;

public class _PlottyMcPlotface {

	public static OroborosList<Vector3> RedList;
	public static OroborosList<Vector3> GreenList;
	public static OroborosList<Vector3> BlueList;

	public _PlottyMcPlotface() {
		RedList = new OroborosList<Vector3>(1, 1024, 2);
		GreenList = new OroborosList<Vector3>(1, 1024, 1);
		BlueList = new OroborosList<Vector3>(1, 1024, 1);
	}

	public void plot(Vector3 r, Vector3 g, Vector3 b) {
		RedList.add(r);
		GreenList.add(g);
		BlueList.add(b);
	}

	public void draw() {
		Sketcher.setColor(Color.RED);
		for (Vector3 r : this.RedList) {

			Vector3 next;
			Vector3 last;
			int n = 0;
			int s = RedList.size() - 1;

			for (int i = 0; i < s + 1; i++) {

				int f = s + 1;
				n = i + 1;
				if (n >= f)
					n = (n % f);

				last = RedList.get(i);
				next = RedList.get(n);

				Sketcher.setColor(1, 0, 0, 0.75f);
				Sketcher.Drawer.line(last.x, last.y, next.x, next.y);

			}

			Sketcher.Drawer.circle(r.x, r.y, Math.abs(r.z / 24));
		}
		Sketcher.setColor(Color.GREEN);
		for (Vector3 g : this.GreenList) {
			Sketcher.Drawer.circle(g.x, g.y, g.z / 24);
		}
		Sketcher.setColor(Color.BLUE);
		for (Vector3 b : this.BlueList) {
			Sketcher.Drawer.circle(b.x, b.y, b.z / 24);
		}
	}

	public void draw(Camera c) {

		Sketcher.setColor(Color.RED);
		for (Vector3 r : this.RedList) {
			Vector3 R = c.project(r.cpy());
			Sketcher.Drawer.circle(R.x, R.y, 2);

			Vector3 next;
			Vector3 last;
			int n = 0;
			int s = RedList.size() - 1;

			for (int i = 0; i < s + 1; i++) {

				int f = s + 1;
				n = i + 1;
				if (n >= f)
					n = (n % f);

				last = RedList.get(i);
				next = RedList.get(n);

				boolean Fn1 = c.frustum.planes[0].testPoint(last) == Plane.PlaneSide.Front;
				boolean Sn1 = c.frustum.sphereInFrustum(last, 16 * 3);
				boolean Fn2 = c.frustum.planes[0].testPoint(next) == Plane.PlaneSide.Front;
				boolean Sn2 = c.frustum.sphereInFrustum(next, 16 * 3);
				if (Fn1 && Sn1 && Fn2 && Sn2) {
					Vector3 N = c.project(next.cpy());
					Vector3 L = c.project(last.cpy());
					Sketcher.setColor(1, 0, 0, 0.75f);
					Sketcher.Drawer.line(L.x, L.y, N.x, N.y);
				}

			}
		}

		Sketcher.setColor(Color.GREEN);
		for (Vector3 g : this.GreenList) {
			Vector3 G = c.project(g.cpy());
			Sketcher.Drawer.circle(G.x, G.y, 2);
		}

		Sketcher.setColor(Color.BLUE);
		for (Vector3 b : this.BlueList) {
			Vector3 B = c.project(b.cpy());
			Sketcher.Drawer.circle(B.x, B.y, 2);
		}
	}
}
