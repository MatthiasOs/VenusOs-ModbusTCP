package de.ossi.wolfsbau.modbus.data.unit;

/** 0=Ok;1=Warning;2=Alarm */
public class Alarm extends DBusUnit {
	public Alarm(String name) {
		super(name);
		values.put(0, "Ok");
		values.put(1, "Warning");
		values.put(2, "Alarm");
	}
}
