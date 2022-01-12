package prime.TEST;

import static prime.METATRON.Metatron.TheMetatron;
import static prime.PRIME.uAppUtils.Log;
import static prime.PRIME.uAppUtils.MouseX;
import static prime.PRIME.uAppUtils.MouseY;
import static prime.PRIME.uSketcher.Sketcher;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

import prime.METATRON._SYS._VTS;
import prime.PRIME.C_O.Prototype.Circ;
import prime.PRIME.SYS.uApp;

public class BasicCircleTest extends uApp {

	public BasicCircleTest() {
		super("Planetarium_0.1");
	}

	@Override
	public void create() {
		super.create();

	}

	@Override
	public void update(float deltaTime) {
		Log();
		Log();
		Log();
		Log(_VTS.Global.toLog());
		Log();

	}

	@Override
	public void render() {
		super.render();

		Vector3 mAt = TheMetatron.CAMERA.Camera.camera.unproject(new Vector3(MouseX, MouseY, 0));
		Circ m = new Circ(mAt.x, mAt.y, 8);
		Circ o = new Circ(0, 0, 12);

		Sketcher.setProjectionMatrix(TheMetatron.CAMERA.getProjection());
		Sketcher.begin();
		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.filledCircle(0, 0, 1);
		Sketcher.setColor(Color.GRAY);
		Sketcher.Drawer.circle(0, 0, 12);

		Sketcher.setColor(Color.RED);
		if (m.overlaps(o))
			Sketcher.setColor(Color.GREEN);
		if (m.contains(o.origin))
			Sketcher.setColor(Color.BLUE);
		if (o.contains(m))
			Sketcher.setColor(Color.YELLOW);

		Sketcher.Drawer.circle(m.origin.x, m.origin.y, m.radius);

		Sketcher.end();
	}

}
