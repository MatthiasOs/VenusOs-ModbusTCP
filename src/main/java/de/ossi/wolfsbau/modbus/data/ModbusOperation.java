package de.ossi.wolfsbau.modbus.data;

/**
 * Unterstuetzte Modbus Operationen nach dem Excel Sheet:
 * CCGX-Modbus-TCP-register-list-2.12.xlsx
 * 
 * @author ossi
 *
 */
public class ModbusOperation {

	public enum Sign {
		SIGNED, UNSIGNED;
	}

	public static final ModbusOperation AC_CONSUMPTION_L1 = new ModbusOperation(817, "AC Consumption L1", 1, Sign.UNSIGNED, DBusUnit.W);
	public static final ModbusOperation AC_CONSUMPTION_L2 = new ModbusOperation(818, "AC Consumption L2", 1, Sign.UNSIGNED, DBusUnit.W);
	public static final ModbusOperation AC_CONSUMPTION_L3 = new ModbusOperation(819, "AC Consumption L3", 1, Sign.UNSIGNED, DBusUnit.W);
	public static final ModbusOperation ACTIVE_INPUT_SOURCE = new ModbusOperation(826, "Active input source", 1, Sign.UNSIGNED, DBusUnit.SOURCE);
	public static final ModbusOperation BATTERY_VOLTAGE_SYSTEM = new ModbusOperation(840, "Battery Voltage (System)", 10, Sign.UNSIGNED, DBusUnit.V_DC);
	public static final ModbusOperation BATTERY_CURRENT_SYSTEM = new ModbusOperation(841, "Battery Current (System)", 10, Sign.SIGNED, DBusUnit.V_DC);
	public static final ModbusOperation BATTERY_POWER_SYSTEM = new ModbusOperation(842, "Battery Power (System)", 1, Sign.SIGNED, DBusUnit.W);
	public static final ModbusOperation BATTERY_STATE_OF_CHARGE_SYSTEM = new ModbusOperation(843, "Battery State of Charge (System)", 1, Sign.UNSIGNED, DBusUnit.PERCENT);
	public static final ModbusOperation BATTERY_STATE_SYSTEM = new ModbusOperation(844, "Battery state (System)", 1, Sign.UNSIGNED, DBusUnit.BATTERY_STATE);
	public static final ModbusOperation BATTERY_CONSUMED_SYSTEM = new ModbusOperation(845, "Battery Consumed Amphours (System)", -10, Sign.UNSIGNED, DBusUnit.AH);
	public static final ModbusOperation BATTERY_TIME_TO_GO_SYSTEM = new ModbusOperation(846, "Battery Time to Go (System)", 0.01, Sign.UNSIGNED, DBusUnit.S);
	// Carlo Gavazzi Meter direct
	public static final ModbusOperation GRID_L1_POWER = new ModbusOperation(2600, "Grid L1 Power", 1, Sign.SIGNED, DBusUnit.W);
	public static final ModbusOperation GRID_L2_POWER = new ModbusOperation(2601, "Grid L2 Power", 1, Sign.SIGNED, DBusUnit.W);
	public static final ModbusOperation GRID_L3_POWER = new ModbusOperation(2602, "Grid L3 Power", 1, Sign.SIGNED, DBusUnit.W);
	// Victro Venus GX
	public static final ModbusOperation GRID_L1 = new ModbusOperation(820, "Grid L1", 1, Sign.SIGNED, DBusUnit.W);
	public static final ModbusOperation GRID_L2 = new ModbusOperation(821, "Grid L2", 1, Sign.SIGNED, DBusUnit.W);
	public static final ModbusOperation GRID_L3 = new ModbusOperation(822, "Grid L3", 1, Sign.SIGNED, DBusUnit.W);

	private final int address;
	private final String description;
	private final double scaleFactor;
	private final DBusUnit dbusUnit;
	private final Sign vorzeichen;

	private ModbusOperation(int address, String description, double scaleFactor, Sign vorzeichen, DBusUnit dbusUnit) {
		this.address = address;
		this.description = description;
		this.scaleFactor = scaleFactor;
		this.dbusUnit = dbusUnit;
		this.vorzeichen = vorzeichen;
	}

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

	public Sign getVorzeichen() {
		return vorzeichen;
	}

}
