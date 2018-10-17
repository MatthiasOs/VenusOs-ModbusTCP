package de.ossi.modbustcp.modbus.data.unit;

/** 0=Not Available;1=Grid;2=Generator;3=Shore Power;240=Not Connected **/
public class Source extends DBusUnit {
	public Source(String name) {
		super(name);
		values.put(0, "Not Available");
		values.put(1, "Grid");
		values.put(2, "Generator");
		values.put(3, "Shore Power");
		values.put(240, "Not Connected");
	}
}
