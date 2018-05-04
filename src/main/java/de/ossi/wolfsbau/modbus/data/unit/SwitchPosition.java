package de.ossi.wolfsbau.modbus.data.unit;

/** 1=Charger Only;2=Inverter Only;3=On;4=Off */
public class SwitchPosition extends DBusUnit {

	public SwitchPosition(String name) {
		super(name);
		values.put(1, "Charger Only");
		values.put(2, "Inverter Only");
		values.put(3, "On");
		values.put(4, "Off");
	}
}
