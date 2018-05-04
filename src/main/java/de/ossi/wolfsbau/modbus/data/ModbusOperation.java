package de.ossi.wolfsbau.modbus.data;

/**
 * Unterstuetzte Modbus Operationen nach dem Excel Sheet:
 * files/CCGX-Modbus-TCP-register-list-2.12.xlsx
 * 
 * @author ossi
 *
 */
public abstract class ModbusOperation {

	public static final ModbusOperation AC_CONSUMPTION_L1 = new ModbusUnsignedOperation(817, "AC Consumption L1", 1, DBusUnit.W);
	public static final ModbusOperation AC_CONSUMPTION_L2 = new ModbusUnsignedOperation(818, "AC Consumption L2", 1, DBusUnit.W);
	public static final ModbusOperation AC_CONSUMPTION_L3 = new ModbusUnsignedOperation(819, "AC Consumption L3", 1, DBusUnit.W);
	public static final ModbusOperation ACTIVE_INPUT_SOURCE = new ModbusUnsignedOperation(826, "Active input source", 1, DBusUnit.SOURCE);
	public static final ModbusOperation BATTERY_VOLTAGE_SYSTEM = new ModbusUnsignedOperation(840, "Battery Voltage (System)", 10, DBusUnit.V_DC);
	public static final ModbusOperation BATTERY_CURRENT_SYSTEM = new ModbusSignedOperation(841, "Battery Current (System)", 10,  DBusUnit.V_DC);
	public static final ModbusOperation BATTERY_POWER_SYSTEM = new ModbusSignedOperation(842, "Battery Power (System)", 1,  DBusUnit.W);
	public static final ModbusOperation BATTERY_STATE_OF_CHARGE_SYSTEM = new ModbusUnsignedOperation(843, "Battery State of Charge (System)", 1, DBusUnit.PERCENT);
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
