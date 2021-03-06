package prime.METATRON._SYS;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.utils.Array;

import prime.PRIME.N_M._N.Type;

public class _VTS {
	
public static _VTS Global;
public HashMap<String, Array<Type>> Every;

	
	
	static {
		Global = new _VTS();
	}

	public _VTS() {
		this.Every = new HashMap<String, Array<Type>>();
	}

	

	public static void put(Type t) {
		boolean exists = false;
		for (Entry<String, Array<Type>> E : Global.Every.entrySet()) {
			if (E.getKey().equals(t.Reference)) {
				exists = true;
				Array<Type> L = E.getValue();
				for (Type T : L) {
					boolean colide = false;

					if (T.Node.toString().contains(t.Node.toString())) {
						colide = true;
					} else
						L.add(t);
				}
			}
		}
		if (!exists) {
			Array<Type> l = new Array<Type>(false, 1, Type.class);
			l.add(t);
			Global.Every.put(t.Reference, l);
		}
	}

	public static Type getA(String name) {
		for (Type T : getAll(name)) {

			if (T.Node.toString().equals(name) || T.Node.toString().contains("<" + name + ">"))
				return T;
			if (T.Node.Name().contains(name))
				return T;
		}
		return new Type(false,name);
	}

	public static Type getA(String name, boolean tex) {
		if (tex) {
			for (Type T : getAll(name)) {

				if (T.Node.toString().equals(name) || T.Node.toString().contains("<" + name + ">"))
					return T;
				if (T.Node.Name().contains(name))
					return T;
			}
			return new Type(name);
		} else
			return Type.Type;
	}

	public static Array<Type> getAll(String word) {
		// list of Types starting with -word-
		if (Global.Every == null)
			Global.Every = new HashMap<String, Array<Type>>();

		Array<Type> res = new Array<Type>(false, 1, Type.class);

		for (Entry<String, Array<Type>> E : Global.Every.entrySet()) {
			if (E.getKey().contains(word))
				res.addAll(E.getValue().toArray());
		}

		return res;
	}

	public static Array<Type> getAllOf(String word) {
		// list of all types containing word
		if (Global.Every == null)
			Global.Every = new HashMap<String, Array<Type>>();

		Array<Type> res = new Array<Type>(false, 1, Type.class);

		for (Entry<String, Array<Type>> E : Global.Every.entrySet()) {
			if (E.getValue().toString().contains(word)) {
				res.addAll(E.getValue().toArray());
				// System.out.println(E.getKey() + "> > " + E.getValue() + " " + word);
			}
		}
		for (Type T : res) {
			// System.out.println("*"+T.Node.toString() + " "+word);
			if (!T.Node.toString().contains(word))
				res.removeValue(T, true);
		}

		return res;

	}

	public static Array<Type> getSimilar(String word) {
		if (Global.Every == null)
			Global.Every = new HashMap<String, Array<Type>>();

		Array<Type> res = new Array<Type>(false, 1, Type.class);

		for (Entry<String, Array<Type>> E : Global.Every.entrySet()) {
			if (E.getValue().toString().contains(word) || E.getKey().contains(word) || word.contains(E.getKey())) {
				res.addAll(E.getValue().toArray());
				// System.out.println(E.getKey() + "> > " + E.getValue() + " " + word);
			}
		}

		return res;

	}
	
	public String toLog()
	{
		String log = "";
		log+="Global\n";
		log += Every;
		return log;
	}
	
	
	public void dispose()
	{
		this.Every.clear();
	}
}
