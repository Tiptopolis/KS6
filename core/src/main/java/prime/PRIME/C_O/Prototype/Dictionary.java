package prime.PRIME.C_O.Prototype;

import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary<K, V> extends HashMap<K, V> {

	// an ordered map

	public ArrayList<K> keys = new ArrayList<K>();

	@Override
	public V put(K key, V value) {
		super.put(key, value);
		if (this.containsKey(key))
			this.keys.add(key);

		return value;
	}

	public int indexOf(K key) {
		return keys.indexOf(key);
	}

	public V get(int index) {
		// getOrDefault ->
		return this.get(keys.get(index));
	}

}
