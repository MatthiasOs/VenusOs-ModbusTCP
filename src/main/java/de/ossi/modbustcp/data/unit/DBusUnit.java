package de.ossi.modbustcp.data.unit;

import java.io.Serializable;

import lombok.Getter;
import lombok.ToString;

@ToString(of = "name")
public class DBusUnit implements Serializable {

	private static final long serialVersionUID = 1496887712045628303L;
	public static final DBusUnit V = new DBusUnit("V");
	public static final DBusUnit V_AC = new DBusUnit("V AC");
	public static final DBusUnit V_DC = new DBusUnit("V DC");
	public static final DBusUnit A_AC = new DBusUnit("A AC");
	public static final DBusUnit A_DC = new DBusUnit("A DC");
	public static final DBusUnit A = new DBusUnit("A");
	public static final DBusUnit W = new DBusUnit("W");
	public static final DBusUnit HZ = new DBusUnit("Hz");
	public static final DBusUnit KWH = new DBusUnit("kWh");
	public static final DBusUnit VA = new DBusUnit("VA");
	public static final DBusUnit PERCENT = new DBusUnit("%");
	public static final DBusUnit AH = new DBusUnit("Ah");
	public static final DBusUnit S = new DBusUnit("s");
	public static final DBusUnit CELSIUS = new DBusUnit("Degrees celsius");
	public static final DBusUnit ACTIVE_INPUT = new DBusSpecialUnit("Active Input")
			.withValue(0, "AC Input 1")
			.withValue(1, "AC Input 2")
			.withValue(240, "Disconnected");
	public static final DBusUnit ALARM = new DBusSpecialUnit("Alarm")
			.withValue(0, "Ok")
			.withValue(1, "Warning")
			.withValue(2, "Alarm");
	public static final DBusUnit BATTERYLIFE_STATE = new DBusSpecialUnit("BatteryLife State")
			.withValue(0, "External control or BL disabled")
			.withValue(1, "Restarting")
			.withValue(2, "Self-consumption")
			.withValue(3, "Self-consumption")
			.withValue(4, "Self-consumption")
			.withValue(5, "Discharge disabled")
			.withValue(6, "Force charge")
			.withValue(7, "Sustain")
			.withValue(9, "Keep batteries charged")
			.withValue(10, "BL Disabled")
			.withValue(11, "BL Disabled (Low SoC)");
	public static final DBusUnit BATTERY_STATE = new DBusSpecialUnit("Battery State")
			.withValue(0, "Idle")
			.withValue(1, "Charging")
			.withValue(2, "Discharging");
	public static final DBusUnit CHARGE_FLAG = new DBusSpecialUnit("Charge Flag")
			.withValue(0, "Charge allowed")
			.withValue(1, "Charge disabled");
	public static final DBusUnit CHARGER_ON_OFF = new DBusSpecialUnit("Charger On/Off")
			.withValue(1, "On")
			.withValue(4, "Off");
	public static final DBusUnit CHARGE_STATE = new DBusSpecialUnit("Charge state")
			.withValue(0, "Off")
			.withValue(2, "Fault")
			.withValue(3, "Bulk")
			.withValue(4, "Absorption")
			.withValue(5, "Float")
			.withValue(6, "Storage")
			.withValue(7, "Equalize")
			.withValue(11, "Other (Hub-1)")
			.withValue(252, "Hub-1");
	public static final DBusUnit RELAY_STATE = new DBusSpecialUnit("Relay State")
			.withValue(0, "Open")
			.withValue(1, "Closed");
	public static final DBusUnit FEEDBACK_FLAG = new DBusSpecialUnit("Feedback Flag")
			.withValue(0, "Feed in allowed")
			.withValue(1, "Feed in disabled");
	public static final DBusUnit POSITION = new DBusSpecialUnit("Position")
			.withValue(0, "AC input 1")
			.withValue(1, "AC output")
			.withValue(2, "AC input 2");
	public static final DBusUnit PV_STATUS = new DBusSpecialUnit("PV Status")
			.withValue(0, "PV enabled")
			.withValue(1, "PV disabled");
	public static final DBusUnit SOURCE = new DBusSpecialUnit("Source")
			.withValue(0, "Not Available")
			.withValue(1, "Grid")
			.withValue(2, "Generator")
			.withValue(3, "Shore Power")
			.withValue(240, "Not Connected");
	public static final DBusUnit START = new DBusSpecialUnit("Start")
			.withValue(0, "Stop")
			.withValue(1, "Start");
	public static final DBusUnit SWITCH_POSITION = new DBusSpecialUnit("Switch Position")
			.withValue(1, "Charger Only")
			.withValue(2, "Inverter Only")
			.withValue(3, "On")
			.withValue(4, "Off");

	@Getter
	private final String name;
	protected DBusUnit(String name) {
		this.name = name;
	}
}
