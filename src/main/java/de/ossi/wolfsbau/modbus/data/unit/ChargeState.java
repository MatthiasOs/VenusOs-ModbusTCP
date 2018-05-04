package de.ossi.wolfsbau.modbus.data.unit;

/**
 * 0=Off;2=Fault;3=Bulk;4=Absorption;5=Float;6=Storage;7=Equalize;11=Other
 * (Hub-1);252=Hub-1
 */
public class ChargeState extends DBusUnit {
	public ChargeState(String name) {
		super(name);
		values.put(0, "Off");
		values.put(2, "Fault");
		values.put(3, "Bulk");
		values.put(4, "Absorption");
		values.put(5, "Float");
		values.put(6, "Storage");
		values.put(7, "Equalize");
		values.put(11, "Other (Hub-1)");
		values.put(252, "Hub-1");
	}
}
