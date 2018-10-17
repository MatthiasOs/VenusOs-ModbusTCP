package de.ossi.modbustcp.modbus.data.unit;

/**1=On;4=Off*/
public class ChargerOnOff extends DBusUnit {
	public ChargerOnOff(String name) {
		super(name);
		values.put(1, "On");
		values.put(4, "Off");
	}
}
