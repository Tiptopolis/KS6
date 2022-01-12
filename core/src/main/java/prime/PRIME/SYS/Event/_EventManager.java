package prime.PRIME.SYS.Event;

import static prime.PRIME.uAppUtils.*;
import static prime.PRIME.uSketcher.*;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Array;

import prime.PRIME.C_O.Prototype.MultiMap;
import prime.PRIME.N_M._N.Node;
import prime.PRIME.N_M._N.Type;
import prime.PRIME.SYS._Struct;


public class _EventManager extends _Struct implements InputProcessor, iSignalProcessor {

	public _EventManager(String name) {
		super(name);
		this.Label = name;
		this.Of = new Type(false, this.getClass().getSimpleName(), _EventManager.class);
		this.Data = _EventManager.class;
		this.Properties = new Array<Node>();
		this.Connections = new MultiMap<String, Node>();
		this.Connections.add("Self", this);
	}

	public void update(char opCode)
	{
		
	}
	
	public void update(float deltaTime) {

	}

	public void update(int deltaTime) {

	}
	
	@Override
	public String toString() {
		return "[" + this.Name() + this.hashString() + ":" + this.Type() + "]|<" + this.Class().getSimpleName() + ">:"
				+ this.get() + "]";
	}

	@Override
	public String toLog() {
		String log = this.toString() + "\n";
		log += ":PROPERTIES:\n";
		for (int i = 0; i < this.Properties.size; i++) {
			Node N = this.Properties.get(i);
			log += i + "]:" + N + "\n";
		}
		log += "--------------\n";
		return log;
	}

	////
	private void BREAK1() {

	}

	public static enum StdInputType {

		Touch("t"), Scroll("s"), Cursor("x"), Command("c"), Key("k");

		public final String get;

		private StdInputType(String symbol) {
			this.get = symbol;
		}
	}

	public static enum StdInputEvents {
		TouchDown(InputEvent.Type.touchDown, StdInputType.Touch, "->"),
		TouchUp(InputEvent.Type.touchUp, StdInputType.Touch, "<-"),
		TouchDragged(InputEvent.Type.touchDragged, StdInputType.Touch, "<_>"),
		KeyDown(InputEvent.Type.keyDown, StdInputType.Key, "->"), KeyUp(InputEvent.Type.keyUp, StdInputType.Key, "<-"),
		KeyTyped(InputEvent.Type.keyTyped, StdInputType.Key, "<->"),
		Scrolled(InputEvent.Type.scrolled, StdInputType.Scroll, "<+>"),
		CursorMoved(InputEvent.Type.mouseMoved, StdInputType.Cursor, "<->");

		public final InputEvent.Type I;
		public final StdInputType T;
		public final String Symbol;

		private StdInputEvents(InputEvent.Type I, StdInputType T, String symbol) {
			this.T = T;
			this.Symbol = symbol;
			this.I = I;
		}

		@Override
		public String toString() {
			return " <[" + this.T.get + "|" + this.Symbol + "]> ";
		}

	}
	
	@Override
	public boolean recieveSignal(iSignal signal) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean transmitSignal(iSignal signal) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		//Log(">[" + keycode + "]<");
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		//Log("<[" + keycode + "]>");
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}


	
	
}
