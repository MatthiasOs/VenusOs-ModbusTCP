package de.ossi.modbustcp.data.operation;

import static java.util.stream.Stream.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.ossi.modbustcp.data.ModbusResultInt;
import de.ossi.modbustcp.data.unit.AccessMode;
import de.ossi.modbustcp.data.unit.Category;
import de.ossi.modbustcp.data.unit.DBusUnit;
import de.ossi.modbustcp.data.unit.Type;

import java.util.stream.Stream;

public class ModbusOperationTest {

	protected static final int MAX_SIGNED = 32767;
	protected static final int MIN_SIGNED = -32768;
	protected static final int MAX_UNSIGNED = 65535;
	protected static final int MIN_UNSIGNED = 0;
	protected static final DBusUnit V_DC = new DBusUnit("V DC");
	protected static final DBusUnit CELSIUS = new DBusUnit("Degrees celsius");
	protected static final DBusUnit W = new DBusUnit("W");
	protected static final DBusUnit KWH = new DBusUnit("kWh");
	protected static final DBusUnit A_DC = new DBusUnit("A DC");
	protected static final DBusUnit VA = new DBusUnit("VA");
	protected static final ModbusDevice CAN_BUS_BMS = new ModbusDevice(225, 512, "CAN-bus BMS");
	protected static final ModbusOperation BAT_BATTERY_TEMPERATURE = new ModbusOperation(Category.BATTERY, "Battery temperature", 262, Type.INT16, 10D, "", "/Dc/0/Temperature",
			AccessMode.READ_ONLY, CELSIUS, "");
	protected static final ModbusOperation BAT_BATTERY_VOLTAGE = new ModbusOperation(Category.BATTERY, "Battery voltage", 259, Type.UINT16, 100D, "", "/Dc/0/Voltage",
			AccessMode.READ_ONLY, V_DC, "");
	protected static final ModbusOperation GRI_GRID_L1_POWER = new ModbusOperation(Category.GRID, "Grid L1 Power", 2600, Type.INT16, 1D, "", "/Ac/L1/Power", AccessMode.READ_ONLY,
			W, "");
	protected static final ModbusOperation GRI_GRID_L1_ENERGY_TO_NET = new ModbusOperation(Category.GRID, "Grid L1 - Energy to net", 2606, Type.UINT16, 100D, "",
			"/Ac/L1/Energy/Reverse", AccessMode.READ_ONLY, KWH, "");
	protected static final ModbusOperation SOL_PV_CURRENT = new ModbusOperation(Category.SOLARCHARGER, "PV current", 777, Type.INT16, 10D, "", "/Pv/I", AccessMode.READ_ONLY, A_DC,
			"");
	protected static final ModbusOperation SOL_YIELD_TODAY = new ModbusOperation(Category.SOLARCHARGER, "Yield today", 784, Type.UINT16, 10D, "", "/History/Daily/0/Yield",
			AccessMode.READ_ONLY, KWH, "");
	protected static final ModbusOperation SYS_BATTERY_CURRENT_SYSTEM = new ModbusOperation(Category.SYSTEM, "Battery Current (System)", 841, Type.INT16, 10D, "",
			"/Dc/Battery/Current", AccessMode.READ_ONLY, V_DC, "");
	protected static final ModbusOperation SYS_AC_CONSUMPTION_L1 = new ModbusOperation(Category.SYSTEM, "AC Consumption L1", 817, Type.UINT16, 1D, "", "/Ac/Consumption/L1/Power",
			AccessMode.READ_ONLY, W, "");
	protected static final ModbusOperation VEB_INPUT_POWER_1 = new ModbusOperation(Category.VEBUS, "Input power 1", 12, Type.INT16, 0.1D, "", "/Ac/ActiveIn/L1/P",
			AccessMode.READ_ONLY, VA, "");
	protected static final ModbusOperation VEB_BATTERY_VOLTAGE = new ModbusOperation(Category.VEBUS, "Battery voltage", 26, Type.UINT16, 100D, "", "/Dc/0/Voltage",
			AccessMode.READ_ONLY, V_DC, "");
	protected static final ModbusOperation TEM_TEMPERATURE = new ModbusOperation(Category.TEMPERATURE, "Temperature", 3304, Type.INT16, 100D, "", "/Temperature",
			AccessMode.READ_ONLY, CELSIUS, "");

	@ParameterizedTest
	@MethodSource({"battery", "grid","solar", "system", "temp", "veBus"})
	public void checkValue(ModbusOperation operation, Integer registerValue, Double value) throws Exception {
		ModbusResultInt result = new ModbusResultInt(operation, CAN_BUS_BMS, registerValue);
		assertThat(result.getValueOfOperationWithUnit()).isEqualTo(value + " " + result.getOperation().getDbusUnit().getValue());
	}

	public static Stream<Arguments> battery() {
		return of(
				/*BAT_BATTERY_TEMPERATURE: Signed; Scalefactor=10*/
				arguments(BAT_BATTERY_TEMPERATURE, MAX_SIGNED + 1, -3276.8D ),
				arguments(BAT_BATTERY_TEMPERATURE, 0, 0D ),
				arguments(BAT_BATTERY_TEMPERATURE, MAX_SIGNED, 3276.7D ),
				arguments(BAT_BATTERY_TEMPERATURE, MAX_UNSIGNED, -0.1D ),
				/*BAT_BATTERY_VOLTAGE: Unsigned; Scalefactor=100*/
				arguments(BAT_BATTERY_VOLTAGE, MAX_SIGNED + 1, 327.68 ),
				arguments(BAT_BATTERY_VOLTAGE, 0, 0D ),
				arguments(BAT_BATTERY_VOLTAGE, MAX_SIGNED, 327.67D ),
				arguments(BAT_BATTERY_VOLTAGE, MAX_UNSIGNED, 655.35 )
		);
	}

	public static Stream<Arguments> grid() {
		return of(
				/*GRI_GRID_L1_POWER: Signed, Scalefactor=1*/
				arguments(GRI_GRID_L1_POWER, MIN_UNSIGNED, d(MIN_UNSIGNED) ),
				arguments(GRI_GRID_L1_POWER, MAX_SIGNED, d(MAX_SIGNED) ),
				arguments(GRI_GRID_L1_POWER, MAX_SIGNED + 1, d(MIN_SIGNED) ),
				arguments(GRI_GRID_L1_POWER, MAX_UNSIGNED, -1D ),
				/*GRI_GRID_L1_ENERGY_TO_NET: Unsigned, Scalefactor=100*/
				arguments(GRI_GRID_L1_ENERGY_TO_NET, MIN_UNSIGNED, d(MIN_UNSIGNED) ),
				arguments(GRI_GRID_L1_ENERGY_TO_NET, MAX_SIGNED, 327.67 ),
				arguments(GRI_GRID_L1_ENERGY_TO_NET, MAX_SIGNED + 1, 327.68D ),
				arguments(GRI_GRID_L1_ENERGY_TO_NET, MAX_UNSIGNED, 655.35 )
		);
	}

	public static Stream<Arguments> solar() {
		return of(
				/*SOL_PV_CURRENT: Signed, Scalefactor=10*/
				arguments(SOL_PV_CURRENT, MIN_UNSIGNED, 0D ),
				arguments(SOL_PV_CURRENT, MAX_SIGNED, 3276.7D ),
				arguments(SOL_PV_CURRENT, MAX_SIGNED + 1, -3276.8D ),
				arguments(SOL_PV_CURRENT, MAX_UNSIGNED, -0.1D ),
				/*SOL_YIELD_TODAY: Unsigned, Scalefactor=10*/
				arguments(SOL_YIELD_TODAY, MIN_UNSIGNED, d(MIN_UNSIGNED) ),
				arguments(SOL_YIELD_TODAY, MAX_SIGNED, 3276.7 ),
				arguments(SOL_YIELD_TODAY, MAX_SIGNED + 1, 3276.8D ),
				arguments(SOL_YIELD_TODAY, MAX_UNSIGNED, 6553.5 )
		);
	}

	public static Stream<Arguments> system() {
		return of(
				/*SYS_BATTERY_CURRENT_SYSTEM: Signed, Scalefactor=10*/
				arguments(SYS_BATTERY_CURRENT_SYSTEM, MIN_UNSIGNED, 0D ),
				arguments(SYS_BATTERY_CURRENT_SYSTEM, MAX_SIGNED, 3276.7D ),
				arguments(SYS_BATTERY_CURRENT_SYSTEM, MAX_SIGNED + 1, -3276.8D ),
				arguments(SYS_BATTERY_CURRENT_SYSTEM, MAX_UNSIGNED, -0.1D ),
				/*SYS_AC_CONSUMPTION_L1: Unsigned, Scalefactor=1*/
				arguments(SYS_AC_CONSUMPTION_L1, MIN_UNSIGNED, d(MIN_UNSIGNED) ),
				arguments(SYS_AC_CONSUMPTION_L1, MAX_SIGNED, d(MAX_SIGNED) ),
				arguments(SYS_AC_CONSUMPTION_L1, MAX_SIGNED + 1, d(MAX_SIGNED+1) ),
				arguments(SYS_AC_CONSUMPTION_L1, MAX_UNSIGNED, d(MAX_UNSIGNED))
		);
	}

	public static Stream<Arguments> temp() {
		return of(
				/*TEM_TEMPERATURE: Signed, Scalefactor=100*/
				arguments(TEM_TEMPERATURE, MAX_SIGNED + 1, -327.68D ),
				arguments(TEM_TEMPERATURE, 0, 0D ),
				arguments(TEM_TEMPERATURE, MAX_SIGNED, 327.67D ),
				arguments(TEM_TEMPERATURE, MAX_UNSIGNED, -0.01D )
		);
	}

	public static Stream<Arguments> veBus() {
		return of(
				/*VEB_INPUT_POWER_1: Signed, Scalefactor=0.1*/
				arguments(VEB_INPUT_POWER_1, MIN_UNSIGNED, 0D ),
				arguments(VEB_INPUT_POWER_1, MAX_SIGNED, 327670D ),
				arguments(VEB_INPUT_POWER_1, MAX_SIGNED + 1, -327680D ),
				arguments(VEB_INPUT_POWER_1, MAX_UNSIGNED, -10D ),
				/*VEB_BATTERY_VOLTAGE: Unsigned, Scalefactor=100*/
				arguments(VEB_BATTERY_VOLTAGE, MIN_UNSIGNED, 0D ),
				arguments(VEB_BATTERY_VOLTAGE, MAX_SIGNED, 327.67D ),
				arguments(VEB_BATTERY_VOLTAGE, MAX_SIGNED + 1, 327.68D ),
				arguments(VEB_BATTERY_VOLTAGE, MAX_UNSIGNED, 655.35D)
		);
	}

	protected static double d(Integer i) {
		return Double.valueOf(i);
	}
}
