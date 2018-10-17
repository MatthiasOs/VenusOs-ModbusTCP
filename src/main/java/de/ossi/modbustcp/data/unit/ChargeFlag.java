package de.ossi.modbustcp.data.unit;

/** 0=Charge allowed;1=Charge disabled */
public class ChargeFlag extends DBusUnit {
	public ChargeFlag(String name) {
		super(name);
		values.put(0, "Charge allowed");
		values.put(1, "Charge disabled");
	}
}
