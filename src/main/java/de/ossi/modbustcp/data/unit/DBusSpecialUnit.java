package de.ossi.modbustcp.data.unit;

import java.util.HashMap;
import java.util.Map;

public class DBusSpecialUnit extends DBusUnit {

	private static final long serialVersionUID = 8350440834710210012L;
	private final Map<Integer, String> values = new HashMap<>();

	protected DBusSpecialUnit(String name) {
		super(name);
	}

	public DBusSpecialUnit withValue(int key, String value) {
		values.put(key, value);
		return this;
	}

	public String getUnit(Integer value) {
		return values.get(value);
	}
}
