package prime.METATRON;

import static prime.PRIME.uAppUtils.*;
import static prime.PRIME.uSketcher.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

import prime.uChumpEngine;
import prime.METATRON._CAMERA.OrthoController;
import prime.METATRON._SYS._VTS;
import prime.PRIME.C_O.Maths;
import prime.PRIME.C_O.Prototype.AtnmKey;
import prime.PRIME.SYS._Shell;
import prime.PRIME.SYS.iApplet;
import prime.PRIME.SYS.uApp;

public class Metatron extends _Shell implements iMonad {
	public final static Metatron TheMetatron;

	public static OrthoController CAMERA;
	public static MetatronConsole CONSOLE;
	public static _VTS GLOBAL = new _VTS();

	public InputMultiplexer Multiplexer;

	protected static MetatronBoot BOOT;
	public uApp CURRENT;

	public AtnmKey Module = new AtnmKey(); // PULSER

	public AtnmKey Metranome = new AtnmKey(); // SCANNER
	public AtnmKey DeltaTime = new AtnmKey(); // FREQUENCY
	public AtnmKey RealSecond = new AtnmKey(); // COUNTER

	public AtnmKey iTime = new AtnmKey(); // COUNTER

	static {
		TheMetatron = new Metatron();
		CAMERA = new OrthoController(TheMetatron);
		CAMERA.init();
		TheMetatron.Multiplexer.addProcessor(CAMERA);
		CONSOLE = new MetatronConsole();
		TheMetatron.connect("CONSOLE", new Node("CONSOLE", CONSOLE));
	}

	public Metatron() {
		super("METATRON");
		this.Multiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(this.Multiplexer);
		this.Multiplexer.addProcessor(this);

		this.VTS = new _VTS();
		this.boot();
	}

	public void update(Number deltaTime) {
		// Log("M_N");
		// This is where TimeKeys come in...?
		// reinvent TimeKey to be Module,Metranome,Float,Int time
		if (Integer.class.isAssignableFrom(deltaTime.getClass()))
			this.update(deltaTime.intValue());
		if (Float.class.isAssignableFrom(deltaTime.getClass()))
			this.update(deltaTime.floatValue());
	}

	@Override
	public void update(float deltaTime) {
		// Log("M_F");
		// Log(BOOT);
		// Log(" "+CURRENT);
		super.update(deltaTime);

		if (BOOT == null)
			this.boot();

		this.timeKeys(deltaTime);
		CAMERA.update(deltaTime);
		if (CURRENT != null) {
			CURRENT.update(deltaTime);
			CURRENT.render();
		}
	}

	@Override
	public void update(int deltaTime) {
		// Log("M_I");
		super.update(deltaTime);

		if (CURRENT != null && deltaTime >= 1)
			CURRENT.update(deltaTime);

		// this.timeKeys(deltaTime);

	}

	@Override
	public void update(char opCode) {
		super.update(opCode);
		if (CURRENT != null)
			CURRENT.update(opCode);

	}

	@Override
	public void dispose() {
		super.dispose();
		CONSOLE.terminate();
		this.VTS.dispose();
		GLOBAL.dispose();

	}

	///////////////
	private void boot() {
		BOOT = new MetatronBoot();
		this.launch(BOOT);
	}

	public void launch(uApp app) {
		if (CURRENT == null || !CURRENT.equals(app)) {
			if (CURRENT != null && !CURRENT.equals(app)) {
				Log("METATRON_SHELL.CLOSING: [" + CURRENT.getClass().getSimpleName() + "]...");
				CURRENT.pause();
				Multiplexer.removeProcessor(CURRENT);
				CURRENT.dispose();
			}
			Log("METATRON_SHELL.LAUNCHING: [" + app.getClass().getSimpleName() + "]...");
			// uApp bootApp = app;

			CURRENT = app;
			CURRENT.create();
			Multiplexer.addProcessor(CURRENT);
			CURRENT.enter();
			CURRENT.resize(Width, Height);
			CURRENT.resume();
		}
	}

	private void timeKeys(Number deltaTime) {
		DeltaTime.I = this.DeltaTime.I.byteValue() + deltaTime.floatValue();
		DeltaTime.i = 1;
		DeltaTime.t = deltaTime.floatValue();
		RealSecond.I = (this.RealSecond.I.floatValue() + deltaTime.floatValue()) % 360;
		//////
		Module.n = Byte.MIN_VALUE;
		Module.m = Byte.MAX_VALUE;
		Module.i = 1;

		Module.a = Module.a.byteValue() + this.Module.i.byteValue();
		Module.t = this.Module.I;
		Module.I = Maths.map(this.Module.a.floatValue(), this.Module.n.floatValue(), this.Module.m.floatValue(), 0f,
				1f);
		//////

		//////
		Metranome.n = Byte.MIN_VALUE;
		Metranome.m = Byte.MAX_VALUE;
		Metranome.i = 1;

		Metranome.a = Metranome.a.byteValue() + Metranome.i.byteValue();
		Metranome.t = Maths.map(Metranome.a.floatValue(), Metranome.n.floatValue(), Metranome.m.floatValue(), -1, 1);
		Metranome.I = Math.abs(Metranome.t.floatValue());
		//////

		iTime.I = ((RealSecond.I.floatValue() / (RealSecond.I.intValue() * DeltaTime.I.floatValue()))
				* DeltaTime.I.floatValue());
		if (iTime.I.floatValue() == Float.POSITIVE_INFINITY)
			iTime.I = 1f;
	}

	public static void mCamOn() {
		if (!CAMERA.isOn) {
			CAMERA.isOn = true;
			TheMetatron.Multiplexer.addProcessor(CAMERA);
		}
	}

	public static void mCamOff() {
		if (CAMERA.isOn) {
			CAMERA.isOn = false;
			TheMetatron.Multiplexer.removeProcessor(CAMERA);
		}
	}

	@Override
	public String toLog() {
		String log = "";
		log += this.toString() + "\n";
		log += "IntTime: " + this.iTime + "\n";
		log += "FltTime: " + this.DeltaTime + "\n";
		log += "ModTime: " + this.Module + "\n";
		log += "MetTime: " + this.Metranome + "\n";
		log += "CntTime: " + this.RealSecond + "\n";
		log += super.toLog() + "\n";

		return log;
	}
}
