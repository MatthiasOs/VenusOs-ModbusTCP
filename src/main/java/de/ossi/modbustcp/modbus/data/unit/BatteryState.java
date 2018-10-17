package de.ossi.modbustcp.modbus.data.unit;

/** 0=Idle;1=Charging;2=Discharging **/
public class BatteryState extends DBusUnit {
	public BatteryState(String name) {
		super(name);
		values.put(0, "Idle");
		values.put(1, "Charging");
		values.put(2, "Discharging");
	}
}
