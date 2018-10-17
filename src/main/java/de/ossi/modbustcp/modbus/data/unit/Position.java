package de.ossi.modbustcp.modbus.data.unit;

/**0=AC input 1;1=AC output;2=AC input 2*/
public class Position extends DBusUnit {
	public Position(String name) {
		super(name);
		values.put(0, "AC input 1");
		values.put(1, "AC output");
		values.put(2, "AC input 2");
	}
}
