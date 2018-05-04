package de.ossi.wolfsbau.modbus.data.unit;

/** 0=PV enabled;1=PV disabled */
public class PvStatus extends DBusUnit {

	public PvStatus(String name) {
		super(name);
		values.put(0, "PV enabled");
		values.put(1, "PV disabled");
	}
}
