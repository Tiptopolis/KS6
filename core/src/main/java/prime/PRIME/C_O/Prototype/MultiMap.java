package prime.PRIME.C_O.Prototype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MultiMap<K, V> implements Map<K, ArrayList<V>> {

	public HashMap<K, ArrayList<V>> data;

	public MultiMap() {
		this.data = new HashMap<K, ArrayList<V>>();
	}

	@Override
	public int size() {
		return this.data.size();
	}

	public int size(K key) {
		if (this.containsKey(key))
			return this.get(key).size();
		else
			return -1;
	}

	@Override
	public boolean isEmpty() {
		return this.data.isEmpty();
	}

	public boolean isEmpty(K key) {
		if (this.containsKey(key))
			return this.get(key).isEmpty();
		else
			return true;
	}

	@Override
	public boolean containsKey(Object key) {
		return this.data.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		if (this.data.containsValue(value))
			return true;
		else {
			for (Entry<K, ArrayList<V>> E : this.data.entrySet()) {
				ArrayList list = E.getValue();
				if (list.contains(value))
					return true;
			}
		}

		return false;
	}

	public boolean contains(K key, V value) {
		if (this.containsKey(key)) {
			return this.data.get(key).contains(value);
		}
		return false;
	}

	//////
	// INNER-MAP
	@Override
	public ArrayList<V> get(Object key) {
		if (this.data.containsKey(key))
			return this.data.get(key);

		return null;
	}

	public V get(Object key, int index) {
		if (this.containsKey(key)) {
			return this.get(key).get(index);
		}
		return null;
	}

	public int indexOf(K key, V value) {
		if (this.containsKey(key)) {
			if (this.get(key).contains(value)) {
				return this.get(key).indexOf(value);
			}
		}
		return -1;
	}

	@Override
	public ArrayList<V> put(K key, ArrayList<V> value) {
		if (!this.containsKey(key))
			return this.data.put(key, value);
		else
			return null;
	}

	public ArrayList<V> put(K key) {
		if (!this.containsKey(key)) {
			return this.put(key, new ArrayList<V>());
		}
		return null;
	}

	public boolean add(Object key, Object value) {
		if (this.containsKey(key)) {			
			return this.get(key).add((V) value);
		} else {
			this.put((K) key);
			this.add(key, value);
		}

		return false;
	}

	// INNER-MAP
	//////
	@Override
	public ArrayList<V> remove(Object key) {
		if (this.data.containsKey(key)) {
			return this.data.remove(key);
		}
		return null;
	}

	@Override
	public boolean remove(Object key, Object value) {
		if (this.data.containsKey(key)) {
			for (Entry<K, ArrayList<V>> E : this.data.entrySet()) {
				if (E.getValue().contains(value)) {
					return E.getValue().remove(value);
				}
			}
		}
		return false;
	}

	@Override
	public void putAll(Map<? extends K, ? extends ArrayList<V>> m) {
		this.data.putAll(m);

	}

	public void addAll(K key, ArrayList<V> all) {
		ArrayList<V> Key = this.data.get(key);
		for (V v : all) {
			Key.add(v);
		}
	}

	@Override
	public void clear() {
		for (Entry<K, ArrayList<V>> E : this.data.entrySet()) {
			E.getValue().clear();
		}
		this.data.clear();
	}

	public void clear(K key) {
		if (this.containsKey(key)) {
			this.get(key).clear();
		}
	}

	@Override
	public Set<K> keySet() {

		return this.data.keySet();
	}

	@Override
	public Collection<ArrayList<V>> values() {
		return this.data.values();
	}

	@Override
	public Set<Entry<K, ArrayList<V>>> entrySet() {
		return this.data.entrySet();
	}

	public ArrayList<V> getAll() {
		ArrayList<V> out = new ArrayList<V>();
		for (Entry<K, ArrayList<V>> E : this.data.entrySet()) {
			for (V e : E.getValue()) {
				out.add(e);
			}
		}
		return out;
	}

	@Override
	public String toString() {
		return this.data.toString();
	}

}
