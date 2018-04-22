package de.ossi.wolfsbau.modbus.data;

public class ModbusOperation {

	public static final ModbusOperation AC_CONSUMPTION_L1 = new ModbusOperation(817, "AC Consumption L1", 1, DBusUnit.W);
	public static final ModbusOperation AC_CONSUMPTION_L2 = new ModbusOperation(818, "AC Consumption L2", 1, DBusUnit.W);
	public static final ModbusOperation AC_CONSUMPTION_L3 = new ModbusOperation(819, "AC Consumption L3", 1, DBusUnit.W);
	public static final ModbusOperation BATTERY_STATE_OF_CHARGE = new ModbusOperation(843, "Battery State of Charge (System)", 1, DBusUnit.PERCENT);
	//Carlo Gavazzi Meter direct
	public static final ModbusOperation GRID_L1_POWER = new ModbusOperation(2600, "Grid L1 Power", 1, DBusUnit.W);
	public static final ModbusOperation GRID_L2_POWER = new ModbusOperation(2601, "Grid L2 Power", 1, DBusUnit.W);
	public static final ModbusOperation GRID_L3_POWER = new ModbusOperation(2602, "Grid L3 Power", 1, DBusUnit.W);
	//Victro Venus GX
	public static final ModbusOperation GRID_L1 = new ModbusOperation(820, "Grid L1", 1, DBusUnit.W);
	public static final ModbusOperation GRID_L2 = new ModbusOperation(821, "Grid L2", 1, DBusUnit.W);
	public static final ModbusOperation GRID_L3 = new ModbusOperation(822, "Grid L3", 1, DBusUnit.W);

	private final int address;
	private final String description;
	private final int scaleFactor;
	private final DBusUnit dbusUnit;

	private ModbusOperation(final int address, final String description, final int scaleFactor, DBusUnit dbusUnit) {
		this.address = address;
		this.description = description;
		this.scaleFactor = scaleFactor;
		this.dbusUnit = dbusUnit;
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

}
