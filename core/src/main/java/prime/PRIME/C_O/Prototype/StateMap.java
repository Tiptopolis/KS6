package prime.PRIME.C_O.Prototype;

import java.util.HashMap;

public class StateMap<T> {

	// rename this stuff, taken from uFPSController in Metatron
	public HashMap<String, T> CommandMap = new HashMap<String, T>();
	public HashMap<T, Boolean> CmdStateMap = new HashMap<T, Boolean>();

	public boolean get(String state) {
		return this.CmdStateMap.get(this.CommandMap.get(state));
	}

	public void set(String key, T value) {
		this.CommandMap.put(key, value);
		this.CmdStateMap.put(value, false);
	}

	public void clear() {
		this.CommandMap.clear();
		this.CmdStateMap.clear();
	}
}
