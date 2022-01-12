package prime.TEST.zT2;

import static prime.PRIME.uAppUtils.*;
import static prime.PRIME.uSketcher.*;

import java.util.ArrayList;

import static prime.METATRON.Metatron.*;
import static prime.PRIME.C_O.Maths.*;
import static prime.PRIME.C_O.VectorUtils.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime.METATRON.Metatron;
import prime.METATRON._CAMERA.uFpsAdapter;
import prime.METATRON._SYS._VTS;
import prime.PRIME.C_O.Geom;
import prime.PRIME.C_O.Maths;
import prime.PRIME.C_O.VectorUtils;
import prime.PRIME.C_O.Prototype.BoundShape;
import prime.PRIME.C_O.Prototype.Circ;
import prime.PRIME.C_O.Prototype.Line;
import prime.PRIME.C_O.Prototype.ShapeFactory;
import prime.PRIME.C_O.Prototype.Transform;
import prime.PRIME.C_O.Prototype.iTransformController;
import prime.PRIME.SYS.uApp;
import prime.PRIME.SYS.Geom.Mesh;
import prime.PRIME.SYS.Geom.Prototype.aLine;
import prime.PRIME.SYS.Geom.Prototype.aVertex;

public class zT2_2 extends uApp {

	public uFpsAdapter Explorer;
	float far = 0;

	Mesh T;
	Transform T2;
	Transform T3;

	Node N;
	Node N2;
	Node N3;

	_PlottyMcPlotface plotter;

	public zT2_2() {
		super("TEST_1");
	}

	@Override
	public void create() {
		super.create();
		this.Explorer = new uFpsAdapter(CAMERA);
		this.Explorer.init();
		this.Explorer.Camera.viewport = CAMERA.Camera.viewport;
		this.far = this.Explorer.perspective.camera.far * 2;
		plotter = new _PlottyMcPlotface();
		this.genMesh();
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		this.Explorer.perspective.camera.far = this.far * CAMERA.Camera.zoom;
		this.Explorer.update();
		this.updateMesh(deltaTime);
	}

	@Override
	public void render() {
		super.render();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Vector3 mAt = TheMetatron.CAMERA.Camera.camera.unproject(new Vector3(MouseX, MouseY, 0));

		Circ m = new Circ(mAt.x, mAt.y, 8);
		Circ o = new Circ(0, 0, 12);

		Sketcher.setLineWidth(1f);

		Sketcher.setProjectionMatrix(this.Explorer.getProjection());
		Sketcher.begin();
		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.circle(0, 0, 8);
		this.renderPrimaryTrns();
		this.renderTrnsVertex(T.transform);
		this.renderTrnsAxes(T.transform);
		this.renderTrnsAxes(T.transform);
		this.renderMesh(T);
		this.renderTrnsVertex(T2);
		this.renderTrnsAxes(T2);
		this.renderTrnsVertex(T3);
		this.renderTrnsAxes(T3);
		Sketcher.end();

		Sketcher.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Width, Height));
		Sketcher.begin();

		this.renderTrnsVertex(this.T.transform, this.Explorer.perspective.camera);
		this.renderTrnsAxes(T.transform, this.Explorer.perspective.camera);
		this.renderMesh(T, this.Explorer.perspective.camera);
		this.renderTrnsVertex(this.T2, this.Explorer.perspective.camera);
		this.renderTrnsAxes(T2, this.Explorer.perspective.camera);
		this.renderTrnsVertex(this.T3, this.Explorer.perspective.camera);
		this.renderTrnsAxes(T3, this.Explorer.perspective.camera);
		this.plotter.draw(this.Explorer.perspective.camera);
		// this.renderMesh(this.T);
		// this.renderMesh(this.T_Forward);
		// this.renderMesh(this.T_Forward_2);
		Sketcher.end();

		if (Gdx.input.isKeyPressed(Keys.TAB)) {
			Sketcher.setProjectionMatrix(CAMERA.getProjection());
			Sketcher.begin();
			Sketcher.setColor(Color.BLACK);
			this.renderPrimaryTrns();

			this.renderTrnsVertex(T.getTransform());
			this.renderTrnsAxes(T.getTransform());
			this.renderMesh(T);
			this.renderTrnsVertex(T2);
			this.renderTrnsAxes(T2);
			this.renderTrnsVertex(T3);
			this.renderTrnsAxes(T3);
			this.plotter.draw();
			this.renderMouseShapeCamera();

			Sketcher.end();
		}

		Sketcher.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Width, Height));
		Sketcher.begin();
		renderMouseShapeScreen();
		Sketcher.end();

		// Log(T.mesh.toLog());
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		// this.Explorer.resize(width, height);
		this.Explorer.update();
	}

	public void dispose() {
		super.dispose();
		T.dispose();
	}

	private void renderPrimaryTrns() {
		Vector3 unit = new Vector3(32, 32, 32);
		float unitLen = unit.len() / 3;
		Vector3 pos = this.Explorer.perspective.camera.position.cpy();
		Vector3 dir = this.Explorer.perspective.camera.direction.cpy();
		Vector3 up = this.Explorer.perspective.camera.up.cpy();

		Vector3 forward = pos.cpy().add(dir.cpy().scl(unit.cpy()));
		// Vector3 far = pos.cpy().add(dir.cpy().scl(this.perspective.far/unitLen));
		Vector3 far = pos.cpy().add(dir.cpy().scl(this.Explorer.perspective.camera.far));
		Vector3 right = pos.cpy().add(dir.cpy().rotate(up.cpy(), -90).scl(unit.cpy()));
		up = pos.cpy().add(up.cpy().scl(unit.cpy()));

		Sketcher.setColor(Color.YELLOW);
		Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(far.x, far.y));
		Sketcher.Drawer.circle(far.x, far.y, 2);

		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.circle(pos.x, pos.y, unit.z);
		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.filledCircle(pos.x, pos.y, 1);
		Sketcher.setColor(Color.RED);
		Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(forward.x, forward.y));
		Sketcher.Drawer.circle(forward.x, forward.y, 2);
		Sketcher.setColor(Color.BLUE);
		Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(right.x, right.y));
		Sketcher.Drawer.circle(right.x, right.y, 2);
		Sketcher.setColor(Color.GREEN);
		Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(up.x, up.y));
		Sketcher.Drawer.circle(up.x, up.y, 2);

	}

	private void renderMouseShapeCamera() {

		Sketcher.setLineWidth(1f - (CAMERA.Camera.zoom));

		Vector3 pos = new Vector3(MouseX, MouseY, 0);
		TheMetatron.CAMERA.Camera.camera.unproject(pos);
		Vector3 rot = new Vector3(0, 1, 0);
		Vector3 up = new Vector3(0, 0, 1);
		Vector3 scl = new Vector3(8, 8, 8);
		BoundShape S = ShapeFactory.bindShape(pos, rot, up, scl, 3);

		Vector3 next;
		Vector3 last;
		int n = 0;
		int s = S.vertices.size() - 1;

		for (int i = 0; i < s + 1; i++) {

			int f = s + 1;
			n = i + 1;
			if (n >= f)
				n = (n % f);

			last = S.vertices.get(i);
			next = S.vertices.get(n);
			// Vector3 N = Metatron.CAMERA.Camera.camera.project(next.cpy());
			// Vector3 L = Metatron.CAMERA.Camera.camera.project(last.cpy());
			Vector3 N = (next.cpy());
			Vector3 L = (last.cpy());
			Sketcher.setColor(Color.ORANGE);
			Sketcher.Drawer.line(L.x, L.y, N.x, N.y);

		}
		S.dispose();

		S = ShapeFactory.bindLine(pos.cpy(), pos.cpy().add(rot.cpy().scl(scl.cpy().scl(0.5f))));
		Vector3 from = S.get(0);
		Vector3 to = S.get(1);
		// Vector3 to = pos.cpy().add(rot.cpy().scl(scl.cpy()));
		Sketcher.setColor(Color.RED);
		Sketcher.Drawer.line(from.x, from.y, to.x, to.y);

		S = ShapeFactory.bindRadius(pos.cpy().add(rot.cpy().scl(scl.cpy())), rot.cpy().rotate(up, 90), scl.cpy(),
				Color.GRAY);
		from = S.get(0);
		to = S.get(1);
		Sketcher.setColor(Color.GRAY);
		Sketcher.Drawer.line(from.x, from.y, to.x, to.y);
		S.dispose();

		Sketcher.setLineWidth(1f);
	}

	private void renderMouseShapeScreen() {

		Vector3 pos = new Vector3(MouseX, Height - MouseY, 0);
		Vector3 center = new Vector3(Width / 2, Height / 2, 0);
		Vector3 rot = dir(pos.cpy(), center.cpy());
		Vector3 up = new Vector3(0, 0, 1);
		Vector3 scl = new Vector3(16, 16, 16);
		BoundShape S = ShapeFactory.bindShape(pos, rot, up, scl, 3);

		Vector3 next;
		Vector3 last;
		int n = 0;
		int s = S.vertices.size() - 1;

		for (int i = 0; i < s + 1; i++) {

			int f = s + 1;
			n = i + 1;
			if (n >= f)
				n = (n % f);

			last = S.vertices.get(i);
			next = S.vertices.get(n);

			Vector3 N = (next.cpy());
			Vector3 L = (last.cpy());
			Sketcher.setColor(Color.PINK);
			Sketcher.Drawer.line(L.x, L.y, N.x, N.y);

		}

		S.dispose();

		// Sketcher.setColor(Color.GRAY);
		// Sketcher.Drawer.line(pos.x, pos.y, pos.x + (rot.x * scl.x), pos.y + (rot.y *
		// scl.y));

		S = ShapeFactory.bindLine(pos.cpy(), pos.cpy().add(rot.cpy().scl(scl.cpy().scl(0.5f))));

		Vector3 from = S.get(0);
		Vector3 to = S.get(1);
		Sketcher.setColor(Color.TEAL);
		Sketcher.Drawer.line(from.x, from.y, to.x, to.y);
		S.clear();
		S.dispose();

		S = ShapeFactory.bindRadius(pos.cpy().add(rot.cpy().scl(scl.cpy())), rot.cpy().rotate(up, 90), scl.cpy(),
				Color.GRAY);
		from = S.get(0);
		to = S.get(1);
		Sketcher.setColor(Color.GRAY);
		Sketcher.Drawer.line(from.x, from.y, to.x, to.y);
		S.dispose();

	}

	private void renderTrnsVertex(iTransformController trns) {
		// Vector3 unit = Transform.SpatialBasis.GetScale();
		Vector3 unit = new Vector3(32, 32, 32);
		float unitLen = (unit.x + unit.y + unit.z) / 3;
		Vector3 pos = trns.position();
		Vector3 dir = downcast(trns.rotation(true));
		Vector3 scl = trns.scale();
		Vector3 up = trns.normal();

		Vector3 forward = pos.cpy().add(dir.cpy().scl(scl.cpy()));
		Vector3 far = pos.cpy().add(dir.cpy().scl(this.Explorer.perspective.camera.far));
		Vector3 right = pos.cpy().add(dir.cpy().rotate(up.cpy(), -90).scl(scl.cpy()));
		up = pos.cpy().add(up.cpy().scl(scl.cpy()));

		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.filledCircle(pos.x, pos.y, unitLen / 2);
		Sketcher.Drawer.circle(pos.x, pos.y, unitLen);
		Sketcher.Drawer.rectangle(pos.x, pos.y, unit.x, unit.y);

		Camera c = this.Explorer.perspective.camera;
		// c.project(pos);
		// c.project(forward);
		// c.project(right);
		// c.project(up);

		Vector3 reference = c.position.cpy();
		float z = (unitLen / reference.cpy().sub(pos.cpy()).len());

		z = (z * (unit.len() / 3)) / 2;
		z = z * (unit.len() / 2);

		Sketcher.setColor(Color.PINK);
		Sketcher.Drawer.circle(pos.x, pos.y, scl.len() / 4);
		Sketcher.setColor(Color.ORANGE);
		Sketcher.Drawer.circle(pos.x, pos.y, scl.len() / 2);

		BoundShape S = ShapeFactory.bindShape(trns, 4);
		// S.drawShape();
		Vector3 next;
		Vector3 last;
		int n = 0;
		int s = S.vertices.size() - 1;

		for (int i = 0; i < s + 1; i++) {

			int f = s + 1;
			n = i + 1;
			if (n >= f)
				n = (n % f);

			last = S.vertices.get(i);
			next = S.vertices.get(n);
			// Vector3 N = Metatron.CAMERA.Camera.camera.project(next.cpy());
			// Vector3 L = Metatron.CAMERA.Camera.camera.project(last.cpy());
			Vector3 N = (next.cpy());
			Vector3 L = (last.cpy());
			Sketcher.setColor(Color.GRAY);
			Sketcher.Drawer.line(L.x, L.y, N.x, N.y);

		}
		S.dispose();
	}

	private void renderTrnsAxes(iTransformController trns) {
		// Vector3 unit = Transform.SpatialBasis.GetScale();
		Vector3 unit = new Vector3(32, 32, 32);
		float unitLen = (unit.x + unit.y + unit.z) / 3;
		Vector3 pos = trns.position();
		Vector3 dir = downcast(trns.rotation(true));
		Vector3 scl = trns.scale();
		Vector3 up = trns.normal();

		Vector3 forward = pos.cpy().add(dir.cpy().scl(scl.cpy()));
		Vector3 far = pos.cpy().add(dir.cpy().scl(this.Explorer.perspective.camera.far));
		Vector3 right = pos.cpy().add(dir.cpy().rotate(up.cpy(), -90).scl(scl.cpy()));
		up = pos.cpy().add(up.cpy().scl(scl.cpy()));

		float i = TheMetatron.RealSecond.I.floatValue();
		// Log(i + " " + i % 1 + " : " + ((i % 1) >= 0.99f) );
		if ((i % 1) >= 0.963f) {
			// Log(i + " " + i % 1);
			this.plotter.plot(forward.cpy(), up, right);//
		}

		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.circle(pos.x, pos.y, unit.z);
		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.filledCircle(pos.x, pos.y, 1);
		// Sketcher.setColor(Color.RED);
		Sketcher.setColor(new Color(1, 0, 0, 0.5f));
		Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(forward.x, forward.y));
		Sketcher.Drawer.circle(forward.x, forward.y, 2);
		// Sketcher.setColor(Color.GREEN);
		Sketcher.setColor(new Color(0, 1, 0, 0.5f));
		Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(up.x, up.y));
		Sketcher.Drawer.circle(up.x, up.y, 2);
		// Sketcher.setColor(Color.BLUE);
		Sketcher.setColor(new Color(0, 0, 1, 0.5f));
		Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(right.x, right.y));
		Sketcher.Drawer.circle(right.x, right.y, 2);

	}

	private void renderTrnsVertex(iTransformController trns, Camera c) {
		// Vector3 unit = Transform.SpatialBasis.GetScale();
		Vector3 unit = new Vector3(32, 32, 32);
		// float unitLen = unit.len()/3;
		float unitLen = (unit.x + unit.y + unit.z) / 3;
		Vector3 pos = trns.position();
		Vector3 dir = downcast(trns.rotation());
		Vector3 scl = trns.scale();
		Vector3 up = trns.normal();

		Vector3 forward = pos.cpy().add(dir.cpy().scl(scl.cpy()));
		Vector3 far = pos.cpy().add(dir.cpy().scl(this.Explorer.perspective.camera.far));
		Vector3 right = pos.cpy().add(dir.cpy().rotate(up.cpy(), -90).scl(scl.cpy()));
		up = pos.cpy().add(up.cpy().scl(scl.cpy()));

		Vector3 reference = c.position.cpy();
		float z = (unitLen / reference.cpy().sub(pos.cpy()).len());
		z = (z * (unit.len() / 3)) / 2;
		z = z * (unit.len() / 2);

		boolean Fn = c.frustum.planes[0].testPoint(pos) == Plane.PlaneSide.Front;
		// boolean Sn = c.frustum.pointInFrustum(pos);
		boolean Sn = c.frustum.sphereInFrustum(pos, scl.cpy().len());
		if (z >= 1 && z < 2400 && Fn && Sn) {

			c.project(pos);
			c.project(forward);
			c.project(right);
			c.project(up);

			if (z == Float.POSITIVE_INFINITY || z == Float.NEGATIVE_INFINITY)
				z = 1;

			Vector3 d = VectorUtils.abs(dst(pos, new Vector3(Width / 2, Height / 2, 0)));
			Vector3 D = inverseLerp(new Vector3(0, 0, 0), new Vector3(Width, Height, 0), d);
			float fD = (z * D.x) / 2;

			// if visible lol, 2000 prevents a dumb SpriteBatch overload crash
			if (z >= 1 && z < 2400) {

				Sketcher.setColor(Color.BLACK);
				Sketcher.Drawer.filledCircle(pos.x, pos.y, (z / 2) + fD);
				Sketcher.setColor(new Color(0, 0, 0, 0.5f));
				Sketcher.Drawer.filledCircle(pos.x, pos.y, (z) + fD);

				Sketcher.setColor(Color.PINK);
				Sketcher.Drawer.circle(pos.x, pos.y, (z / 2) + fD);

				Sketcher.setColor(Color.ORANGE);
				Sketcher.Drawer.circle(pos.x, pos.y, (z) + fD);
			}

			BoundShape S = ShapeFactory.bindShape(trns, 4);
			Vector3 next;
			Vector3 last;
			int n = 0;
			int s = S.vertices.size() - 1;

			for (int i = 0; i < s + 1; i++) {

				int f = s + 1;
				n = i + 1;
				if (n >= f)
					n = (n % f);

				last = S.vertices.get(i);
				next = S.vertices.get(n);

				boolean Fn1 = c.frustum.planes[0].testPoint(last) == Plane.PlaneSide.Front;
				boolean Sn1 = c.frustum.sphereInFrustum(last, scl.cpy().len() * 3);
				boolean Fn2 = c.frustum.planes[0].testPoint(next) == Plane.PlaneSide.Front;
				boolean Sn2 = c.frustum.sphereInFrustum(next, scl.cpy().len() * 3);
				if (Fn1 && Sn1 && Fn2 && Sn2) {
					Vector3 N = c.project(next.cpy());
					Vector3 L = c.project(last.cpy());
					Sketcher.setColor(Color.GRAY);
					Sketcher.Drawer.line(L.x, L.y, N.x, N.y);
				}

			}
			S.dispose();
		}
	}

	private void renderTrnsAxes(iTransformController trns, Camera c) {
		// Vector3 unit = Transform.SpatialBasis.GetScale();
		Vector3 unit = new Vector3(32, 32, 32);
		// float unitLen = unit.len()/3;
		float unitLen = (unit.x + unit.y + unit.z) / 3;
		Vector3 pos = trns.position();
		Vector3 dir = downcast(trns.rotation(true));
		Vector3 scl = trns.scale();
		Vector3 up = trns.normal();

		Vector3 forward = pos.cpy().add(dir.cpy().scl(scl.cpy()));
		Vector3 far = pos.cpy().add(dir.cpy().scl(this.Explorer.perspective.camera.far));
		Vector3 right = pos.cpy().add(dir.cpy().rotate(up.cpy(), -90).scl(scl.cpy()));
		up = pos.cpy().add(up.cpy().scl(scl.cpy()));

		Vector3 reference = c.position.cpy();
		float z = (unitLen / reference.cpy().sub(pos.cpy()).len());
		z = (z * (unit.len() / 3)) / 2;
		z = z * (unit.len() / 2);

		boolean Fn = c.frustum.planes[0].testPoint(pos) == Plane.PlaneSide.Front;
		// boolean Sn = c.frustum.pointInFrustum(pos);
		boolean Sn = c.frustum.sphereInFrustum(pos, scl.cpy().len());

		boolean fForward = c.frustum.planes[0].testPoint(forward) == Plane.PlaneSide.Front;
		boolean sForward = c.frustum.sphereInFrustum(forward, scl.cpy().len());
		boolean fUp = c.frustum.planes[0].testPoint(up) == Plane.PlaneSide.Front;
		boolean sUp = c.frustum.sphereInFrustum(up, scl.cpy().len());
		boolean fRight = c.frustum.planes[0].testPoint(right) == Plane.PlaneSide.Front;
		boolean sRight = c.frustum.sphereInFrustum(right, scl.cpy().len());

		if (z >= 1 && z < 2400 && Fn && Sn) {

			c.project(pos);
			c.project(forward);
			c.project(right);
			c.project(up);

			Sketcher.setColor(Color.BLACK);
			Sketcher.Drawer.circle(pos.x, pos.y, unitLen / z);
			Sketcher.setColor(Color.BLACK);
			Sketcher.Drawer.filledCircle(pos.x, pos.y, 1);
			if (fForward && sForward) {
				Sketcher.setColor(Color.RED);
				Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(forward.x, forward.y));
				Sketcher.Drawer.circle(forward.x, forward.y, 2);
			}
			if (fUp && sUp) {
				Sketcher.setColor(Color.GREEN);
				Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(up.x, up.y));
				Sketcher.Drawer.circle(up.x, up.y, 2);
			}
			if (fRight && sRight) {
				Sketcher.setColor(Color.BLUE);
				Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(right.x, right.y));
				Sketcher.Drawer.circle(right.x, right.y, 2);
			}

		}
	}

	public void genMesh() {
		// T = new XformController();
		// Transform.SpatialBasis.SetScale(new Vector3(32, 32, 32));

		// T = new Transform();
		// T.scale(new Vector3(32, 32, 32));
		T = new Mesh();
		T.getTransform().scale(new Vector3(32, 32, 32), true);
		T.getTransform().setLocalRotation(new Quaternion(0, 1, 0, 0));

		T2 = new Transform();
		T2.setParent(T.getTransform());
		T2.setLocalPosition(new Vector3(0, 1, 0));
		T2.setLocalRotation(new Quaternion(0, 1, 0, 0));
		T3 = new Transform();
		T3.setParent(T2);
		T3.setLocalPosition(new Vector3(0, 1, 0));
		T3.setLocalRotation(new Quaternion(0, 1, 0, 0));

		N = new Node(T);
		N2 = new Node(T2);
		N3 = new Node(T3);

		// T.rotate(new Vector3(1,0,0), 90);

		Log(Type.Every);
		Log(_VTS.Global.Every);
		Log();
		Log(N);
		Log(N.toLog());
		Log(N.megaLog());
		Log("->> " + N.getConnected("Self"));

		// Array<Vector3> pts = ShapeFactory.genPoly(T.transform, 6);
		Array<Vector3> pts = ShapeFactory.genPoly(T.position(), T.direction(), T.transform.getLocalNormal(),
				new Vector3(1, 1, 1), 6);
		for (int i = 0; i < pts.size; i++) {
			aVertex v = new aVertex(T.transform, pts.get(i), true);
			T.geometry.vertices.add(v);
		}
		pts.clear();

		//

		aVertex next;
		aVertex last;
		int n = 0;
		int s = T.geometry.vertices.size - 1;

		for (int i = 0; i < s + 1; i++) {

			int f = s + 1;
			n = i + 1;
			if (n >= f)
				n = (n % f);

			last = T.geometry.vertices.get(i);
			next = T.geometry.vertices.get(n);
			aLine nL = new aLine(last, next);
			T.geometry.lines.add(nL);
		}

	}

	

	public void updateMesh(float deltaTime) {
		float rotVelocity = -15 * deltaTime;

		float iTime = TheMetatron.Metranome.t.floatValue();
		// Log(iTime);

		T.getTransform().rotate(new Vector3(0, 1, 0), rotVelocity);

		// T2.translate(new Vector3(0, 0, (iTime * deltaTime)));
		T2.move(new Vector3((iTime), 0, 0));

		// Log("_______________________________");
		// Log(Transform.SpatialBasis.toString());
		// Log(" ");

		// Log("T");
		// Log(T.toString());

		// Log();
		// Log("T_2");
		// Log(T2.toString());
		// Log(div(dst(T.getPosition(), T2.getPosition()), T2.getScale()));

		// Log();
		// Log("T_3");
		// Log(T3.toString());
		// Log(div(dst(T2.getPosition(), T3.getPosition()), T3.getScale()));
		// Log();

	}

	private void renderMesh(Mesh M) {
		Sketcher.setColor(Color.YELLOW);

		// Log(M.geometry.toLog());
		for (aVertex v : M.geometry.vertices) {

			Sketcher.Drawer.filledCircle(v.get().x, v.get().y, 2);
		}
		Vector3 next;
		Vector3 last;
		int n = 0;
		int s = M.geometry.vertices.size - 1;

		for (int i = 0; i < s + 1; i++) {

			int f = s + 1;
			n = i + 1;
			if (n >= f)
				n = (n % f);

			last = M.geometry.vertices.get(i).get();
			next = M.geometry.vertices.get(n).get();
			// Vector3 N = Metatron.CAMERA.Camera.camera.project(next.cpy());
			// Vector3 L = Metatron.CAMERA.Camera.camera.project(last.cpy());
			Vector3 N = (next.cpy());
			Vector3 L = (last.cpy());
			Sketcher.setColor(Color.YELLOW);
			Sketcher.Drawer.line(L.x, L.y, N.x, N.y);

		}
	}

	private void renderMesh(Mesh M, Camera c) {
		Sketcher.setColor(Color.YELLOW);

		// Log(M.geometry.toLog());
		for (aVertex v : M.geometry.vertices) {

			Sketcher.Drawer.filledCircle(v.get().x, v.get().y, 2);
		}
		Vector3 next;
		Vector3 last;
		int n = 0;
		int s = M.geometry.vertices.size - 1;

		for (int i = 0; i < s + 1; i++) {

			int f = s + 1;
			n = i + 1;
			if (n >= f)
				n = (n % f);

			last = M.geometry.vertices.get(i).get();
			next = M.geometry.vertices.get(n).get();
			// Vector3 N = Metatron.CAMERA.Camera.camera.project(next.cpy());
			// Vector3 L = Metatron.CAMERA.Camera.camera.project(last.cpy());
			Vector3 N = (next.cpy());
			Vector3 L = (last.cpy());
			c.project(N);
			c.project(L);
			Sketcher.setColor(Color.YELLOW);
			Sketcher.Drawer.line(L.x, L.y, N.x, N.y);

		}
	}

}
