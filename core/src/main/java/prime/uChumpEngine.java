package prime;

import static prime.PRIME.uAppUtils.*;
import static prime.PRIME.uSketcher.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import prime.METATRON.Metatron;
import prime.METATRON.iMonad;
import prime.METATRON.iSpace;
import prime.METATRON._CAMERA.OrthoController;
import prime.METATRON._SYS._VTS;
import prime.PRIME.uAppUtils;
import prime.PRIME.uSketcher;
import prime.PRIME.N_M._N;
import prime.PRIME.N_M._N.Type;
import prime.TEST.TestApp0;
import prime.TEST.TestApp1;
import prime.TEST.zL1.zL1_1;
import prime.TEST.zT1.zT1_1;
import prime.TEST.zT1.zT1_2;
import prime.TEST.zT2.zT2_2;
import prime.TEST.zT3.zT3_1;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class uChumpEngine extends ApplicationAdapter implements iMonad, iSpace {
	private Texture image;

	public static Metatron METATRON;
	public static Camera CAMERA;

	@Override
	public void create() {
		image = new Texture("badlogic.png");
		uSketcher s = new uSketcher();
		METATRON = Metatron.TheMetatron;
		this.CAMERA = Metatron.CAMERA.Camera.camera;

		// Type T = _VTS.getA("Type");
		// METATRON.launch(new TestApp1());
		//METATRON.launch(new TestApp1());
		//METATRON.launch(new zT1_1());
		//METATRON.launch(new zT1_2());
		
		//METATRON.launch(new zT2_2());
		//METATRON.launch(new zT3_1()); //transform & basic Mesh
		METATRON.launch(new zL1_1());

	}

	@Override
	public void render() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		uAppUtils.update(deltaTime);

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Sketcher.setProjectionMatrix(METATRON.CAMERA.getProjection());
		Sketcher.begin();
		Sketcher.getBatch().draw(image, 165, 180);
		Sketcher.end();

		// Log(_VTS.Global.toLog());
		METATRON.update(' ');
		METATRON.update(deltaTime);

	}

	@Override
	public void resize(int width, int height) {
		uAppUtils.resize();
		METATRON.CAMERA.resize(Width, Height);
		METATRON.CAMERA.update(0);
		if (METATRON.CURRENT != null)
			METATRON.CURRENT.resize(width, height);

	}

	@Override
	public void dispose() {
		image.dispose();
		METATRON.dispose();
		_N.Type.Every.clear();
		Sketcher.dispose();
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public Vector3 getSize() {
		return new Vector3(Width, Height, 1);
	}

	@Override
	public Vector3 getUnit() {
		return new Vector3(1, 1, 1);
	}
}