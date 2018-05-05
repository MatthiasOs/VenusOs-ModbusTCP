package de.ossi.wolfsbau.modbus.data;

import de.ossi.wolfsbau.modbus.data.unit.DBusUnit;

/**
 * Unterstuetzte Modbus Operationen nach dem Excel Sheet:
 * files/CCGX-Modbus-TCP-register-list-2.12.xlsx Die RangeFrom und RangeTo
 * braucht man nicht, da man sich die Range mittels der Un-/Signed Range und dem
 * Scalefactor ausrechnen kann.
 * 
 * @author ossi
 *
 */
public abstract class ModbusOperation {

	// >>>VEB_ = com.victronenergy.vebus >>>
	public static final ModbusOperation VEB_INPUT_VOLTAGE_PHASE_1 = new UnsignedOperation(3, "Input voltage phase 1", 10, DBusUnit.V_AC);
	public static final ModbusOperation VEB_INPUT_CURRENT_PHASE_1 = new SignedOperation(6, "Input current phase 1", 10, DBusUnit.A_AC);
	public static final ModbusOperation VEB_INPUT_FREQUENCY_1 = new SignedOperation(9, "Input frequency 1", 100, DBusUnit.HZ);
	public static final ModbusOperation VEB_INPUT_POWER_1 = new SignedOperation(12, "Input power 1", 0.1, DBusUnit.VA_OR_WATTS);
	public static final ModbusOperation VEB_OUTPUT_FREQUENCY = new SignedOperation(21, "Output frequency", 100, DBusUnit.HZ);
	public static final ModbusOperation VEB_ACTIVE_INPUT_CURRENT_LIMIT = new SignedOperation(22, "Active input current limit", 10, DBusUnit.A);
	public static final ModbusOperation VEB_OUTPUT_POWER_1 = new SignedOperation(23, "Output power 1", 0.1, DBusUnit.VA_OR_WATTS);
	public static final ModbusOperation VEB_BATTERY_VOLTAGE = new UnsignedOperation(26, "Battery voltage", 100, DBusUnit.V_DC);
	public static final ModbusOperation VEB_BATTERY_CURRENT = new SignedOperation(27, "Battery current", 10, DBusUnit.A_DC);
	public static final ModbusOperation VEB_ACTIVE_INPUT = new UnsignedOperation(29, "Active input", 1, DBusUnit.ACTIVE_INPUT);
	public static final ModbusOperation VEB_VE_BUS_SOC = new UnsignedOperation(30, "VE.Bus state of charge", 10, DBusUnit.PERCENT);
	public static final ModbusOperation VEB_SWITCH_POSITION = new UnsignedOperation(33, "Switch Position", 1, DBusUnit.SWITCH_POSITION);
	public static final ModbusOperation VEB_TEMPREATURE_ALARM = new UnsignedOperation(34, "Temperature alarm", 1, DBusUnit.ALARM);
	public static final ModbusOperation VEB_LOW_BATTERY_ALARM = new UnsignedOperation(35, "Low battery alarm", 1, DBusUnit.ALARM);
	public static final ModbusOperation VEB_OVERLOAD_ALARM = new UnsignedOperation(36, "Overload alarm", 1, DBusUnit.ALARM);
	public static final ModbusOperation VEB_ESS_POWER_SET_POINT_PHASE_1 = new SignedOperation(37, "ESS power setpoint phase 1", 1, DBusUnit.W);
	public static final ModbusOperation VEB_ESS_DISABLE_CHARGE_FLAG_PHASE = new SignedOperation(38, "ESS disable charge flag phase", 1, DBusUnit.CHARGE_FLAG);
	public static final ModbusOperation VEB_ESS_DISABLE_FEEDBACK_FLAG_PHASE = new SignedOperation(39, "ESS disable feedback flag phase", 1, DBusUnit.FEEDBACK_FLAG);
	public static final ModbusOperation VEB_ESS_POWER_SET_POINT_PHASE_2 = new SignedOperation(40, "ESS power setpoint phase 2", 1, DBusUnit.W);
	public static final ModbusOperation VEB_ESS_POWER_SET_POINT_PHASE_3 = new SignedOperation(41, "ESS power setpoint phase 3", 1, DBusUnit.W);
	public static final ModbusOperation VEB_TEMPREATURE_SENSOR_ALARM = new UnsignedOperation(42, "Temperatur sensor alarm", 1, DBusUnit.ALARM);
	public static final ModbusOperation VEB_VOLTAGE_SENSOR_ALARM = new UnsignedOperation(43, "Voltage sensor alarm", 1, DBusUnit.ALARM);
	public static final ModbusOperation VEB_TEMPREATURE_ALARM_L1 = new UnsignedOperation(44, "Temperature alarm L1", 1, DBusUnit.ALARM);
	public static final ModbusOperation VEB_LOW_BATTERY_ALARM_L1 = new UnsignedOperation(45, "Low battery alarm L1", 1, DBusUnit.ALARM);
	public static final ModbusOperation VEB_OVERLOAD_ALARM_L1 = new UnsignedOperation(46, "Overload alarm L1", 1, DBusUnit.ALARM);
	public static final ModbusOperation VEB_RIPPLE_ALARM_L1 = new UnsignedOperation(47, "Ripple alarm L1", 1, DBusUnit.ALARM);
	public static final ModbusOperation VEB_DISABLE_PV_INVERTER = new UnsignedOperation(56, "Disable PV inverter", 1, DBusUnit.PV_STATUS);
	// <<<

	// >>>BAT_ = com.victronenergy.battery >>>
	public static final ModbusOperation BAT_BATTERY_VOLTAGE = new UnsignedOperation(259, "Battery voltage", 100, DBusUnit.V_DC);
	public static final ModbusOperation BAT_CURRENT = new SignedOperation(261, "Current", 10, DBusUnit.A_DC);
	public static final ModbusOperation BAT_BATTERY_TEMPERATURE = new SignedOperation(262, "Battery temperature", 10, DBusUnit.CELSIUS);
	public static final ModbusOperation BAT_MID_POINT_VOLTAGE_BATTERY_BANK = new UnsignedOperation(263, "Mid-point voltage of the battery bank", 100, DBusUnit.V_DC);
	public static final ModbusOperation BAT_MID_POINT_DEVIATION_BATTERY_BANK = new UnsignedOperation(264, "Mid-point deviation of the battery bank", 100, DBusUnit.PERCENT);
	public static final ModbusOperation BAT_STATE_OF_CHARGE = new UnsignedOperation(266, "State of charge", 10, DBusUnit.PERCENT);
	public static final ModbusOperation BAT_MIN_CELL_TEMPERATURE = new SignedOperation(318, "Minimum cell temperature", 10, DBusUnit.CELSIUS);
	public static final ModbusOperation BAT_MAX_CELL_TEMPERATURE = new SignedOperation(319, "Maximum cell temperature", 10, DBusUnit.CELSIUS);
	// <<<

	// >>>SOL_ = com.victronenergy.solarcharger >>>
	public static final ModbusOperation SOL_CHARGER_ON_OFF = new UnsignedOperation(774, "Charger on/off", 1, DBusUnit.CHARGER_ON_OFF);
	public static final ModbusOperation SOL_CHARGER_STATE = new UnsignedOperation(775, "Charge state", 1, DBusUnit.CHARGE_STATE);
	public static final ModbusOperation SOL_PV_VOLTAGE = new UnsignedOperation(776, "PV voltage", 100, DBusUnit.V_DC);
	public static final ModbusOperation SOL_PV_CURRENT = new SignedOperation(777, "PV current", 10, DBusUnit.A_DC);
	public static final ModbusOperation SOL_YIELD_TODAY = new UnsignedOperation(784, "Yield today", 10, DBusUnit.KWH);
	public static final ModbusOperation SOL_PV_POWER = new UnsignedOperation(789, "PV power", 10, DBusUnit.W);
	public static final ModbusOperation SOL_USER_YIELD = new UnsignedOperation(790, "User yield", 10, DBusUnit.KWH);
	// <<<

	// >>>SYS_ = com.victronenergy.system >>>
	// FIXME returns String
	// public static final ModbusOperation SYS_SERIAL_SYSTEM = new
	// UnsignedOperation(800, "Serial (System)", 1, null);
	public static final ModbusOperation SYS_CCGX_RELAY_1_STATE = new UnsignedOperation(806, "CCGX Relay 1 state", 1, DBusUnit.RELAY_STATE);
	public static final ModbusOperation SYS_CCGX_RELAY_2_STATE = new UnsignedOperation(807, "CCGX Relay 2 state", 1, DBusUnit.RELAY_STATE);
	public static final ModbusOperation SYS_PC_AC_COUPLED_OUTPUT_L1 = new UnsignedOperation(808, "PV - AC-coupled on output L1", 1, DBusUnit.W);
	public static final ModbusOperation SYS_PC_AC_COUPLED_OUTPUT_L2 = new UnsignedOperation(809, "PV - AC-coupled on output L2", 1, DBusUnit.W);
	public static final ModbusOperation SYS_PC_AC_COUPLED_OUTPUT_L3 = new UnsignedOperation(810, "PV - AC-coupled on output L3", 1, DBusUnit.W);
	public static final ModbusOperation SYS_PC_AC_COUPLED_INPUT_L1 = new UnsignedOperation(811, "PV - AC-coupled on input L1", 1, DBusUnit.W);
	public static final ModbusOperation SYS_PC_AC_COUPLED_INPUT_L2 = new UnsignedOperation(812, "PV - AC-coupled on input L2", 1, DBusUnit.W);
	public static final ModbusOperation SYS_PC_AC_COUPLED_INPUT_L3 = new UnsignedOperation(813, "PV - AC-coupled on input L3", 1, DBusUnit.W);
	public static final ModbusOperation SYS_AC_CONSUMPTION_L1 = new UnsignedOperation(817, "AC Consumption L1", 1, DBusUnit.W);
	public static final ModbusOperation SYS_AC_CONSUMPTION_L2 = new UnsignedOperation(818, "AC Consumption L2", 1, DBusUnit.W);
	public static final ModbusOperation SYS_AC_CONSUMPTION_L3 = new UnsignedOperation(819, "AC Consumption L3", 1, DBusUnit.W);
	// Victro Venus GX
	public static final ModbusOperation SYS_GRID_L1 = new SignedOperation(820, "Grid L1", 1, DBusUnit.W);
	public static final ModbusOperation SYS_GRID_L2 = new SignedOperation(821, "Grid L2", 1, DBusUnit.W);
	public static final ModbusOperation SYS_GRID_L3 = new SignedOperation(822, "Grid L3", 1, DBusUnit.W);

	public static final ModbusOperation SYS_ACTIVE_INPUT_SOURCE = new UnsignedOperation(826, "Active input source", 1, DBusUnit.SOURCE);
	public static final ModbusOperation SYS_BATTERY_VOLTAGE_SYSTEM = new UnsignedOperation(840, "Battery Voltage (System)", 10, DBusUnit.V_DC);
	public static final ModbusOperation SYS_BATTERY_CURRENT_SYSTEM = new SignedOperation(841, "Battery Current (System)", 10, DBusUnit.V_DC);
	public static final ModbusOperation SYS_BATTERY_POWER_SYSTEM = new SignedOperation(842, "Battery Power (System)", 1, DBusUnit.W);
	public static final ModbusOperation SYS_BATTERY_SOC_SYSTEM = new UnsignedOperation(843, "Battery State of Charge (System)", 1, DBusUnit.PERCENT);
	public static final ModbusOperation SYS_BATTERY_STATE_SYSTEM = new UnsignedOperation(844, "Battery state (System)", 1, DBusUnit.BATTERY_STATE);
	public static final ModbusOperation SYS_BATTERY_CONSUMED_SYSTEM = new UnsignedOperation(845, "Battery Consumed Amphours (System)", -10, DBusUnit.AH);
	public static final ModbusOperation SYS_BATTERY_TIME_TO_GO_SYSTEM = new UnsignedOperation(846, "Battery Time to Go (System)", 0.01, DBusUnit.S);
	public static final ModbusOperation SYS_PV_DC_COUPLED_POWER = new UnsignedOperation(850, "PV - DC-coupled power", 1, DBusUnit.W);
	public static final ModbusOperation SYS_PC_DC_COUPLED_CURRENT = new SignedOperation(851, "PV - DC-coupled current", 10, DBusUnit.A_DC);
	public static final ModbusOperation SYS_CHARGE_POWER = new UnsignedOperation(855, "Charger power", 1, DBusUnit.W);
	public static final ModbusOperation SYS_DC_SYSTEM_POWER = new SignedOperation(860, "DC System Power", 1, DBusUnit.W);
	public static final ModbusOperation SYS_VE_BUS_CHARGE_POWER = new SignedOperation(866, "VE.Bus charge power (System)", 1, DBusUnit.W);
	// <<<

	// >>>GRI_ = com.victronenergy.grid >>>
	// Carlo Gavazzi Meter direct
	public static final ModbusOperation GRI_GRID_L1_POWER = new SignedOperation(2600, "Grid L1 Power", 1, DBusUnit.W);
	public static final ModbusOperation GRI_GRID_L2_POWER = new SignedOperation(2601, "Grid L2 Power", 1, DBusUnit.W);
	public static final ModbusOperation GRI_GRID_L3_POWER = new SignedOperation(2602, "Grid L3 Power", 1, DBusUnit.W);

	public static final ModbusOperation GRI_GRID_L1_ENERGY_FROM_NET = new UnsignedOperation(2603, "Grid L1 - Energy from net", 100, DBusUnit.KWH);
	public static final ModbusOperation GRI_GRID_L2_ENERGY_FROM_NET = new UnsignedOperation(2604, "Grid L2 - Energy from net", 100, DBusUnit.KWH);
	public static final ModbusOperation GRI_GRID_L3_ENERGY_FROM_NET = new UnsignedOperation(2605, "Grid L3 - Energy from net", 100, DBusUnit.KWH);
	public static final ModbusOperation GRI_GRID_L1_ENERGY_TO_NET = new UnsignedOperation(2606, "Grid L1 - Energy to net", 100, DBusUnit.KWH);
	public static final ModbusOperation GRI_GRID_L2_ENERGY_TO_NET = new UnsignedOperation(2607, "Grid L2 - Energy to net", 100, DBusUnit.KWH);
	public static final ModbusOperation GRI_GRID_L3_ENERGY_TO_NET = new UnsignedOperation(2608, "Grid L3 - Energy to net", 100, DBusUnit.KWH);
	// FIXME Returns String
	// public static final ModbusOperation GRI_SERIAL = new
	// SignedOperation(2609, "Serial", 1, DBusUnit.W);
	public static final ModbusOperation GRI_GRID_L1_VOLTAGE = new UnsignedOperation(2616, "Grid L1 – Voltage", 10, DBusUnit.V_AC);
	public static final ModbusOperation GRI_GRID_L1_CURRENT = new SignedOperation(2617, "Grid L1 – Current", 10, DBusUnit.A_AC);
	public static final ModbusOperation GRI_GRID_L2_VOLTAGE = new UnsignedOperation(2618, "Grid L2 – Voltage", 10, DBusUnit.V_AC);
	public static final ModbusOperation GRI_GRID_L2_CURRENT = new SignedOperation(2619, "Grid L2 – Current", 10, DBusUnit.A_AC);
	public static final ModbusOperation GRI_GRID_L3_VOLTAGE = new UnsignedOperation(2620, "Grid L3 – Voltage", 10, DBusUnit.V_AC);
	public static final ModbusOperation GRI_GRID_L3_CURRENT = new SignedOperation(2621, "Grid L3 – Current", 10, DBusUnit.A_AC);
	// <<<

	// TEM_ = com.victronenergy.temperature >>>
	public static final ModbusOperation TEM_TEMPERATURE = new SignedOperation(3304, "Temperature", 100, DBusUnit.CELSIUS);
	// <<<

	/** Address Spalte im Excel */
	public final int address;
	/** description Spalte im Excel */
	public final String description;
	/** Scalefactor Spalte im Excel */
	public final double scaleFactor;
	/** dbus-unit Spalte im Excel */
	public final DBusUnit dbusUnit;

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
}
