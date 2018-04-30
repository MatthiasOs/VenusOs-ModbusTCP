package de.ossi.wolfsbau.modbus.data;

import org.apache.commons.lang3.Range;

/**
 * Unterstuetzte Modbus Operationen nach dem Excel Sheet:
 * CCGX-Modbus-TCP-register-list-2.12.xlsx 
 * @author ossi
 *
 */
public class ModbusOperation {
	
	private static final double MIN_SIGNED = -32768D;
	private static final double MAX_SIGNED = 32767D;
	private static final double MIN_UNSIGNED = 0D;
	private static final double MAX_UNSIGNED = 65335D;
	public static final ModbusOperation AC_CONSUMPTION_L1 = new ModbusOperation(817, "AC Consumption L1", 1, DBusUnit.W, Range.between(MIN_UNSIGNED, MAX_UNSIGNED));
	public static final ModbusOperation AC_CONSUMPTION_L2 = new ModbusOperation(818, "AC Consumption L2", 1, DBusUnit.W, Range.between(MIN_UNSIGNED, MAX_UNSIGNED));
	public static final ModbusOperation AC_CONSUMPTION_L3 = new ModbusOperation(819, "AC Consumption L3", 1, DBusUnit.W, Range.between(MIN_UNSIGNED, MAX_UNSIGNED));
	public static final ModbusOperation ACTIVE_INPUT_SOURCE = new ModbusOperation(826, "Active input source", 1, DBusUnit.SOURCE, Range.between(MIN_UNSIGNED, MAX_SIGNED));
	public static final ModbusOperation BATTERY_VOLTAGE_SYSTEM = new ModbusOperation(840, "Battery Voltage (System)", 10, DBusUnit.V_DC, Range.between(0D, 6533.6D));
	public static final ModbusOperation BATTERY_CURRENT_SYSTEM = new ModbusOperation(841, "Battery Current (System)", 10, DBusUnit.V_DC, Range.between(-3276.8D, 3276.7D));
	public static final ModbusOperation BATTERY_POWER_SYSTEM = new ModbusOperation(842, "Battery Power (System)", 1, DBusUnit.W, Range.between(MIN_SIGNED, MAX_SIGNED));
	public static final ModbusOperation BATTERY_STATE_OF_CHARGE_SYSTEM = new ModbusOperation(843, "Battery State of Charge (System)", 1, DBusUnit.PERCENT, Range.between(0D, 100D));
	public static final ModbusOperation BATTERY_STATE_SYSTEM = new ModbusOperation(844, "Battery state (System)", 1, DBusUnit.BATTERY_STATE, Range.between(MIN_UNSIGNED, MAX_UNSIGNED));
	public static final ModbusOperation BATTERY_CONSUMED_SYSTEM = new ModbusOperation(845, "Battery Consumed Amphours (System)", -10, DBusUnit.AH, Range.between(-6533.6, 0D)); 
	public static final ModbusOperation BATTERY_TIME_TO_GO_SYSTEM = new ModbusOperation(846, "Battery Time to Go (System)", 0.01, DBusUnit.S, Range.between(0D, 6533600D));
	// Carlo Gavazzi Meter direct
	public static final ModbusOperation GRID_L1_POWER = new ModbusOperation(2600, "Grid L1 Power", 1, DBusUnit.W, Range.between(MIN_SIGNED, MAX_SIGNED));
	public static final ModbusOperation GRID_L2_POWER = new ModbusOperation(2601, "Grid L2 Power", 1, DBusUnit.W, Range.between(MIN_SIGNED, MAX_SIGNED));
	public static final ModbusOperation GRID_L3_POWER = new ModbusOperation(2602, "Grid L3 Power", 1, DBusUnit.W, Range.between(MIN_SIGNED, MAX_SIGNED));
	// Victro Venus GX
	public static final ModbusOperation GRID_L1 = new ModbusOperation(820, "Grid L1", 1, DBusUnit.W, Range.between(MIN_SIGNED, MAX_SIGNED));
	public static final ModbusOperation GRID_L2 = new ModbusOperation(821, "Grid L2", 1, DBusUnit.W, Range.between(MIN_SIGNED, MAX_SIGNED));
	public static final ModbusOperation GRID_L3 = new ModbusOperation(822, "Grid L3", 1, DBusUnit.W, Range.between(MIN_SIGNED, MAX_SIGNED));

	private final int address;
	private final String description;
	private final double scaleFactor;
	private final DBusUnit dbusUnit;
	/**Die erwarteten Werte */
	private final Range<Double> wertRange;

	private ModbusOperation(int address, String description, double scaleFactor, DBusUnit dbusUnit, Range<Double> wertRange) {
		this.address = address;
		this.description = description;
		this.scaleFactor = scaleFactor;
		this.dbusUnit = dbusUnit;
		this.wertRange = wertRange;
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

	public Range<Double> getWertRange() {
		return wertRange;
	}
}
