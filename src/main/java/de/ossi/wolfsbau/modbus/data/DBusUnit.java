package de.ossi.wolfsbau.modbus.data;

public enum DBusUnit {
	W("W"), 
	V_DC("V DC"), 
	A_DV("A DC"), 
	PERCENT("%"), 
	AH("Ah"), 
	S("s"),
	/** 0=Open;1=Closed **/
	RELAY_STATE("Relay State"),
	/** 0=Idle;1=Charging;2=Discharging **/
	BATTERY_STATE("Battery State"),
	/** 0=Available;1=Grid;2=Generator;3=Shore Power;240=Not Connected **/
	SOURCE("Source"),
	;

	private final String name;

	private DBusUnit(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
