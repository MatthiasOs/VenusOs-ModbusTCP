package de.ossi.modbustcp.data.operation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import de.ossi.modbustcp.data.unit.DBusUnit;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Unterstuetzte Modbus Operationen nach dem Excel Sheet:
 * files/CCGX-Modbus-TCP-register-list-2.12.xlsx Die RangeFrom und RangeTo
 * braucht man nicht, da man sich die Range mittels der Un-/Signed Range und dem
 * Scalefactor ausrechnen kann.
 * 
 * @author ossi
 *
 */
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public abstract class ModbusOperation implements Serializable{

	private static final long serialVersionUID = 1L;

	public enum AccessMode {
		READ_WRITE, READ_ONLY
	}

	public enum Category {
		VEBUS, BATTERY, SOLARCHARGER, SYSTEM, PVINVERTER, GRID, HUB4, SETTINGS, GENSET, TEMPERATURE
	}

	// >>>VEB_ = com.victronenergy.vebus >>>
	public static final ModbusOperation VEB_INPUT_VOLTAGE_PHASE_1 = new UnsignedOperation(Category.VEBUS, 3, "Input voltage phase 1", 10, DBusUnit.V_AC, AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_INPUT_CURRENT_PHASE_1 = new SignedOperation(Category.VEBUS, 6, "Input current phase 1", 10, DBusUnit.A_AC, AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_INPUT_FREQUENCY_1 = new SignedOperation(Category.VEBUS, 9, "Input frequency 1", 100, DBusUnit.HZ, AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_INPUT_POWER_1 = new SignedOperation(Category.VEBUS, 12, "Input power 1", 0.1, DBusUnit.VA, AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_OUTPUT_FREQUENCY = new SignedOperation(Category.VEBUS, 21, "Output frequency", 100, DBusUnit.HZ, AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_ACTIVE_INPUT_CURRENT_LIMIT = new SignedOperation(Category.VEBUS, 22, "Active input current limit", 10, DBusUnit.A,
			AccessMode.READ_WRITE);
	public static final ModbusOperation VEB_OUTPUT_POWER_1 = new SignedOperation(Category.VEBUS, 23, "Output power 1", 0.1, DBusUnit.VA, AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_BATTERY_VOLTAGE = new UnsignedOperation(Category.VEBUS, 26, "Battery voltage", 100, DBusUnit.V_DC, AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_BATTERY_CURRENT = new SignedOperation(Category.VEBUS, 27, "Battery current", 10, DBusUnit.A_DC, AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_ACTIVE_INPUT = new UnsignedOperation(Category.VEBUS, 29, "Active input", 1, DBusUnit.ACTIVE_INPUT, AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_VE_BUS_SOC = new UnsignedOperation(Category.VEBUS, 30, "VE.Bus state of charge", 10, DBusUnit.PERCENT, AccessMode.READ_WRITE);
	public static final ModbusOperation VEB_SWITCH_POSITION = new UnsignedOperation(Category.VEBUS, 33, "Switch Position", 1, DBusUnit.SWITCH_POSITION, AccessMode.READ_WRITE);
	public static final ModbusOperation VEB_TEMPREATURE_ALARM = new UnsignedOperation(Category.VEBUS, 34, "Temperature alarm", 1, DBusUnit.ALARM, AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_LOW_BATTERY_ALARM = new UnsignedOperation(Category.VEBUS, 35, "Low battery alarm", 1, DBusUnit.ALARM, AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_OVERLOAD_ALARM = new UnsignedOperation(Category.VEBUS, 36, "Overload alarm", 1, DBusUnit.ALARM, AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_ESS_POWER_SET_POINT_PHASE_1 = new SignedOperation(Category.VEBUS, 37, "ESS power setpoint phase 1", 1, DBusUnit.W,
			AccessMode.READ_WRITE);
	public static final ModbusOperation VEB_ESS_DISABLE_CHARGE_FLAG_PHASE = new SignedOperation(Category.VEBUS, 38, "ESS disable charge flag phase", 1, DBusUnit.CHARGE_FLAG,
			AccessMode.READ_WRITE);
	public static final ModbusOperation VEB_ESS_DISABLE_FEEDBACK_FLAG_PHASE = new SignedOperation(Category.VEBUS, 39, "ESS disable feedback flag phase", 1, DBusUnit.FEEDBACK_FLAG,
			AccessMode.READ_WRITE);
	public static final ModbusOperation VEB_ESS_POWER_SET_POINT_PHASE_2 = new SignedOperation(Category.VEBUS, 40, "ESS power setpoint phase 2", 1, DBusUnit.W,
			AccessMode.READ_WRITE);
	public static final ModbusOperation VEB_ESS_POWER_SET_POINT_PHASE_3 = new SignedOperation(Category.VEBUS, 41, "ESS power setpoint phase 3", 1, DBusUnit.W,
			AccessMode.READ_WRITE);
	public static final ModbusOperation VEB_TEMPREATURE_SENSOR_ALARM = new UnsignedOperation(Category.VEBUS, 42, "Temperatur sensor alarm", 1, DBusUnit.ALARM,
			AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_VOLTAGE_SENSOR_ALARM = new UnsignedOperation(Category.VEBUS, 43, "Voltage sensor alarm", 1, DBusUnit.ALARM, AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_TEMPREATURE_ALARM_L1 = new UnsignedOperation(Category.VEBUS, 44, "Temperature alarm L1", 1, DBusUnit.ALARM, AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_LOW_BATTERY_ALARM_L1 = new UnsignedOperation(Category.VEBUS, 45, "Low battery alarm L1", 1, DBusUnit.ALARM, AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_OVERLOAD_ALARM_L1 = new UnsignedOperation(Category.VEBUS, 46, "Overload alarm L1", 1, DBusUnit.ALARM, AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_RIPPLE_ALARM_L1 = new UnsignedOperation(Category.VEBUS, 47, "Ripple alarm L1", 1, DBusUnit.ALARM, AccessMode.READ_ONLY);
	public static final ModbusOperation VEB_DISABLE_PV_INVERTER = new UnsignedOperation(Category.VEBUS, 56, "Disable PV inverter", 1, DBusUnit.PV_STATUS, AccessMode.READ_WRITE);
	// <<<

	// >>>BAT_ = com.victronenergy.battery >>>
	public static final ModbusOperation BAT_BATTERY_VOLTAGE = new UnsignedOperation(Category.BATTERY, 259, "Battery voltage", 100, DBusUnit.V_DC, AccessMode.READ_ONLY);
	public static final ModbusOperation BAT_CURRENT = new SignedOperation(Category.BATTERY, 261, "Current", 10, DBusUnit.A_DC, AccessMode.READ_ONLY);
	public static final ModbusOperation BAT_BATTERY_TEMPERATURE = new SignedOperation(Category.BATTERY, 262, "Battery temperature", 10, DBusUnit.CELSIUS, AccessMode.READ_ONLY);
	public static final ModbusOperation BAT_STATE_OF_CHARGE = new UnsignedOperation(Category.BATTERY, 266, "State of charge", 10, DBusUnit.PERCENT, AccessMode.READ_ONLY);
	// <<<

	// >>>SOL_ = com.victronenergy.solarcharger >>>
	public static final ModbusOperation SOL_CHARGER_ON_OFF = new UnsignedOperation(Category.SOLARCHARGER, 774, "Charger on/off", 1, DBusUnit.CHARGER_ON_OFF, AccessMode.READ_WRITE);
	public static final ModbusOperation SOL_CHARGER_STATE = new UnsignedOperation(Category.SOLARCHARGER, 775, "Charge state", 1, DBusUnit.CHARGE_STATE, AccessMode.READ_ONLY);
	public static final ModbusOperation SOL_PV_VOLTAGE = new UnsignedOperation(Category.SOLARCHARGER, 776, "PV voltage", 100, DBusUnit.V_DC, AccessMode.READ_ONLY);
	public static final ModbusOperation SOL_PV_CURRENT = new SignedOperation(Category.SOLARCHARGER, 777, "PV current", 10, DBusUnit.A_DC, AccessMode.READ_ONLY);
	public static final ModbusOperation SOL_YIELD_TODAY = new UnsignedOperation(Category.SOLARCHARGER, 784, "Yield today", 10, DBusUnit.KWH, AccessMode.READ_ONLY);
	public static final ModbusOperation SOL_PV_POWER = new UnsignedOperation(Category.SOLARCHARGER, 789, "PV power", 10, DBusUnit.W, AccessMode.READ_ONLY);
	public static final ModbusOperation SOL_USER_YIELD = new UnsignedOperation(Category.SOLARCHARGER, 790, "User yield", 10, DBusUnit.KWH, AccessMode.READ_ONLY);
	// <<<

	// >>>SYS_ = com.victronenergy.system >>>
	// FIXME returns String
	// public static final ModbusOperation SYS_SERIAL_SYSTEM = new
	// StringOperation(Category.SYSTEM, 800, "Serial (System)", 6,
	// AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_CCGX_RELAY_1_STATE = new UnsignedOperation(Category.SYSTEM, 806, "CCGX Relay 1 state", 1, DBusUnit.RELAY_STATE, AccessMode.READ_WRITE);
	public static final ModbusOperation SYS_CCGX_RELAY_2_STATE = new UnsignedOperation(Category.SYSTEM, 807, "CCGX Relay 2 state", 1, DBusUnit.RELAY_STATE, AccessMode.READ_WRITE);
	public static final ModbusOperation SYS_PC_AC_COUPLED_OUTPUT_L1 = new UnsignedOperation(Category.SYSTEM, 808, "PV - AC-coupled on output L1", 1, DBusUnit.W,
			AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_PC_AC_COUPLED_OUTPUT_L2 = new UnsignedOperation(Category.SYSTEM, 809, "PV - AC-coupled on output L2", 1, DBusUnit.W,
			AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_PC_AC_COUPLED_OUTPUT_L3 = new UnsignedOperation(Category.SYSTEM, 810, "PV - AC-coupled on output L3", 1, DBusUnit.W,
			AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_PC_AC_COUPLED_INPUT_L1 = new UnsignedOperation(Category.SYSTEM, 811, "PV - AC-coupled on input L1", 1, DBusUnit.W,
			AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_PC_AC_COUPLED_INPUT_L2 = new UnsignedOperation(Category.SYSTEM, 812, "PV - AC-coupled on input L2", 1, DBusUnit.W,
			AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_PC_AC_COUPLED_INPUT_L3 = new UnsignedOperation(Category.SYSTEM, 813, "PV - AC-coupled on input L3", 1, DBusUnit.W,
			AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_AC_CONSUMPTION_L1 = new UnsignedOperation(Category.SYSTEM, 817, "AC Consumption L1", 1, DBusUnit.W, AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_AC_CONSUMPTION_L2 = new UnsignedOperation(Category.SYSTEM, 818, "AC Consumption L2", 1, DBusUnit.W, AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_AC_CONSUMPTION_L3 = new UnsignedOperation(Category.SYSTEM, 819, "AC Consumption L3", 1, DBusUnit.W, AccessMode.READ_ONLY);
	// Victro Venus GX
	public static final ModbusOperation SYS_GRID_L1 = new SignedOperation(Category.SYSTEM, 820, "Grid L1", 1, DBusUnit.W, AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_GRID_L2 = new SignedOperation(Category.SYSTEM, 821, "Grid L2", 1, DBusUnit.W, AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_GRID_L3 = new SignedOperation(Category.SYSTEM, 822, "Grid L3", 1, DBusUnit.W, AccessMode.READ_ONLY);

	public static final ModbusOperation SYS_ACTIVE_INPUT_SOURCE = new UnsignedOperation(Category.SYSTEM, 826, "Active input source", 1, DBusUnit.SOURCE, AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_BATTERY_VOLTAGE_SYSTEM = new UnsignedOperation(Category.SYSTEM, 840, "Battery Voltage (System)", 10, DBusUnit.V_DC,
			AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_BATTERY_CURRENT_SYSTEM = new SignedOperation(Category.SYSTEM, 841, "Battery Current (System)", 10, DBusUnit.V_DC, AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_BATTERY_POWER_SYSTEM = new SignedOperation(Category.SYSTEM, 842, "Battery Power (System)", 1, DBusUnit.W, AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_BATTERY_SOC_SYSTEM = new UnsignedOperation(Category.SYSTEM, 843, "Battery State of Charge (System)", 1, DBusUnit.PERCENT,
			AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_BATTERY_STATE_SYSTEM = new UnsignedOperation(Category.SYSTEM, 844, "Battery state (System)", 1, DBusUnit.BATTERY_STATE,
			AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_BATTERY_CONSUMED_SYSTEM = new UnsignedOperation(Category.SYSTEM, 845, "Battery Consumed Amphours (System)", -10, DBusUnit.AH,
			AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_BATTERY_TIME_TO_GO_SYSTEM = new UnsignedOperation(Category.SYSTEM, 846, "Battery Time to Go (System)", 0.01, DBusUnit.S,
			AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_PV_DC_COUPLED_POWER = new UnsignedOperation(Category.SYSTEM, 850, "PV - DC-coupled power", 1, DBusUnit.W, AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_PC_DC_COUPLED_CURRENT = new SignedOperation(Category.SYSTEM, 851, "PV - DC-coupled current", 10, DBusUnit.A_DC, AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_CHARGE_POWER = new UnsignedOperation(Category.SYSTEM, 855, "Charger power", 1, DBusUnit.W, AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_DC_SYSTEM_POWER = new SignedOperation(Category.SYSTEM, 860, "DC System Power", 1, DBusUnit.W, AccessMode.READ_ONLY);
	public static final ModbusOperation SYS_VE_BUS_CHARGE_POWER = new SignedOperation(Category.SYSTEM, 866, "VE.Bus charge power (System)", 1, DBusUnit.W, AccessMode.READ_ONLY);
	// <<<

	// >>> PVI_ = com.victronenergy.pvinverter >>>
	public static final ModbusOperation PVI_POSITION = new UnsignedOperation(Category.PVINVERTER, 1026, "Position", 1, DBusUnit.POSITION, AccessMode.READ_ONLY);
	public static final ModbusOperation PVI_L1_VOLTAGE = new UnsignedOperation(Category.PVINVERTER, 1027, "L1 Voltage", 10, DBusUnit.V_AC, AccessMode.READ_ONLY);
	public static final ModbusOperation PVI_L1_CURRENT = new SignedOperation(Category.PVINVERTER, 1028, "L1 Current", 10, DBusUnit.A_AC, AccessMode.READ_ONLY);
	public static final ModbusOperation PVI_L1_POWER = new UnsignedOperation(Category.PVINVERTER, 1029, "L1 Power", 1, DBusUnit.W, AccessMode.READ_ONLY);
	public static final ModbusOperation PVI_L1_ENERGY = new UnsignedOperation(Category.PVINVERTER, 1030, "L1 Energy", 100, DBusUnit.KWH, AccessMode.READ_ONLY);
	public static final ModbusOperation PVI_L2_VOLTAGE = new UnsignedOperation(Category.PVINVERTER, 1031, "L2 Voltage", 10, DBusUnit.V_AC, AccessMode.READ_ONLY);
	public static final ModbusOperation PVI_L2_CURRENT = new SignedOperation(Category.PVINVERTER, 1032, "L2 Current", 10, DBusUnit.A_AC, AccessMode.READ_ONLY);
	public static final ModbusOperation PVI_L2_POWER = new UnsignedOperation(Category.PVINVERTER, 1033, "L2 Power", 1, DBusUnit.W, AccessMode.READ_ONLY);
	public static final ModbusOperation PVI_L2_ENERGY = new UnsignedOperation(Category.PVINVERTER, 1034, "L2 Energy", 100, DBusUnit.KWH, AccessMode.READ_ONLY);
	public static final ModbusOperation PVI_L3_VOLTAGE = new UnsignedOperation(Category.PVINVERTER, 1035, "L3 Voltage", 10, DBusUnit.V_AC, AccessMode.READ_ONLY);
	public static final ModbusOperation PVI_L3_CURRENT = new SignedOperation(Category.PVINVERTER, 1036, "L3 Current", 10, DBusUnit.A_AC, AccessMode.READ_ONLY);
	public static final ModbusOperation PVI_L3_POWER = new UnsignedOperation(Category.PVINVERTER, 1037, "L3 Power", 1, DBusUnit.W, AccessMode.READ_ONLY);
	public static final ModbusOperation PVI_L3_ENERGY = new UnsignedOperation(Category.PVINVERTER, 1038, "L3 Energy", 100, DBusUnit.KWH, AccessMode.READ_ONLY);
	// FIXME returns String
	// public static final ModbusOperation PVI_SERIAL = new
	// StringOperation(Category.PVINVERTER, 1039, "Serial", 7,
	// AccessMode.READ_ONLY);
	// <<<

	// >>>GRI_ = com.victronenergy.grid >>>
	// Carlo Gavazzi Meter direct
	public static final ModbusOperation GRI_GRID_L1_POWER = new SignedOperation(Category.GRID, 2600, "Grid L1 Power", 1, DBusUnit.W, AccessMode.READ_ONLY);
	public static final ModbusOperation GRI_GRID_L2_POWER = new SignedOperation(Category.GRID, 2601, "Grid L2 Power", 1, DBusUnit.W, AccessMode.READ_ONLY);
	public static final ModbusOperation GRI_GRID_L3_POWER = new SignedOperation(Category.GRID, 2602, "Grid L3 Power", 1, DBusUnit.W, AccessMode.READ_ONLY);

	public static final ModbusOperation GRI_GRID_L1_ENERGY_FROM_NET = new UnsignedOperation(Category.GRID, 2603, "Grid L1 - Energy from net", 100, DBusUnit.KWH,
			AccessMode.READ_ONLY);
	public static final ModbusOperation GRI_GRID_L2_ENERGY_FROM_NET = new UnsignedOperation(Category.GRID, 2604, "Grid L2 - Energy from net", 100, DBusUnit.KWH,
			AccessMode.READ_ONLY);
	public static final ModbusOperation GRI_GRID_L3_ENERGY_FROM_NET = new UnsignedOperation(Category.GRID, 2605, "Grid L3 - Energy from net", 100, DBusUnit.KWH,
			AccessMode.READ_ONLY);
	public static final ModbusOperation GRI_GRID_L1_ENERGY_TO_NET = new UnsignedOperation(Category.GRID, 2606, "Grid L1 - Energy to net", 100, DBusUnit.KWH, AccessMode.READ_ONLY);
	public static final ModbusOperation GRI_GRID_L2_ENERGY_TO_NET = new UnsignedOperation(Category.GRID, 2607, "Grid L2 - Energy to net", 100, DBusUnit.KWH, AccessMode.READ_ONLY);
	public static final ModbusOperation GRI_GRID_L3_ENERGY_TO_NET = new UnsignedOperation(Category.GRID, 2608, "Grid L3 - Energy to net", 100, DBusUnit.KWH, AccessMode.READ_ONLY);
	// FIXME returns String
	// public static final ModbusOperation GRI_SERIAL = new
	// StringOperation(Category.GRID, 2609, "Serial", 7, AccessMode.READ_ONLY);
	public static final ModbusOperation GRI_GRID_L1_VOLTAGE = new UnsignedOperation(Category.GRID, 2616, "Grid L1 – Voltage", 10, DBusUnit.V_AC, AccessMode.READ_ONLY);
	public static final ModbusOperation GRI_GRID_L1_CURRENT = new SignedOperation(Category.GRID, 2617, "Grid L1 – Current", 10, DBusUnit.A_AC, AccessMode.READ_ONLY);
	public static final ModbusOperation GRI_GRID_L2_VOLTAGE = new UnsignedOperation(Category.GRID, 2618, "Grid L2 – Voltage", 10, DBusUnit.V_AC, AccessMode.READ_ONLY);
	public static final ModbusOperation GRI_GRID_L2_CURRENT = new SignedOperation(Category.GRID, 2619, "Grid L2 – Current", 10, DBusUnit.A_AC, AccessMode.READ_ONLY);
	public static final ModbusOperation GRI_GRID_L3_VOLTAGE = new UnsignedOperation(Category.GRID, 2620, "Grid L3 – Voltage", 10, DBusUnit.V_AC, AccessMode.READ_ONLY);
	public static final ModbusOperation GRI_GRID_L3_CURRENT = new SignedOperation(Category.GRID, 2621, "Grid L3 – Current", 10, DBusUnit.A_AC, AccessMode.READ_ONLY);
	// <<<

	// >>>HUB_ = com.victronenergy.hub4 >>>
	public static final ModbusOperation HUB_ESS_CONTROL_LOOP_SETPOINT = new SignedOperation(Category.HUB4, 2700, "ESS control loop setpoint", 1, DBusUnit.W, AccessMode.READ_WRITE);
	public static final ModbusOperation HUB_ESS_MAX_CHARGE_CURRENT = new UnsignedOperation(Category.HUB4, 2701, "ESS max charge current (fractional)", 1, DBusUnit.PERCENT,
			AccessMode.READ_WRITE);
	public static final ModbusOperation HUB_ESS_MAX_DISCHARGE_CURRENT = new UnsignedOperation(Category.HUB4, 2702, "ESS max discharge current (fractional)", 1, DBusUnit.PERCENT,
			AccessMode.READ_WRITE);
	public static final ModbusOperation HUB_ESS_CONTROL_LOOP_SETPOINT_2 = new SignedOperation(Category.HUB4, 2703, "ESS control loop setpoint", 0.01, DBusUnit.W,
			AccessMode.READ_WRITE);
	// <<<

	// >>>SET_ = com.victronenergy.settings
	public static final ModbusOperation SET_ESS_BATTERY_LIFE_STATE = new UnsignedOperation(Category.SETTINGS, 2900, "ESS BatteryLife state", 1, DBusUnit.BATTERYLIFE_STATE,
			AccessMode.READ_WRITE);
	public static final ModbusOperation SET_ESS_MINIMUM_SOC = new UnsignedOperation(Category.SETTINGS, 2901, "ESS Minimum SoC (unless grid fails)", 10, DBusUnit.PERCENT,
			AccessMode.READ_WRITE);

	// >>>GEN_ = com.victronenergy.genset
	public static final ModbusOperation GEN_START_GENERATOR = new UnsignedOperation(Category.GENSET, 3223, "Start generator", 1, DBusUnit.START, AccessMode.READ_WRITE);
	// <<<

	// TEM_ = com.victronenergy.temperature >>>
	public static final ModbusOperation TEM_TEMPERATURE = new SignedOperation(Category.TEMPERATURE, 3304, "Temperature", 100, DBusUnit.CELSIUS, AccessMode.READ_ONLY);
	// <<<

	/** letzter Teil des dbus-service name im Excel: com.victronenergy. */
	private final Category category;
	/** Address Spalte im Excel */
	private final int address;
	/** description Spalte im Excel */
	private final String description;
	/** Scalefactor Spalte im Excel */
	private final double scaleFactor;
	/** dbus-unit Spalte im Excel */
	private final DBusUnit dbusUnit;
	/** writable Spalte im Excel */
	private final AccessMode mode;

	public abstract Double getSkaliertenWertInWertebreich(Integer registerWert);

	protected double skaliereWert(Double registerWert) {
		return registerWert / this.scaleFactor;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(category).append(": ").append(description).append(" on register: ").append(address).toString();
	}

	public String getName() {
		return new StringBuilder().append(category).append(": ").append(description).toString();
	}

	public static List<ModbusOperation> allOperations() {
		return Arrays.asList(VEB_INPUT_VOLTAGE_PHASE_1, VEB_INPUT_CURRENT_PHASE_1, VEB_INPUT_FREQUENCY_1, VEB_INPUT_POWER_1, VEB_OUTPUT_FREQUENCY, VEB_ACTIVE_INPUT_CURRENT_LIMIT,
				VEB_OUTPUT_POWER_1, VEB_BATTERY_VOLTAGE, VEB_BATTERY_CURRENT, VEB_ACTIVE_INPUT, VEB_VE_BUS_SOC, VEB_SWITCH_POSITION, VEB_TEMPREATURE_ALARM, VEB_LOW_BATTERY_ALARM,
				VEB_OVERLOAD_ALARM, VEB_ESS_POWER_SET_POINT_PHASE_1, VEB_ESS_DISABLE_CHARGE_FLAG_PHASE, VEB_ESS_DISABLE_FEEDBACK_FLAG_PHASE, VEB_ESS_POWER_SET_POINT_PHASE_2,
				VEB_ESS_POWER_SET_POINT_PHASE_3, VEB_TEMPREATURE_SENSOR_ALARM, VEB_VOLTAGE_SENSOR_ALARM, VEB_TEMPREATURE_ALARM_L1, VEB_LOW_BATTERY_ALARM_L1, VEB_OVERLOAD_ALARM_L1,
				VEB_RIPPLE_ALARM_L1, VEB_DISABLE_PV_INVERTER, BAT_BATTERY_VOLTAGE, BAT_CURRENT, BAT_BATTERY_TEMPERATURE, BAT_STATE_OF_CHARGE, SOL_CHARGER_ON_OFF, SOL_CHARGER_STATE,
				SOL_PV_VOLTAGE, SOL_PV_CURRENT, SOL_YIELD_TODAY, SOL_PV_POWER, SOL_USER_YIELD, SYS_CCGX_RELAY_1_STATE, SYS_CCGX_RELAY_2_STATE, SYS_PC_AC_COUPLED_OUTPUT_L1,
				SYS_PC_AC_COUPLED_OUTPUT_L2, SYS_PC_AC_COUPLED_OUTPUT_L3, SYS_PC_AC_COUPLED_INPUT_L1, SYS_PC_AC_COUPLED_INPUT_L2, SYS_PC_AC_COUPLED_INPUT_L3, SYS_AC_CONSUMPTION_L1,
				SYS_AC_CONSUMPTION_L2, SYS_AC_CONSUMPTION_L3, SYS_GRID_L1, SYS_GRID_L2, SYS_GRID_L3, SYS_ACTIVE_INPUT_SOURCE, SYS_BATTERY_VOLTAGE_SYSTEM,
				SYS_BATTERY_CURRENT_SYSTEM, SYS_BATTERY_POWER_SYSTEM, SYS_BATTERY_SOC_SYSTEM, SYS_BATTERY_STATE_SYSTEM, SYS_BATTERY_CONSUMED_SYSTEM, SYS_BATTERY_TIME_TO_GO_SYSTEM,
				SYS_PV_DC_COUPLED_POWER, SYS_PC_DC_COUPLED_CURRENT, SYS_CHARGE_POWER, SYS_DC_SYSTEM_POWER, SYS_VE_BUS_CHARGE_POWER, PVI_POSITION, PVI_L1_VOLTAGE, PVI_L1_CURRENT,
				PVI_L1_POWER, PVI_L1_ENERGY, PVI_L2_VOLTAGE, PVI_L2_CURRENT, PVI_L2_POWER, PVI_L2_ENERGY, PVI_L3_VOLTAGE, PVI_L3_CURRENT, PVI_L3_POWER, PVI_L3_ENERGY,
				GRI_GRID_L1_POWER, GRI_GRID_L2_POWER, GRI_GRID_L3_POWER, GRI_GRID_L1_ENERGY_FROM_NET, GRI_GRID_L2_ENERGY_FROM_NET, GRI_GRID_L3_ENERGY_FROM_NET,
				GRI_GRID_L1_ENERGY_TO_NET, GRI_GRID_L2_ENERGY_TO_NET, GRI_GRID_L3_ENERGY_TO_NET, GRI_GRID_L1_VOLTAGE, GRI_GRID_L1_CURRENT, GRI_GRID_L2_VOLTAGE, GRI_GRID_L2_CURRENT,
				GRI_GRID_L3_VOLTAGE, GRI_GRID_L3_CURRENT, HUB_ESS_CONTROL_LOOP_SETPOINT, HUB_ESS_CONTROL_LOOP_SETPOINT_2, HUB_ESS_MAX_CHARGE_CURRENT, HUB_ESS_MAX_DISCHARGE_CURRENT,
				SET_ESS_BATTERY_LIFE_STATE, SET_ESS_MINIMUM_SOC, GEN_START_GENERATOR, TEM_TEMPERATURE);
	}
}
