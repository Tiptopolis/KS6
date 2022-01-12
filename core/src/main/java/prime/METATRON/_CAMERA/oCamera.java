package prime.METATRON._CAMERA;

import static prime.PRIME.uAppUtils.*;
import static prime.PRIME.uSketcher.*;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import prime.METATRON._CAMERA.A_I.aCamera;
import prime.METATRON._CAMERA.A_I.aCameraController;
import prime.PRIME.C_O.Geom;
import prime.PRIME.C_O.Prototype.Rect;





public class oCamera extends aCamera {
	// Basic Screen Camera
	public oCamera(aCameraController controller) {
		super(controller);
		this.camera = new OrthographicCamera();
		((OrthographicCamera) this.camera).setToOrtho(false);
		this.viewport = new ScreenViewport(this.camera);
		//this.viewport = new StretchViewport(Width, Height, this.camera);
		this.view = new Rect(this.camera.position.x, this.camera.position.y, this.camera.viewportWidth,
				this.camera.viewportHeight);
	}
	
	public oCamera(aCameraController controller, Camera camera) {
		super(controller);
		this.camera = camera;
		// this.viewport = new ScreenViewport(this.camera);
		this.viewport = new StretchViewport(Width, Height, this.camera);
		this.view = new Rect(this.camera.position.x, this.camera.position.y, this.camera.viewportWidth,
				this.camera.viewportHeight);
	}
	
	public oCamera(aCameraController controller, Viewport viewport) {
		super(controller);
		
		this.camera=viewport.getCamera();
		this.viewport = viewport;
		this.view = new Rect(this.camera.position.x, this.camera.position.y, this.camera.viewportWidth,
				this.camera.viewportHeight);
	}
	public oCamera(aCameraController controller, Camera camera, Viewport viewport) {
		super(controller);
		
		this.camera=camera;
		this.viewport = viewport;
		this.viewport.setCamera(camera);
		this.view = new Rect(this.camera.position.x, this.camera.position.y, this.camera.viewportWidth,
				this.camera.viewportHeight);
	}

	@Override
	public void update() {
		this.zoom = MathUtils.clamp(this.zoom, Geom.kEpsZoom, 224);

		if (this.zoom >= 223)
			this.zoom = Geom.kEpsZoom + Geom.kEpsilon;

		if (this.zoom <= (Geom.kEpsZoom + 1e-4F))
			this.zoom = 223;

		((OrthographicCamera) this.camera).zoom = this.zoom;
		((OrthographicCamera) this.camera).update();
		this.view.set(this.camera.position.x, this.camera.position.y, this.camera.viewportWidth,
				this.camera.viewportHeight);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		this.viewport = new ScreenViewport(this.camera);			
		this.view = new Rect(this.camera.position.x, this.camera.position.y, this.camera.viewportWidth,
				this.camera.viewportHeight);
	}

	@Override
	public float getZoom() {
		return ((OrthographicCamera) this.camera).zoom;
	}



}
