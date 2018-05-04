package de.ossi.wolfsbau.modbus.data.unit;

/** 0=Feed in allowed;1=Feed in disabled */
public class FeedbackFlag extends DBusUnit {

	public FeedbackFlag(String name) {
		super(name);
		values.put(0, "Feed in allowed");
		values.put(1, "Feed in disabled");
	}
}
