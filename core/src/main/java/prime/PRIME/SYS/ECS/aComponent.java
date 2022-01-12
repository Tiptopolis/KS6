package prime.PRIME.SYS.ECS;

import java.util.Map.Entry;

import com.badlogic.gdx.utils.Array;

import prime.PRIME.C_O.Prototype.MultiMap;
import prime.PRIME.N_M._N.Type;
import prime.PRIME.SYS._Property;

public class aComponent extends _Property<MultiMap<String, _Property>> {

	// bucket of properties

	public static Type Component = new Type("Component", aComponent.class);
	public static Type ComponentType = new Type("Component", aComponent.class, Type.class);

	public aEntity owner;

	public aComponent(String label) {
		super(label);
		this.Of = Component;
		this.Data = new MultiMap<String, _Property>();
		this.Node = Component;
	}

	public aComponent(aComponent cpy) {
		super(cpy.Label);
		this.Of = cpy.Of;
		this.Data = new MultiMap<String, _Property>();
		this.Node = cpy.Node;
	}

	public void assign(aEntity owner) {
		owner.Properties.add(this);
		this.owner = owner;
	}

	public aComponent copyFrom(aComponent from) {

		this.Properties.clear();
		this.Data.clear();
		for (Node N : from.Properties) {
			this.Properties.add(N.cpy());
		}
		for (Entry E : from.Data.data.entrySet()) {
			this.Data.add(E.getKey(), E.getValue());
		}
		return this;
	}

	public aComponent copyTo(aComponent to) {

		to.Properties.clear();
		to.Data.clear();
		for (Node N : this.Properties) {
			to.Properties.add(N.cpy());
		}
		for (Entry E : this.Data.data.entrySet()) {
			to.Data.add(E.getKey(), E.getValue());
		}

		return to;
	}

	public aComponent cpy() {
		aComponent cpy = new aComponent(this.Label);
		this.copyTo(cpy);
		return cpy;
	}

	@Override
	public String toString() {

		return "[" + this.Name() + ":" + this.Of + "]" + "[" + this.Class().getSimpleName() + ":" + this.Type() + "]";
	}

	@Override
	public String toLog() {
		String log = this.toString() + " {[::]}" + "\n";
		log += "-PROPERTIES:" + this.Properties.size + "\n";
		log += this.Properties + "\n";
		log += "-CONNECTIONS:" + this.Connections.size() + "\n";
		log += this.Connections + "\n";

		return log;
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
