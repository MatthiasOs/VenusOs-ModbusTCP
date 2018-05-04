package de.ossi.wolfsbau.modbus.data.unit;

/** 0=Open;1=Closed **/
public class RelayState extends DBusUnit {

	public RelayState(String name) {
		super(name);
		values.put(0, "Open");
		values.put(1, "Closed");
	}
}
