package de.ossi.modbustcp.db.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Enum Pair welches zwei verschiedene Enums aufeinander matcht
 * 
 * TODO Besser generisch lösen. Problem ist type erasure. Deshalb kann man nicht
 * die beiden Methoden "V get(K k)" und "K get(V v)" haben. Andere Möglichkeit
 * wäre beim Aufruf zu casten.
 * 
 * @author ossi
 *
 * @param <K>
 * @param <V>
 */
public class EnumPair<K extends Enum<?>, V extends Enum<?>> {
	private Map<K, V> enumPairs = new HashMap<>();

	public boolean add(K k, V v) {
		if (istKeyOderValueBereitsVorhanden(k, v)) {
			return false;
		}
		int sizeOld = size();
		enumPairs.put(k, v);
		int sizeNew = size();
		return sizeOld < sizeNew;
	}

	private boolean istKeyOderValueBereitsVorhanden(K k, V v) {
		return getV(k) != null || getK(v) != null;
	}

	public boolean isPair(K k, V v) {
		V vFound = getV(k);
		return v.equals(vFound);
	}

	public V getV(K k) {
		return enumPairs.get(k);
	}

	public K getK(V v) {
		Optional<K> kFound = enumPairs.entrySet().stream().filter(enumValue -> enumValue.getValue().equals(v)).map(Map.Entry::getKey).findFirst();
		return kFound.isPresent() ? kFound.get() : null;
	}

	public int size() {
		return enumPairs.size();
	}

	public void clear() {
		enumPairs.clear();
	}

}
