package de.ossi.wolfsbau.modbus.data;

public enum DBusUnit {
	W("W"), 
	V_DC("V DC"),
	V_AC("V AC"), 
	A("A"), 
	A_AC("A AC"), 
	A_DC("A DC"), 
	PERCENT("%"), 
	AH("Ah"), 
	HZ("Hz"),
	VA_OR_WATTS("VA or Watts"),
	S("s"),
	/** 0=Open;1=Closed **/
	RELAY_STATE("Relay State"),
	/** 0=Idle;1=Charging;2=Discharging **/
	BATTERY_STATE("Battery State"),
	/** 0=Not Available;1=Grid;2=Generator;3=Shore Power;240=Not Connected **/
	SOURCE("Source"),
	/** 0=AC Input 1;1=AC Input 2;240=Disconnected */
	ACTIVE_INPUT("Active Input"),
	/**1=Charger Only;2=Inverter Only;3=On;4=Off*/
	SWITCH_POSITION("Switch Position"),
	/**0=Ok;1=Warning;2=Alarm*/
	ALARM("Alarm"),
	/**0=Charge allowed;1=Charge disabled*/
	CHARGE_FLAG("Charge Flag"), 
	/**0=Feed in allowed;1=Feed in disabled*/
	FEEDBACK_FLAG("Feedback Flag"),
	/**0=PV enabled;1=PV disabled*/
	PV("PV"),
	;

	private final String name;

	private DBusUnit(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
