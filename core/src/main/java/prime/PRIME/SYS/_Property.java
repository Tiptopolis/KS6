package prime.PRIME.SYS;

import static prime.PRIME.uAppUtils.Log;

import java.util.ArrayList;
import java.util.Map.Entry;

import com.badlogic.gdx.utils.Null;

import prime.PRIME.N_M._N;
import prime.PRIME.N_M._N.Node;
import prime.PRIME.N_M._N.Type;

public class _Property<T> extends Node<T> {
	public static Type Property = new Type("Property", _Property.class);
	public static Type PropertyType = new Type("Property", _Property.class, Type.class);

	public Node header;
	public Type Node;

	public boolean refresh = true;
	public boolean serialized = false;

	public _Property() {
		this.Label = "aProperty";
		this.Of = Property;
		this.Data = (T) PropertyType;
		this.Node = Property;
	}

	public _Property(String name) {
		this.Label = name;
		this.Of = Property;
		this.Data = (T) PropertyType;
		this.Node = Property;
	}

	public _Property(String name, T data) {
		this.Label = name;
		this.Of = new Type(data.getClass().getSimpleName(), data.getClass());
		this.Data = data;
		this.Node = Property;
	}

	public _Property(String label, Type data) {
		this.Label = label;
		// this.Of = _VTS.getA(data.Type.Reference);
		this.Data = (T) data;
		// this.Node=data;
		this.Node = Property;
	}

	public _Property(_Property<T> p) {
		this.Label = p.Label;
		this.Of = p.Of;
		this.Data = p.Data;
		this.Node = p.Node;
	}

	@Override
	public <O extends Object, aProperty> aProperty set(O in) {
		// this.Data = (T) in;

		// this.msg("Listener", "Update");
		this.message(this, "Update", false, this.Data);
		return (aProperty) super.set(in);
	}

	@Override
	public boolean message(_N source, String message, boolean callback, @Null Object data) {
		String c = "";
		String d = "";
		if (data != null) {

			c = "<" + data.getClass().getSimpleName() + ">:[";
			d = data.toString() + "]";

			if (this.isOf(data))
				this.set(data);
		}

		// Log(" =<<" + this.Name() + " " + data);

		if (callback) {
			source.message(this, "CALLBACK_FOR:" + message, false, this.get());
		}

		return true;
	}

	public _Property<T> serialize() {
		this.serialized = true;
		return this;
	}

	public _Property cpy() {
		return new _Property(this);
	}

	public String headString() {
		String aCap = "";
		String bCap = "";
		if (this.Data instanceof Type) {
			aCap = "(";
			bCap = ")";
		}
		return this.Name() + ":" + aCap + this.get() + bCap;
	}

	public String tailString() {
		return this.Class().getSimpleName() + this.Node + ":" + this.Type();
	}

	@Override
	public String toString() {

		return "[" + this.headString() + "]" + "[" + this.tailString() + "]";
	}



	@Override
	public String toLog() {
		String s = this.toString() + " {::}" + "\n";
		s += " "+this.Properties + "\n";
		s += " "+this.Connections + "\n";

		return s;
	}

	public String megaLog() {
		String log = this.toString() + "\n";
		log += "NAME: " + this.Label + "\n";
		log += "OF:" + this.Of + "\n";
		log += "DATA: " + this.Data + "\n";
		log += "CLASS: " + this.Class() + "\n";
		log += "TYPE: " + this.Type() + "\n";
		log += "-PROPERTIES:" + this.Properties.size + "\n";
		log += this.Properties + "\n";
		log += "-CONNECTIONS:" + this.Connections.size() + "\n";
		log += this.Connections + "\n";
		return log;
	}
}
