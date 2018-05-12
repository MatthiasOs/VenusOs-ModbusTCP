package de.ossi.wolfsbau.modbus.data.unit;

/** 0=Stop;1=Start */
public class Start extends DBusUnit {
	public Start(String name) {
		super(name);
		values.put(0, "Stop");
		values.put(1, "Start");
	}
}
