package de.ossi.wolfsbau.modbus.data.unit;

/**
 * 0=External control or BL disabled
 * 1=Restarting
 * 2=Self-consumption
 * 3=Self-consumption
 * 4=Self-consumption
 * 5=Discharge disabled
 * 6=Force charge
 * 7=Sustain
 * 9=Keep batteries charged
 * 10=BL Disabled
 * 11=BL Disabled (Low SoC)
 **/
public class BatteryLifeState extends DBusUnit {
	public BatteryLifeState(String name) {
		super(name);
		values.put(0, "External control or BL disabled");
		values.put(1, "Restarting");
		values.put(2, "Self-consumption");
		values.put(3, "Self-consumption");
		values.put(4, "Self-consumption");
		values.put(5, "Discharge disabled");
		values.put(6, "Force charge");
		values.put(7, "Sustain");
		values.put(9, "Keep batteries charged");
		values.put(10, "BL Disabled");
		values.put(11, "BL Disabled (Low SoC)");
	}
}

