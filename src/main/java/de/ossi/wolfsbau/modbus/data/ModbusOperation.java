package de.ossi.wolfsbau.modbus.data;

import org.apache.commons.lang3.Range;

public class ModbusOperation {

	public static final ModbusOperation AC_CONSUMPTION_L1 = new ModbusOperation(817, "AC Consumption L1", 1, DBusUnit.W, Range.between(0, 65336));
	public static final ModbusOperation AC_CONSUMPTION_L2 = new ModbusOperation(818, "AC Consumption L2", 1, DBusUnit.W, Range.between(0, 65336));
	public static final ModbusOperation AC_CONSUMPTION_L3 = new ModbusOperation(819, "AC Consumption L3", 1, DBusUnit.W, Range.between(0, 65336));
	public static final ModbusOperation BATTERY_STATE_OF_CHARGE = new ModbusOperation(843, "Battery State of Charge (System)", 1, DBusUnit.PERCENT, Range.between(0, 100));
	// Carlo Gavazzi Meter direct
	public static final ModbusOperation GRID_L1_POWER = new ModbusOperation(2600, "Grid L1 Power", 1, DBusUnit.W, Range.between(-32668, 32668));
	public static final ModbusOperation GRID_L2_POWER = new ModbusOperation(2601, "Grid L2 Power", 1, DBusUnit.W, Range.between(-32668, 32668));
	public static final ModbusOperation GRID_L3_POWER = new ModbusOperation(2602, "Grid L3 Power", 1, DBusUnit.W, Range.between(-32668, 32668));
	// Victro Venus GX
	public static final ModbusOperation GRID_L1 = new ModbusOperation(820, "Grid L1", 1, DBusUnit.W, Range.between(-32668, 32668));
	public static final ModbusOperation GRID_L2 = new ModbusOperation(821, "Grid L2", 1, DBusUnit.W, Range.between(-32668, 32668));
	public static final ModbusOperation GRID_L3 = new ModbusOperation(822, "Grid L3", 1, DBusUnit.W, Range.between(-32668, 32668));

	private final int address;
	private final String description;
	private final int scaleFactor;
	private final DBusUnit dbusUnit;
	private final Range<Integer> wertRange;

	private ModbusOperation(int address, String description, int scaleFactor, DBusUnit dbusUnit, Range<Integer> wertRange) {
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

	public int getScaleFactor() {
		return scaleFactor;
	}

	public DBusUnit getDbusUnit() {
		return dbusUnit;
	}

	public Range<Integer> getWertRange() {
		return wertRange;
	}
}
