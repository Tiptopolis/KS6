package prime.TEST.zT1;

import static prime.PRIME.uAppUtils.*;
import static prime.PRIME.uSketcher.*;
import static prime.METATRON.Metatron.*;
import static prime.PRIME.C_O.Maths.*;
import static prime.PRIME.C_O.VectorUtils.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import prime.METATRON.Metatron;
import prime.METATRON._CAMERA.uFpsAdapter;
import prime.PRIME.C_O.Maths;
import prime.PRIME.C_O.Prototype.BoundShape;
import prime.PRIME.C_O.Prototype.Circ;
import prime.PRIME.C_O.Prototype.ShapeFactory;
import prime.PRIME.C_O.Prototype.Transform;

import prime.PRIME.SYS.uApp;

public class zT1_1 extends uApp {

	public uFpsAdapter Explorer;
	float far = 0;

	Transform T;
	Transform T2;
	Transform T3;

	public zT1_1() {
		super("TEST_1");
	}

	@Override
	public void create() {
		super.create();
		this.Explorer = new uFpsAdapter(CAMERA);
		this.Explorer.init();
		this.far = this.Explorer.perspective.camera.far * 2;
		this.genMesh();
	}

	@Override
	public void update(float deltaTime) {

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

		if (Gdx.input.isKeyPressed(Keys.TAB)) {
			Sketcher.setProjectionMatrix(CAMERA.getProjection());
			Sketcher.begin();
			Sketcher.setColor(Color.BLACK);
			this.renderPrimaryTrns();
			this.renderMouseShape();
			this.renderTrnsVertex(T);
			this.renderTrnsAxes(T);
			this.renderTrnsVertex(T2);
			this.renderTrnsAxes(T2);
			this.renderTrnsVertex(T3);
			this.renderTrnsAxes(T3);

			Sketcher.end();
		}

		Sketcher.setProjectionMatrix(this.Explorer.getProjection());
		Sketcher.begin();
		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.circle(0, 0, 8);
		this.renderPrimaryTrns();
		this.renderTrnsVertex(T);
		this.renderTrnsAxes(T);
		this.renderTrnsVertex(T2);
		this.renderTrnsAxes(T2);
		this.renderTrnsVertex(T3);
		this.renderTrnsAxes(T3);
		Sketcher.end();

		Sketcher.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Width, Height));
		Sketcher.begin();
		Sketcher.setLineWidth(1f);

		this.renderTrnsVertex(this.T, this.Explorer.perspective.camera);
		this.renderTrnsAxes(T, this.Explorer.perspective.camera);
		this.renderTrnsVertex(this.T2, this.Explorer.perspective.camera);
		this.renderTrnsAxes(T2, this.Explorer.perspective.camera);
		this.renderTrnsVertex(this.T3, this.Explorer.perspective.camera);
		this.renderTrnsAxes(T3, this.Explorer.perspective.camera);

		// this.renderMesh(this.T);
		// this.renderMesh(this.T_Forward);
		// this.renderMesh(this.T_Forward_2);
		Sketcher.end();

		// Log(T.mesh.toLog());
	}

	public void dispose() {
		super.dispose();
		// T.dispose();
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

	private void renderMouseShape() {

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
		S.clear();

		Vector3 center = new Vector3(Width / 2, Height / 2, 0);
		Vector3 dir = dir(new Vector3(MouseX,Height-MouseY,0), center.cpy());
		
		S = ShapeFactory.bindShape(pos, dir, up, scl, 3);

		
		n = 0;
		s = S.vertices.size() - 1;

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
			Sketcher.setColor(Color.PINK);
			Sketcher.Drawer.line(L.x, L.y, N.x, N.y);

		}
		S.clear();
		
	}

	private void renderTrnsVertex(Transform trns) {
		// Vector3 unit = Transform.SpatialBasis.GetScale();
		Vector3 unit = new Vector3(32, 32, 32);
		float unitLen = (unit.x + unit.y + unit.z) / 3;
		Vector3 pos = trns.getPosition();
		Vector3 dir = downcast(trns.getLocalRotation());
		Vector3 scl = trns.getScale();
		Vector3 up = trns.getLocalNormal();

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

		Log(z + "  <<");
		Sketcher.setColor(Color.GREEN);
		Sketcher.Drawer.circle(pos.x, pos.y, z);
		Sketcher.setColor(Color.BLUE);
		Sketcher.Drawer.circle(pos.x, pos.y, 16);

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
		S.clear();
	}

	private void renderTrnsAxes(Transform trns) {
		// Vector3 unit = Transform.SpatialBasis.GetScale();
		Vector3 unit = new Vector3(32, 32, 32);
		float unitLen = (unit.x + unit.y + unit.z) / 3;
		Vector3 pos = trns.getPosition();
		Vector3 dir = downcast(trns.getLocalRotation());
		Vector3 scl = trns.getScale();
		Vector3 up = trns.getLocalNormal();

		Vector3 forward = pos.cpy().add(dir.cpy().scl(scl.cpy()));
		Vector3 far = pos.cpy().add(dir.cpy().scl(this.Explorer.perspective.camera.far));
		Vector3 right = pos.cpy().add(dir.cpy().rotate(up.cpy(), -90).scl(scl.cpy()));
		up = pos.cpy().add(up.cpy().scl(scl.cpy()));

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

	private void renderTrnsVertex(Transform trns, Camera c) {
		// Vector3 unit = Transform.SpatialBasis.GetScale();
		Vector3 unit = new Vector3(32, 32, 32);
		// float unitLen = unit.len()/3;
		float unitLen = (unit.x + unit.y + unit.z) / 3;
		Vector3 pos = trns.getPosition();
		Vector3 dir = downcast(trns.getRotation());
		Vector3 scl = trns.getScale();
		Vector3 up = trns.getLocalNormal();

		Vector3 forward = pos.cpy().add(dir.cpy().scl(scl.cpy()));
		Vector3 far = pos.cpy().add(dir.cpy().scl(this.Explorer.perspective.camera.far));
		Vector3 right = pos.cpy().add(dir.cpy().rotate(up.cpy(), -90).scl(scl.cpy()));
		up = pos.cpy().add(up.cpy().scl(scl.cpy()));

		Vector3 reference = c.position.cpy();
		float z = (unitLen / reference.cpy().sub(pos.cpy()).len());
		z = (z * (unit.len() / 3)) / 2;
		z = z * (unit.len() / 2);

		c.project(pos);
		c.project(forward);
		c.project(right);
		c.project(up);

		Vector3 lX = dst(pos.cpy(), new Vector3(Width / 2, Height / 2, 0));
		Vector3 lW = Maths.abs(inverseLerp(new Vector3(0, 0, 0), new Vector3(Width / 2, Height / 2, 0), lX));
		// Log("> " + z);
		// Log("* " + pos.x);
		// Log("_ " + lC);
		if (lX.len() == Float.POSITIVE_INFINITY)
			lX.set(0, 0, 0);
		if (lW.len() == Float.POSITIVE_INFINITY)
			lW.set(0, 0, 0);
		Log(lX + " -> " + lX.len() + " -> " + lW);

		// if visible lol
		if (z / 3 <= unit.len()) {

			Sketcher.setColor(Color.BLACK);
			Sketcher.Drawer.filledCircle(pos.x, pos.y, z / 2);
			Sketcher.setColor(new Color(0, 0, 0, 0.5f));
			Sketcher.Drawer.filledCircle(pos.x, pos.y, z);

			Sketcher.setColor(Color.PINK);
			Sketcher.Drawer.circle(pos.x, pos.y, (z / 2) + ((z / 2) * ((lW.x + lW.y) / 2)) - 1);

			Sketcher.setColor(Color.ORANGE);
			Sketcher.Drawer.circle(pos.x, pos.y, (z) + (z * ((lW.x + lW.y) / 2)) - 1);
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
			Vector3 N = c.project(next.cpy());
			Vector3 L = c.project(last.cpy());
			Sketcher.setColor(Color.GRAY);
			Sketcher.Drawer.line(L.x, L.y, N.x, N.y);

		}
		S.clear();
	}

	private void renderTrnsAxes(Transform trns, Camera c) {
		// Vector3 unit = Transform.SpatialBasis.GetScale();
		Vector3 unit = new Vector3(32, 32, 32);
		// float unitLen = unit.len()/3;
		float unitLen = (unit.x + unit.y + unit.z) / 3;
		Vector3 pos = trns.getPosition();
		Vector3 dir = downcast(trns.getLocalRotation());
		Vector3 scl = trns.getScale();
		Vector3 up = trns.getLocalNormal();

		Vector3 forward = pos.cpy().add(dir.cpy().scl(scl.cpy()));
		Vector3 far = pos.cpy().add(dir.cpy().scl(this.Explorer.perspective.camera.far));
		Vector3 right = pos.cpy().add(dir.cpy().rotate(up.cpy(), -90).scl(scl.cpy()));
		up = pos.cpy().add(up.cpy().scl(scl.cpy()));

		Vector3 reference = c.position.cpy();
		float z = (unitLen / reference.cpy().sub(pos.cpy()).len());
		z = (z * (unit.len() / 3)) / 2;
		z = z * (unit.len() / 2);

		c.project(pos);
		c.project(forward);
		c.project(right);
		c.project(up);

		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.circle(pos.x, pos.y, unit.z);
		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.filledCircle(pos.x, pos.y, 1);
		Sketcher.setColor(Color.RED);
		Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(forward.x, forward.y));
		Sketcher.Drawer.circle(forward.x, forward.y, 2);
		Sketcher.setColor(Color.GREEN);
		Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(up.x, up.y));
		Sketcher.Drawer.circle(up.x, up.y, 2);
		Sketcher.setColor(Color.BLUE);
		Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(right.x, right.y));
		Sketcher.Drawer.circle(right.x, right.y, 2);

	}

	public void genMesh() {
		// T = new XformController();
		// Transform.SpatialBasis.SetScale(new Vector3(32, 32, 32));

		T = new Transform();
		T.setScale(new Vector3(32, 32, 32));
		// T.setRotation(new Quaternion(0,1,0,0));
		T2 = new Transform();
		T2.setParent(T);
		T2.setLocalPosition(new Vector3(0, 1, 0));
		T3 = new Transform();
		T3.setParent(T2);

		T3.setLocalPosition(new Vector3(1, 0, 0));
	}

	public void updateMesh(float deltaTime) {
		float rotVelocity = -15 * deltaTime;

		float iTime = TheMetatron.Metranome.t.floatValue();

		T.rotate(new Vector3(0, 1, 0), rotVelocity);
		Log("_______________________________");
		// Log(Transform.SpatialBasis.toString());
		Log("  ");

		Log("T");
		Log(T.toString());

		Log();
		Log("T_2");
		Log(T2.toString());
		Log(div(dst(T.getPosition(), T2.getPosition()), T2.getScale()));

		Log();
		Log("T_3");
		Log(T3.toString());
		Log(div(dst(T2.getPosition(), T3.getPosition()), T3.getScale()));
		Log();

	}

}
