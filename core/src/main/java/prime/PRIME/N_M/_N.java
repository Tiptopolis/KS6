package prime.PRIME.N_M;

import static prime.PRIME.uAppUtils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;

import prime.METATRON._SYS._VTS;

//import com.google.common.collect.HashMultimap;

import prime.PRIME.C_O.Prototype.MultiMap;

public interface _N<N> {
	public static String A_N = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

	public default void Name(String name) {

	}

	public String Name();

	public N Class();

	public Object Type();

	public default _N Symbol() {
		return this;
	}

	public default Number Index() {
		return 0;
	}

	public default boolean isOf(_N N) {

		return this.Symbol().toString().contains(N.Symbol().toString());
	}

	public default <O extends Object> O get(_N s) {
		return this.get();
	}

	public default <O extends Object> O get(String s) {
		return this.get();
	}

	public <O extends Object> O get();

	public default <I extends Object, O extends Object> O get(I in) {
		return null;
	}

	public default <I extends Object, O extends Object> O get(I... in) {

		return null;
	}

	public default <I extends Object, O extends Object> O set(I in) {
		return null;
	}

	public default <I extends Object, O extends Object> O set(I... in) {
		return null;
	}

	public default boolean message(String message) {
		// Log(this.toString() + " " + message);
		return message(this, message, true, null);
	}

	public default boolean message(_N source, String message, boolean callback, @Null Object data) {
		// Log(this.toString() + " " + "CALLBACK_FOR:"+message);
		source.message(this, "CALLBACK_FOR:" + message, false, data);
		return true;
	}

	public default Array<Object> getBits() {
		// return (O[]) new Object[] { this.Name(), this.Type(), this.Symbol() };
		return new Array<Object>(true, 0, Object.class);
	}

	public default <O extends Object> Array<O> properties() {
		// return (O[]) new Object[] { this.Name(), this.Type(), this.Symbol() };
		return new Array<O>(true, 0, Object.class);
	}

	public default String toLog() {
		return this.toString();
	}

	//// GENERALITIES

	public default <O extends Object> O as() {
		return null;
	}

	///////////////////////////////////////////
	public class Name implements _N, CharSequence {
		public String Reference;
		public String Value;

		public Name(String name) {
			this.Reference = name;
			this.Value = "NAME";
		}

		public Name(String ref, String val) {
			this.Reference = ref;
			this.Value = val;
		}

		public Name(String ref, Type val) {
			this.Reference = ref;
			this.Value = val.Node.toString();
		}

		@Override
		public String Name() {
			return this.Reference;
		}

		@Override
		public Object Class() {
			return this.getClass();
		}

		@Override
		public String Type() {
			return this.Value;
		}

		@Override
		public CharSequence get() {
			return this;
		}

		@Override
		public int length() {
			return this.Value.length();
		}

		@Override
		public char charAt(int index) {
			return this.Value.charAt(index);
		}

		@Override
		public CharSequence subSequence(int start, int end) {
			return this.Value.subSequence(start, end);
		}

		@Override
		public String toString() {
			if (!this.Reference.equals(this.Value))
				return this.Reference + ":" + this.Value;
			else
				return this.Value;
		}

	}

	///////////////////////////////////////////
	public class Type implements _N<Class>, CharSequence {

		// public int Count = 0;
		public String Reference = "null";
		public Class Value;
		public _N Node;

		protected boolean subclass;
		public Type of;

		public static Type Type = new Type("Type", Type.class, Class.class);

		public static Type Number = new Type("Number", Number.class);
		public static Type NumberType = new Type("Number", Number.class, Type);

		public static HashMap<Type, String> Every = new HashMap<Type, String>();

		public Type() {
			this.Reference = "?";
			this.Value = Object.class;
			this.of = this;
			this.Node = this;
			this.reg();
		}

		public Type(String name) {

			this.Reference = name;			
			this.Value = Object.class;
			this.of = this;
			this.Node = this;
			this.reg();
		}

		public Type(String name, Class val) {

			this.Reference = name;			
			this.Value = val;
			this.of = this;
			this.Node = this;
			this.reg();
		}

		public Type(String name, Object val) {

			this.Reference = name;			
			this.Value = val.getClass();
			this.of = this;
			this.Node = this;
			this.reg();
		}

		public Type(Object val) {
			String name = val.getClass().getSimpleName();

			this.Reference = name;			
			this.Value = val.getClass();
			this.of = this;
			this.Node = this;
			this.reg();
		}

		public Type(Number val) {
			String name = val.getClass().getSimpleName();

			this.Reference = name;			
			this.Value = val.getClass();
			this.of = this;
			this.Node = this;
			this.reg();
		}

		/////////////////////////

		public Type(String name, Class val, Class ext) {

			this.Reference = name;			
			this.Value = val;
			this.of = new Type(ext.getSimpleName(), ext);
			this.subclass = true;
			this.Node = this;
			this.reg();
		}

		public Type(String name, Object val, Object ext) {

			this.Reference = name;			
			this.Value = val.getClass();
			this.of = new Type(ext.getClass().getSimpleName(), ext);
			this.subclass = true;
			this.Node = this;
			this.reg();
		}

		public Type(String name, Class val, Object ext) {

			this.Reference = name;			
			this.Value = val;
			this.of = new Type(ext.getClass().getSimpleName(), ext);
			this.subclass = true;
			this.Node = this;
			this.reg();
		}

		public Type(String name, Object val, Class ext) {

			this.Reference = name;		
			this.Value = val.getClass();
			this.of = this.of = new Type(ext.getClass().getSimpleName(), ext);
			this.subclass = true;
			this.Node = this;
			this.reg();
		}

		public Type(String name, Type ext) {

			this.Reference = name;		
			this.Value = ext.Value;
			this.of = ext;
			this.subclass = true;
			this.Node = this;
			this.reg();
		}

		///
		public Type(boolean global) {
			this.Reference = "?";
			this.Value = Object.class;
			this.of = this;
			this.Node = this;
			this.reg(global);
		}

		public Type(boolean global, String name) {

			this.Reference = name;			
			this.Value = Object.class;
			this.of = this;
			this.Node = this;
			this.reg(global);
		}

		public Type(boolean global, String name, Object val) {

			this.Reference = name;			
			this.Value = val.getClass();
			this.of = this;
			this.Node = this;
			this.reg(global);
		}

		public Type(boolean global, String name, Class val) {

			this.Reference = name;			
			this.Value = val;
			this.of = this;
			this.Node = this;
			this.reg(global);
		}

		public Type(boolean global, String name, Object val, Object ext) {

			this.Reference = name;			
			this.Value = val.getClass();
			this.of = new Type(ext.getClass().getSimpleName(), ext);
			this.subclass = true;
			this.Node = this;
			this.reg(global);
		}

		public Type(boolean global, String name, Type ext) {

			this.Reference = name;		
			this.Value = ext.Value;
			this.of = ext;
			this.subclass = true;
			this.Node = this;
			this.reg(global);
		}

		public void reg() {
			this.reg(true);
		}

		private void reg(boolean global) {
			if (global) {
				if (Every == null)
					Every = new HashMap<Type, String>();

				if (!Every.containsKey(this) && !Every.containsValue(this.Reference))
					Every.put(this, this.Reference);

				// Metatron.GLOBAL.put(this); // GLOBAL PUT
				_VTS.Global.put(this);
			}

		}

		public static Type extend(Type n, String name) {
			Type k = new Type(name, n.Class(), n.Class());
			return k;
		}

		public static Type extendA(Type a, Type b) {
			b.of = a;
			b.subclass = true;
			b.reg();
			a.reg();

			return b;
		}

		@Override
		public String Name() {
			return this.Reference;
		}

		public Type Type() {
			if (this.subclass)
				return this.of;
			else
				return this;
		}

		@Override
		public Class Class() {
			// return Class.class;
			return this.Value.getClass();
		}

		@Override
		public _N Symbol() {
			return new Name("<" + this.Reference + ">", "<" + this.Reference + ">");
		}

		@Override
		public Class get() {
			return this.Value;
		}

		public boolean subclass() {
			return (this.subclass);
		}

		public Type subclass(String name, Type t) {
			return new Type(name, t);
		}

		public boolean isOf(Class c) {
			// For some reason, equality returns change when i remove the Log()s
			if (c.equals(Object.class) /* || c.equals(_Object.class) */) {
				// Log(" ==<<1");
				return true;
			}

			if (c.equals(String.class)) {
				// Log(" ==<<2 " + c.toString());
				return this.isOf(c.getSimpleName());
			}

			if (this.getClass().isAssignableFrom(c)) {
				// Log(" ==<<3");
				return true;
			}
			if (this.subclass) {
				boolean a = false;
				boolean b = false;

				if (this.of.Value.equals(c)) {
					// Log(" ==<<4_1 "+ this.of.Value); // keep these seperate for the moment, for
					// debugging Node
					// Types
					a = true;
				}
				if (this.of.Value.isAssignableFrom(c)) {
					// Log(" ==<<4_2");
					b = true;
				}
				if (this.of.Reference.equals(this.Reference)) {
					// Log(" ==<<4_3");
				}
				return a && b;
			}

			// Log("!=<<O");
			return false;

		}

		public boolean isOf(String o) {
			// Log(" >str>" + o);

			if (o.equals("" + Object.class.getSimpleName()) /* || o.equals("" + _Object.class.getSimpleName()) */)
				return true;
			if (this.subclass && (this.of.Name().toUpperCase().equals(o)
					|| this.of.Value.getSimpleName().toUpperCase().equals(o)))
				return true;

			return false;
		}

		public boolean isOf(Type o) {

			if (o.equals(this)) {
				// Log(" --<0" + o + " " + this);
				return true;
			}

			if (this.isOf(o.Class())) {
				// Log(" --<1" + o + " " + o.Type());
				return true;
			}

			if (this.isOf(o.Type().Reference)) {
				// Log(" --<2");
				return true;
			}

			if (this.Node.toString().contains(o.of.toString())) {
				// Log(" --<3");
				return true;
			}

			if (this.isOf(o.Value.toString()) || this.isOf(o.Class().toString())) {
				// Log(" --<4");
				return true;
			}

			return false;
		}

		public boolean isOf(Object o) {
			return this.isOf(o.getClass());
		}

		@Override
		public boolean equals(Object other) {

			if (other instanceof Class) {
				Class O = (Class) other;
				// Log(" >EQ=1[T] " + this.Name() + " = " + O.getSimpleName());
				return this.Name().equals(O.getSimpleName());
			}
			if (other instanceof Type) {
				// Log(" >EQ=2<T> " + this.Name() + " = " + ((Type) other).Name());
				return this.Name().equals(((Type) other).Name());
			}

			if (other instanceof CharSequence) {
				String s = other.toString();
				// Log(" >EQ=3[C] " + this.Name() + " = " + s);
				return (this.Name().equals(s));
			}

			return false;
		}

		@Override
		public String toString() {
			String ext = "";
			if (this.subclass || (this.of != null && this.Value.getClass().equals(this.of)))
				ext = ":" + this.of;
			return "<" + this.Reference + ">" + ext;
		}

		@Override
		public String toLog() {
			String ext = "";
			String base = this.Class().getSimpleName();

			if (this.subclass || (this.of != null && this.Value.getClass().equals(this.of))) {
				base = this.of.Name();
				ext = ":<" + this.of.Class().getSimpleName() + ">";
			}

			return "[" + this.toString() + "][(<" + base + ">" + ext + ")]";
		}

		public String megaLog() {
			String log = "";
			log += "---\n";
			log += this.toString() + "\n";
			log += "REF: " + this.Reference + "\n";
			log += "SYMB:" + this.Symbol() + "\n";
			log += "TYPE: " + this.Type() + "\n";
			log += "NODE:" + this.Node + "\n";
			log += "VALUE: " + this.Value + "\n";
			log += "CLASS: " + this.Class() + "\n";

			log += "---\n";
			return log;
		}

		@Override
		public char charAt(int arg0) {
			return this.Name().charAt(arg0);
		}

		@Override
		public int length() {
			return this.Name().length();
		}

		@Override
		public CharSequence subSequence(int arg0, int arg1) {
			return this.Name().subSequence(arg0, arg1);
		}

	}

	public class Node<T> implements _N<Object> {

		public boolean State = true;

		public static Type Node = new Type("Node", Node.class);
		public static Type NodeType = new Type("Node", Node.class, Type.class);
		public String Label;
		public Type Of;
		public T Data;

		public Array<Node> Properties; // from Data
		public MultiMap<String, Node> Connections;

		public int depth = 0;
		public boolean update = false;

		public Node() {
			this.Label = "NULL";
			this.Data = (T) "NULL";
			this.Of = NodeType;

			this.Properties = new Array<Node>();
			this.Connections = new MultiMap<String, Node>();
			this.Connections.add("Self", this);
		}

		public Node(T data) {
			this.Label = "Node";
			this.Of = new Type(true, data.getClass().getSimpleName(), data.getClass());
			this.Data = data;
			this.Properties = new Array<Node>();
			this.Connections = new MultiMap<String, Node>();
			this.Connections.add("Self", this);

		}

		public Node(String label, T data) {
			this.Label = label;
			this.Of = new Type(true, this.getClass().getSimpleName(), data.getClass());
			this.Data = data;
			this.Properties = new Array<Node>();
			this.Connections = new MultiMap<String, Node>();
			this.Connections.add("Self", this);
		}

		public Node(_N data) {
			this.Label = data.Name();
			this.Of = new Type(true, this.Label, data.getClass());
			this.Data = (T) data;

			this.Properties = new Array<Node>();
			this.Connections = new MultiMap<String, Node>();
			this.Connections.add("Self", this);
		}

		public Node(String label, _N data) {
			this.Label = label;
			this.Of = new Type(true, this.Label, data.Type());
			this.Data = (T) data;

			this.Properties = new Array<Node>();
			this.Connections = new MultiMap<String, Node>();
			this.Connections.add("Self", this);
		}

		public Node(String label, Type data) {
			this.Label = label;
			this.Of = data;
			this.Data = (T) data;

			this.Properties = data.properties();
			this.Connections = new MultiMap<String, Node>();
			this.Connections.add("Self", this);
		}

		public Node(String label, Node data) {
			this.Label = label;
			this.Of = data.Of;
			this.Data = (T) data.Data;

			this.Properties = data.properties();
			this.Connections = new MultiMap<String, Node>();
			this.Connections.add("Self", this);
		}

		public Node(String label, T data, Type type) {
			this.Label = label;
			this.Of = type;
			this.Data = data;
			this.Properties = new Array<Node>();
			this.Connections = new MultiMap<String, Node>();
			this.Connections.add("Self", this);
		}

		public boolean connect(String con, Node other) {

			return (this.Connections.add(con, other));
		}

		public boolean hasConnection(String conName) {

			return this.Connections.containsKey(conName);
		}

		public ArrayList<Node> getConnected(String conName) {
			ArrayList<Node> N = null;
			for (Entry<String, ArrayList<Node>> E : this.Connections.entrySet()) {
				if (E.getKey().toUpperCase().equals(conName.toUpperCase()))
					N = E.getValue();
			}

			return N;

		}

		public ArrayList<Node> getConnected(String conName, String conType) {
			ArrayList<Node> N;
			ArrayList<Node> C = new ArrayList<Node>();

			if (this.Connections.containsKey(conName)) {
				N = new ArrayList<Node>();
				for (Node n : N) {
					if (n.equals(conType) || n.isOf(conType))
						C.add(n);
				}
			}

			return C;
		}

		public boolean isConnected(Node N) {

			return this.Connections.containsValue(N);
		}

		public boolean isConnected(Object N) {

			return this.Connections.containsValue(N);
		}

		@Override
		public String Name() {
			return this.Label;
		}

		@Override
		public Type Type() {
			return this.Of;
		}

		@Override
		public Class Class() {
			return this.Data.getClass();
		}

		@Override
		public _N Symbol() {
			return this.Type();
		}

		@Override
		public <T> T get() {
			return (T) this.Data;
		}

		@Override
		public <O extends Object, Node> Node set(O in) {
			if (this.isOf(in) || this.Type().isOf(in) || in.getClass().equals(this.Data.getClass()))
				this.Data = (T) in;
			else
				Log("CANNOT SET NODE <" + this.Data.getClass().getSimpleName() + "> TO A <"
						+ in.getClass().getSimpleName() + ">---------------------------------<<");

			if (this.Connections.containsKey("Listener")) {
				ArrayList<_N.Node> a = (ArrayList<_N.Node>) this.Connections.get("Listener");
				for (_N.Node N : a) {
					N.message(this, "ListenerUpdate", true, this.Data);
				}
			}

			return (Node) this;
		}

		public void set(String name, Object O) {
			for (Node P : this.Properties) {
				if (P.Label == name) {
					try {
						P.set(O);
					} finally {

					}
				}
			}
		}

		public void update() {
			this.update = false;
		}

		@Override
		public boolean message(String message) {
			// self-targeting
			return message(this, message, true, this.get());
		}

		@Override
		public boolean message(_N source, String message, boolean callback, @Null Object data) {

			if (callback) {
				source.message(this, "CALLBACK_FOR:" + message, false, data);
			}

			return true;
		}

		public boolean isOf(Node other) {
			return this.isOf(other.Node);
		}

		public boolean isOf(Type type) {

			if (this.Class().toString().equals(type.toString()))
				return true;
			if (this.Type().isOf(type) && this.Type().toString().contains(type.Node.toString()))
				return true;
			if (this.toString().contains(type.toString()))
				return true;

			return false;

		}

		public boolean isOf(String n) {
			if (this.get().toString().contains(n))
				return true;
			return false;
		}

		public boolean isOf(Object o) {

			if (this.get().getClass().isAssignableFrom(o.getClass()))
				return true;

			else
				return this.isOf(o.toString());
		}

		public Node cpy() {
			Node N = new Node(this.Label);
			N.Data = this.Data;
			if (this.Of != null)
				N.Of = this.Of;

			return N;
		}

		@Override
		public boolean equals(Object other) {
			if (other instanceof Node) {
				Node N = (Node) other;
				if (this.Data.equals(N.Data))
					return true;
			}

			if (other instanceof Type) {
				return this.Type().isOf(((Type) other));
			}

			if (other instanceof String) {
				String o = "" + other;
				if (this.Name().toString().equals(o))
					return true;

				if (this.Class().toString().equals(o))
					return true;
			}

			return false;
		}

		@Override
		public Array<Node> properties() {
			return this.Properties;
		}

		public String hashString() {
			return "@" + Integer.toHexString(this.hashCode());
		}

		public String lhString() {
			return "[" + this.Name() + ":" + this.get() + "]";
		}

		public String rhString() {
			return "[" + this.Class().getSimpleName() + ":" + this.Type() + "]";
		}

		@Override
		public String toString() {
			return "" + this.Type() + ":" + this.get();
		}

		@Override
		public String toLog() {
			String s = this.toString() + " {::}" + "\n";
			s += " " + this.Properties + "\n";
			s += " " + this.Connections + "\n";

			return s;
		}

		public String megaLog() {
			String log = this.toString() + "\n";
			log += "LABEL: " + this.Label + "\n";
			log += "OF:" + this.Of + " " + this.Of.Value + "\n";
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
}
