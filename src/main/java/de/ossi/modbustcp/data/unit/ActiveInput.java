package de.ossi.modbustcp.data.unit;

/** 0=AC Input 1;1=AC Input 2;240=Disconnected */
public class ActiveInput extends DBusUnit {
	public ActiveInput(String name) {
		super(name);
		values.put(0, "AC Input 1");
		values.put(1, "AC Input 2");
		values.put(240, "Disconnected");
	}
}
