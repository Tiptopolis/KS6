package prime.PRIME.SYS;

import com.badlogic.gdx.InputProcessor;

import prime.PRIME.SYS.Event.iSignalProcessor;


public interface iApplet extends InputProcessor, iSignalProcessor {

	public void create();

	public void update(float deltaTime);

	public void update(int deltaTime);

	public void render();

	public void resize(int width, int height);

	public void dispose();

}
