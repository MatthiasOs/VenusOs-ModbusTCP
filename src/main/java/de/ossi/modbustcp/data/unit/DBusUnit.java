package de.ossi.modbustcp.data.unit;

import java.io.Serializable;

import lombok.Getter;
import lombok.ToString;

@ToString(of = "name")
public class DBusUnit implements Serializable {

	private static final long serialVersionUID = 1496887712045628303L;
	
	//Normal units >>>
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
	//Do we really need a count unit? Maybe an empty unit instead?
	public static final DBusUnit COUNT = new DBusUnit("Count");
	
	//Special units with values >>>
	public static final DBusUnit STATE = new DBusSpecialUnit("State")
			.withValue(0, "Off")
			.withValue(1, "Low Power")
			.withValue(2, "Fault")
			.withValue(3, "Bulk")
			.withValue(4, "Absorption")
			.withValue(5, "Float")
			.withValue(6, "Storage")
			.withValue(7, "Equalize")
			.withValue(8, "Passthru")
			.withValue(9, "Inverting")
			.withValue(10, "Power assist")
			.withValue(11, "Power supply")
			.withValue(252, "Bulk protection");
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
	public static final DBusUnit ERROR = new DBusSpecialUnit("Error")
			.withValue(0, "No error")
			.withValue(1, "Device is switched off because one of the other phases in the system has switched off")
			.withValue(2, "New and old types MK2 are mixed in the system")
			.withValue(3, "Not all- or more than- the expected devices were found in the system")
			.withValue(4, "No other device whatsoever detected")
			.withValue(5, "Overvoltage on AC-out")
			.withValue(6, "Error in DDC Program")
			.withValue(7, "VE.Bus BMS connected- which requires an Assistant- but no assistant found")
			.withValue(10, "System time synchronisation problem occurred")
			.withValue(14, "Device cannot transmit data")
			.withValue(16, "Dongle missing")
			.withValue(17, "One of the devices assumed master status because the original master failed")
			.withValue(18, "AC Overvoltage on the output of a slave has occurred while already switched off")
			.withValue(22, "This device cannot function as slave")
			.withValue(24, "Switch-over system protection initiated")
			.withValue(25, "Firmware incompatibility. The firmware of one of the connected device is not sufficiently up to date to operate in conjunction with this device")
			.withValue(26, "Internal error");

	@Getter
	private final String name;
	protected DBusUnit(String name) {
		this.name = name;
	}
}
