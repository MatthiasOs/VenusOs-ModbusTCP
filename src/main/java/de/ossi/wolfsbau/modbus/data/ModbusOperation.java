package de.ossi.wolfsbau.modbus.data;

import de.ossi.wolfsbau.modbus.data.unit.DBusUnit;

/**
 * Unterstuetzte Modbus Operationen nach dem Excel Sheet:
 * files/CCGX-Modbus-TCP-register-list-2.12.xlsx
 * 
 * @author ossi
 *
 */
public abstract class ModbusOperation {

	public static final ModbusOperation INPUT_VOLTAGE_PHASE_1 = new ModbusUnsignedOperation(3, "Input voltage phase 1", 10, DBusUnit.V_AC);
	public static final ModbusOperation INPUT_CURRENT_PHASE_1 = new ModbusSignedOperation(6, "Input current phase 1", 10, DBusUnit.A_AC);
	public static final ModbusOperation INPUT_FREQUENCY_1 = new ModbusSignedOperation(9, "Input frequency 1", 100, DBusUnit.HZ);
	public static final ModbusOperation INPUT_POWER_1 = new ModbusSignedOperation(12, "Input power 1", 0.1, DBusUnit.VA_OR_WATTS);
	public static final ModbusOperation OUTPUT_FREQUENCY = new ModbusSignedOperation(21, "Output frequency", 100, DBusUnit.HZ);
	public static final ModbusOperation ACTIVE_INPUT_CURRENT_LIMIT = new ModbusSignedOperation(22, "Active input current limit", 10, DBusUnit.A);
	public static final ModbusOperation OUTPUT_POWER_1 = new ModbusSignedOperation(23, "Output power 1", 0.1, DBusUnit.VA_OR_WATTS);
	public static final ModbusOperation BATTERY_VOLTAGE = new ModbusUnsignedOperation(26, "Battery voltage", 100, DBusUnit.V_DC);
	public static final ModbusOperation BATTERY_CURRENT = new ModbusSignedOperation(27, "Battery current", 10, DBusUnit.A_DC);
	public static final ModbusOperation ACTIVE_INPUT = new ModbusUnsignedOperation(29, "Active input", 1, DBusUnit.ACTIVE_INPUT);
	public static final ModbusOperation VE_BUS_SOC = new ModbusUnsignedOperation(30, "VE.Bus state of charge", 10, DBusUnit.PERCENT);
	public static final ModbusOperation SWITCH_POSITION = new ModbusUnsignedOperation(33, "Switch Position", 1, DBusUnit.SWITCH_POSITION);
	public static final ModbusOperation TEMPREATURE_ALARM = new ModbusUnsignedOperation(34, "Temperature alarm", 1, DBusUnit.ALARM);
	public static final ModbusOperation LOW_BATTERY_ALARM = new ModbusUnsignedOperation(35, "Low battery alarm", 1, DBusUnit.ALARM);
	public static final ModbusOperation OVERLOAD_ALARM = new ModbusUnsignedOperation(36, "Overload alarm", 1, DBusUnit.ALARM);
	public static final ModbusOperation ESS_POWER_SET_POINT_PHASE_1 = new ModbusSignedOperation(37, "ESS power setpoint phase 1", 1, DBusUnit.W);
	public static final ModbusOperation ESS_DISABLE_CHARGE_FLAG_PHASE = new ModbusSignedOperation(38, "ESS disable charge flag phase", 1, DBusUnit.CHARGE_FLAG);
	public static final ModbusOperation ESS_DISABLE_FEEDBACK_FLAG_PHASE = new ModbusSignedOperation(39, "ESS disable feedback flag phase", 1, DBusUnit.FEEDBACK_FLAG);
	public static final ModbusOperation ESS_POWER_SET_POINT_PHASE_2 = new ModbusSignedOperation(40, "ESS power setpoint phase 2", 1, DBusUnit.W);
	public static final ModbusOperation ESS_POWER_SET_POINT_PHASE_3 = new ModbusSignedOperation(41, "ESS power setpoint phase 3", 1, DBusUnit.W);
	public static final ModbusOperation TEMPREATURE_SENSOR_ALARM = new ModbusUnsignedOperation(42, "Temperatur sensor alarm", 1, DBusUnit.ALARM);
	public static final ModbusOperation VOLTAGE_SENSOR_ALARM = new ModbusUnsignedOperation(43, "Voltage sensor alarm", 1, DBusUnit.ALARM);
	public static final ModbusOperation TEMPREATURE_ALARM_L1 = new ModbusUnsignedOperation(44, "Temperature alarm L1", 1, DBusUnit.ALARM);
	public static final ModbusOperation LOW_BATTERY_ALARM_L1 = new ModbusUnsignedOperation(45, "Low battery alarm L1", 1, DBusUnit.ALARM);
	public static final ModbusOperation OVERLOAD_ALARM_L1 = new ModbusUnsignedOperation(46, "Overload alarm L1", 1, DBusUnit.ALARM);
	public static final ModbusOperation RIPPLE_ALARM_L1 = new ModbusUnsignedOperation(47, "Ripple alarm L1", 1, DBusUnit.ALARM);
	public static final ModbusOperation DISABLE_PV_INVERTER = new ModbusUnsignedOperation(56, "Disable PV inverter", 1, DBusUnit.PV_STATUS);

	public static final ModbusOperation AC_CONSUMPTION_L1 = new ModbusUnsignedOperation(817, "AC Consumption L1", 1, DBusUnit.W);
	public static final ModbusOperation AC_CONSUMPTION_L2 = new ModbusUnsignedOperation(818, "AC Consumption L2", 1, DBusUnit.W);
	public static final ModbusOperation AC_CONSUMPTION_L3 = new ModbusUnsignedOperation(819, "AC Consumption L3", 1, DBusUnit.W);
	public static final ModbusOperation ACTIVE_INPUT_SOURCE = new ModbusUnsignedOperation(826, "Active input source", 1, DBusUnit.SOURCE);
	public static final ModbusOperation BATTERY_VOLTAGE_SYSTEM = new ModbusUnsignedOperation(840, "Battery Voltage (System)", 10, DBusUnit.V_DC);
	public static final ModbusOperation BATTERY_CURRENT_SYSTEM = new ModbusSignedOperation(841, "Battery Current (System)", 10, DBusUnit.V_DC);
	public static final ModbusOperation BATTERY_POWER_SYSTEM = new ModbusSignedOperation(842, "Battery Power (System)", 1, DBusUnit.W);
	public static final ModbusOperation BATTERY_SOC_SYSTEM = new ModbusUnsignedOperation(843, "Battery State of Charge (System)", 1, DBusUnit.PERCENT);
	public static final ModbusOperation BATTERY_STATE_SYSTEM = new ModbusUnsignedOperation(844, "Battery state (System)", 1, DBusUnit.BATTERY_STATE);
	public static final ModbusOperation BATTERY_CONSUMED_SYSTEM = new ModbusUnsignedOperation(845, "Battery Consumed Amphours (System)", -10, DBusUnit.AH);
	public static final ModbusOperation BATTERY_TIME_TO_GO_SYSTEM = new ModbusUnsignedOperation(846, "Battery Time to Go (System)", 0.01, DBusUnit.S);
	// Carlo Gavazzi Meter direct
	public static final ModbusOperation GRID_L1_POWER = new ModbusSignedOperation(2600, "Grid L1 Power", 1, DBusUnit.W);
	public static final ModbusOperation GRID_L2_POWER = new ModbusSignedOperation(2601, "Grid L2 Power", 1, DBusUnit.W);
	public static final ModbusOperation GRID_L3_POWER = new ModbusSignedOperation(2602, "Grid L3 Power", 1, DBusUnit.W);
	// Victro Venus GX
	public static final ModbusOperation GRID_L1 = new ModbusSignedOperation(820, "Grid L1", 1, DBusUnit.W);
	public static final ModbusOperation GRID_L2 = new ModbusSignedOperation(821, "Grid L2", 1, DBusUnit.W);
	public static final ModbusOperation GRID_L3 = new ModbusSignedOperation(822, "Grid L3", 1, DBusUnit.W);

	private final int address;
	private final String description;
	private final double scaleFactor;
	private final DBusUnit dbusUnit;

	protected ModbusOperation(int address, String description, double scaleFactor, DBusUnit dbusUnit) {
		this.address = address;
		this.description = description;
		this.scaleFactor = scaleFactor;
		this.dbusUnit = dbusUnit;
	}

	protected double skaliereWert(Double registerWert) {
		return registerWert / this.scaleFactor;
	}

	/**
	 * Ermittle den Wert skaliert und an den Wertebereich der Operation angepasst.
	 */
	public abstract Double getSkaliertenWertInWertebreich(Integer registerWert);

	@Override
	public String toString() {
		return description;
	}

	public int getAddress() {
		return address;
	}

	public String getDescription() {
		return description;
	}

	public double getScaleFactor() {
		return scaleFactor;
	}

	public DBusUnit getDbusUnit() {
		return dbusUnit;
	}

}
