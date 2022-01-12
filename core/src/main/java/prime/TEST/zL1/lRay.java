package prime.TEST.zL1;

import static prime.PRIME.uAppUtils.*;
import static prime.PRIME.uSketcher.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;

import prime.PRIME.C_O.Geom;
import prime.PRIME.C_O.Maths;
import prime.PRIME.C_O.VectorUtils;
import prime.PRIME.C_O.Prototype.Transform;
import prime.PRIME.C_O.Prototype.iTransformController;
import prime.PRIME.SYS._Environment;
import prime.PRIME.SYS.ECS.aEntity;

public class lRay extends aEntity implements iTransformController {

	public Transform transform;

	public Array<Vector3> pts;
	Array<Vector3> mod1;

	public lRay(_Environment e, Vector3 at, Vector3 dir, float len) {
		super("Ray", e);
		this.transform = new Transform();
		Vector3 gradiant = VectorUtils.dir(at.cpy(), at.cpy().add(dir.cpy())).nor();
		this.transform.setLocalNormal(gradiant.crs(0, 0, 1).nor().crs(0, -1, 0).nor());
		this.cast(at, dir, new Vector3(len, len, len), this.transform.getLocalNormal());
	}

	public lRay(_Environment e, Vector3 at, Vector3 dir, Vector3 up, float len) {
		super("Ray", e);
		Vector3 gradiant = VectorUtils.dir(at.cpy(), at.cpy().add(dir.cpy())).nor();
		this.transform = new Transform();
		this.transform.setLocalNormal(up);
		this.cast(at, dir, new Vector3(len, len, len), this.transform.getLocalNormal());
	}

	public lRay(_Environment e, Vector3 at, Vector3 dir) {
		super("Ray", e);
		Vector3 gradiant = VectorUtils.dir(at.cpy(), at.cpy().add(dir.cpy())).nor();
		this.transform = new Transform();
		this.transform.setLocalNormal(gradiant.crs(0, 0, 1).nor().crs(0, -1, 0).nor());
		this.cast(at, dir, e.getUnit(), this.transform.getLocalNormal());
	}

	public lRay(_Environment e, Ray r) {
		super("Ray", e);
		Vector3 gradiant = VectorUtils.dir(r.origin.cpy(), r.origin.cpy().add(r.direction.cpy())).nor();
		this.transform = new Transform();
		this.transform.setLocalNormal(gradiant.crs(0, 0, 1).nor().crs(0, -1, 0).nor());
		this.cast(r.origin, r.direction, e.getUnit(), this.transform.getLocalNormal());
	}

	public void cast(Vector3 at, Vector3 dir, Vector3 len, Vector3 up) {
		this.transform = new Transform();
		this.transform.setLocalNormal(up);
		if (pts == null)
			pts = new Array<Vector3>(true, 0, Vector3.class);
		if (mod1 == null)
			mod1 = new Array<Vector3>(true, 0, Vector3.class);
		Vector3 to = at.cpy().add(dir.cpy().scl(len));

		this.clear();
		if (!this.Environment.Members.contains(this, true))
			this.Environment.Members.add(this);
		this.position(at);
		this.rotation(VectorUtils.upcast(dir));
		this.scale(len, true);

		// this.pts = Geom.interpolatePoints(at, to,(int)(len.len())); //does not
		// demonstrate 0-anchors
		// this.pts = Geom.interpolatePoints(at, to, 0.001f, false);//discontinuous,
		// intervals demonstrate non-overlapping radials
		// this.pts = Geom.interpolatePoints(at, to, 10, true);//continuous, intervals
		// demonstrate overlapping radials
		// this.pts = Geom.interpolatePoints(at, to);

		// this.pts = Geom.interpolatePoints(at, to, 0.0001f, false);
		this.pts = Geom.interpolatePoints(at, to, 0.005f, false);
		this.mod1 = new Array(true, pts.size, Vector3.class);
	}

	public void clear() {
		this.pts.clear();
		this.mod1.clear();

	}

	@Override
	public Transform getTransform() {
		return this.transform;
	}

	public void render() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		Vector3 unit = this.Environment.getUnit();
		float unitLen = unit.len() / 3;

		Vector3 to = this.position().cpy().add(this.direction().cpy().scl(this.scale().cpy()));

		if (this.pts != null && !this.pts.isEmpty()) {
			for (int i = 0; i < this.pts.size; i++) {

				int n = (i + 1) % this.pts.size;
				Vector3 at = this.pts.get(i);

				Vector3 mod = new Vector3(1, 1, 1);
				Vector3 modN = mod.cpy();
				Vector3 next = this.pts.get(n).cpy();
				Vector3 gradiant = VectorUtils.dir(at.cpy(), next.cpy()).nor();

				// Vector3 localUp = this.getTransform().getLocalNormal();// globalUp
				Vector3 localUp = new Vector3(0, 0, 1);
				Vector3 localRight = localUp.cpy().crs(gradiant.cpy().scl(-1)).nor();
				localUp = localRight.cpy().crs(gradiant.cpy());// localUp

				Sketcher.setColor(Color.BLACK);
				Sketcher.Drawer.filledCircle(at.x, at.y, 2);

				Sketcher.setColor(Color.RED);
				mod.set(localRight.cpy().scl((float) Math.sin(i))).scl(32);
				Sketcher.Drawer.filledCircle(at.x + mod.x, at.y + mod.y, 1);
				Sketcher.setColor(Color.GREEN);
				mod.set(localRight.cpy().scl((float) Math.cos(i))).scl(32);
				Sketcher.Drawer.filledCircle(at.x + mod.x, at.y + mod.y, 1);
				Sketcher.setColor(Color.BLUE);
				mod.set(localRight.cpy().scl((float) Math.tan(i))).scl(32);
				Sketcher.Drawer.filledCircle(at.x + mod.x, at.y + mod.y, 1);

				Color Z = new Color();
				Color Q = new Color();
				if (n != 0) {
					// R
					Q.set(1, 0, 0, 0.5f);
					Z.set(1, 1, 1, 0.5f);

					mod.set(localRight.cpy().scl((float) Math.sin(i))).scl(32);
					modN.set(localRight.cpy().scl((float) Math.sin(n))).scl(32);
					if (Maths.isEven(i))
						Sketcher.Drawer.line(at.x + mod.x, at.y + mod.y, next.x + modN.x, next.y + modN.y, Z, Q);
					else
						Sketcher.Drawer.line(at.x + mod.x, at.y + mod.y, next.x + modN.x, next.y + modN.y, Q, Z);

					// B
					Q.set(0, 0, 1, 0.5f);
					Z.set(1, 1, 1, 0.5f);

					mod.set(localRight.cpy().scl((float) Math.tan(i))).scl(32);
					modN.set(localRight.cpy().scl((float) Math.tan(n))).scl(32);
					if (Maths.isEven(i))
						Sketcher.Drawer.line(at.x + mod.x, at.y + mod.y, next.x + modN.x, next.y + modN.y, Z, Q);
					else
						Sketcher.Drawer.line(at.x + mod.x, at.y + mod.y, next.x + modN.x, next.y + modN.y, Q, Z);

					// G
					Q.set(0, 1, 0, 0.5f);
					Z.set(1, 1, 1, 0.5f);

					mod.set(localRight.cpy().scl((float) Math.cos(i))).scl(32);
					modN.set(localRight.cpy().scl((float) Math.cos(n))).scl(32);
					if (Maths.isEven(i))
						Sketcher.Drawer.line(at.x + mod.x, at.y + mod.y, next.x + modN.x, next.y + modN.y, Z, Q);
					else
						Sketcher.Drawer.line(at.x + mod.x, at.y + mod.y, next.x + modN.x, next.y + modN.y, Q, Z);

				}
			}
		}

	}

	public void render(Camera eye) {
		float deltaTime = Gdx.graphics.getDeltaTime();
		Vector3 unit = this.Environment.getUnit();
		float unitLen = unit.len() / 3;
		Vector3 reference = eye.position;

		Vector3 to = this.position().cpy().add(this.direction().cpy().scl(this.scale().cpy()));

		if (this.pts != null && !this.pts.isEmpty()) {
			boolean oF = eye.frustum.planes[0].testPoint(this.position()) == Plane.PlaneSide.Front
					|| eye.frustum.planes[0].testPoint(this.position()) == Plane.PlaneSide.OnPlane;
			boolean oS = (eye.frustum.sphereInFrustum(this.position(), unit.len() * 3));
			boolean eF = eye.frustum.planes[0].testPoint(to) == Plane.PlaneSide.Front
					|| eye.frustum.planes[0].testPoint(to) == Plane.PlaneSide.OnPlane;
			boolean eS = (eye.frustum.sphereInFrustum(to, unit.len() * 3));

			Vector3 prjOrig = eye.project(this.position().cpy());
			Vector3 prjEnd = eye.project(to.cpy());

			if (oF && oS && eF && eS) {
				Sketcher.setColor(1, 0, 1, 0.5f);
				Sketcher.Drawer.line(prjOrig.x, prjOrig.y, prjEnd.x, prjEnd.y, 1);
			}

			for (int i = 0; i < this.pts.size; i++) {
				int n = (i + 1) % this.pts.size;

				Vector3 v = this.pts.get(i);
				Vector3 next = this.pts.get(n).cpy();
				Vector3 gradiant = VectorUtils.dir(v.cpy(), next.cpy()).nor();

				Vector3 localUp = this.getTransform().getLocalNormal();// globalUp
				Vector3 localRight = localUp.cpy().crs(gradiant.cpy().scl(-1)).nor();
				localUp = localRight.cpy().crs(gradiant.cpy());// localUp

				float z = (unitLen / reference.cpy().sub(v.cpy()).len());
				z = (z * (unit.len() / 3)) / 2;
				z = z * (unit.len() / 3);

				float q = 1 / z;

				Color Z = new Color(1, 1, 1, z);
				Color Q = new Color(1, q, 1, 1 - q);

				Vector3 modI = new Vector3((float) MathUtils.sin(i), (float) MathUtils.sin(i),
						(float) MathUtils.sin(i));
				Vector3 modN = new Vector3((float) MathUtils.sin(n), (float) MathUtils.sin(n),
						(float) MathUtils.sin(n));

				//// .nor() produces digital signals
				Vector3 I = localUp.cpy().scl(modI);
				Vector3 N = localUp.cpy().scl(modN);
				Vector3 I1 = localUp.cpy().scl(modI).nor();
				Vector3 N1 = localUp.cpy().scl(modN).nor();

				modI = new Vector3((float) MathUtils.cos(i), (float) MathUtils.cos(i), (float) MathUtils.cos(i));
				modN = new Vector3((float) MathUtils.cos(n), (float) MathUtils.cos(n), (float) MathUtils.cos(n));

				Vector3 I2 = localRight.cpy().scl(modI);
				Vector3 N2 = localRight.cpy().scl(modN);
				Vector3 I3 = localRight.cpy().scl(modI).nor();
				Vector3 N3 = localRight.cpy().scl(modN).nor();

				modI = new Vector3((float) Math.tan(i), (float) Math.tan(i), (float) Math.tan(i));
				modN = new Vector3((float) Math.tan(n), (float) Math.tan(n), (float) Math.tan(n));

				Vector3 I4 = localUp.cpy().scl(modI);
				Vector3 N4 = localUp.cpy().scl(modN);
				Vector3 I5 = (localRight.cpy().add(localUp.cpy())).scl(modI).nor();
				Vector3 N5 = (localRight.cpy().add(localUp.cpy())).scl(modN).nor();

				Vector3 prj_0 = eye.project(v.cpy());// base-line

				Vector3 prjA1 = eye.project(v.cpy().add(I));
				Vector3 prjB1 = eye.project(next.cpy().add(N));
				Vector3 prjA2 = eye.project(v.cpy().add(I1));
				Vector3 prjB2 = eye.project(next.cpy().add(N1));

				Vector3 prjA3 = eye.project(v.cpy().add(I2));
				Vector3 prjB3 = eye.project(next.cpy().add(N2));
				Vector3 prjA4 = eye.project(v.cpy().add(I3));
				Vector3 prjB4 = eye.project(next.cpy().add(N3));

				Vector3 prjA5 = eye.project(v.cpy().add(I4));
				Vector3 prjB5 = eye.project(next.cpy().add(N4));
				Vector3 prjA6 = eye.project(v.cpy().add(I5));
				Vector3 prjB6 = eye.project(next.cpy().add(N5));

				this.mod1.add(v.cpy().add(I1));

				boolean base = true;
				boolean red = true;
				boolean teal = true;
				boolean green = true;
				boolean magenta = true;
				boolean blue = true;
				boolean yellow = true;
				if (n != 0) {
					boolean F1 = eye.frustum.planes[0].testPoint(v) == Plane.PlaneSide.Front;
					boolean S1 = (eye.frustum.sphereInFrustum(v, unit.len()));
					boolean F2 = eye.frustum.planes[0].testPoint(next) == Plane.PlaneSide.Front;
					boolean S2 = (eye.frustum.sphereInFrustum(next, unit.len()));

					if (F1 && S1 && F2 && S2) {

						// BaseLine
						Sketcher.setLineWidth(q * 100);
						Sketcher.setLineWidth((1 - q) * 2);
						Z = new Color(1, 1, 1, z);
						Q = new Color(1, q, 1, 1 - q);

						if (base) {
							Sketcher.setColor(q, q, q, 1 - q);
							Sketcher.Drawer.circle(prj_0.x, prj_0.y, z / 128);
							Sketcher.Drawer.filledCircle(prj_0.x, prj_0.y, z * q);
						}

						// SinRED
						if (red) {
							Q = new Color(1 - q, 0, 0, 1 - q);
							if (Maths.isEven(i))
								Sketcher.Drawer.line(prjA1.x, prjA1.y, prjB1.x, prjB1.y, Q, Z);
							else
								Sketcher.Drawer.line(prjA1.x, prjA1.y, prjB1.x, prjB1.y, Z, Q);

							Sketcher.setColor(Q);
							Sketcher.Drawer.circle(prjA1.x, prjA1.y, z / 64);
							Sketcher.Drawer.filledCircle(prjA1.x, prjA1.y, z * q);
						}
						// SinNorTEAL
						if (teal) {

							Q = new Color(q, 1 - q, 1, 1 - q);
							if (Maths.isEven(i))
								Sketcher.Drawer.line(prjA2.x, prjA2.y, prjB2.x, prjB2.y, Q, Z);
							else
								Sketcher.Drawer.line(prjA2.x, prjA2.y, prjB2.x, prjB2.y, Z, Q);

							Sketcher.setColor(Q);
							Sketcher.Drawer.circle(prjA2.x, prjA2.y, z / 64);
							Sketcher.Drawer.filledCircle(prjA2.x, prjA2.y, z * q);
						}

						// CosGREEN
						if (green) {
							Q = new Color(q, 1 - q, 0, 1 - q);
							if (Maths.isEven(i))
								Sketcher.Drawer.line(prjA3.x, prjA3.y, prjB3.x, prjB3.y, Q, Z);
							else
								Sketcher.Drawer.line(prjA3.x, prjA3.y, prjB3.x, prjB3.y, Z, Q);

							Sketcher.setColor(Q);
							Sketcher.Drawer.circle(prjA3.x, prjA3.y, z / 64);
							Sketcher.Drawer.filledCircle(prjA3.x, prjA3.y, z * q);
						}
						// CosNorMAGENTA
						if (magenta) {

							Q = new Color(1 - q, q, 1 - q, 1 - q);
							if (Maths.isEven(i))
								Sketcher.Drawer.line(prjA4.x, prjA4.y, prjB4.x, prjB4.y, Q, Z);
							else
								Sketcher.Drawer.line(prjA4.x, prjA4.y, prjB4.x, prjB4.y, Z, Q);

							Sketcher.setColor(Q);
							Sketcher.Drawer.circle(prjA4.x, prjA4.y, z / 64);
							Sketcher.Drawer.filledCircle(prjA4.x, prjA4.y, z * q);
						}

						// TanBLUE
						if (blue) {
							Q = new Color(q, q, 1 - q, 1 - q);
							if (Maths.isEven(i))
								Sketcher.Drawer.line(prjA5.x, prjA5.y, prjB5.x, prjB5.y, Q, Z);
							else
								Sketcher.Drawer.line(prjA5.x, prjA5.y, prjB5.x, prjB5.y, Z, Q);

							Sketcher.setColor(Q);
							Sketcher.Drawer.circle(prjA5.x, prjA5.y, z / 64);
							Sketcher.Drawer.filledCircle(prjA5.x, prjA5.y, z * q);
						}

						// TanNorYELLOW
						if (yellow) {

							Q = new Color(1 - q, 1 - q, q, 1 - q);
							if (Maths.isEven(i))
								Sketcher.Drawer.line(prjA6.x, prjA6.y, prjB6.x, prjB6.y, Q, Z);
							else
								Sketcher.Drawer.line(prjA6.x, prjA6.y, prjB6.x, prjB6.y, Z, Q);

							Sketcher.setColor(Q);
							Sketcher.Drawer.circle(prjA6.x, prjA6.y, z / 64);
							Sketcher.Drawer.filledCircle(prjA6.x, prjA6.y, z * q);
						}

					}
					Sketcher.setLineWidth(1);
				}
			}
			// this.analyze(this.pts, this.mod1);
		}
	}

}
