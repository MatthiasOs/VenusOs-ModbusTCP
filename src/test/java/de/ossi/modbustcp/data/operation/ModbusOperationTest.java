package de.ossi.modbustcp.data.operation;

import static java.lang.Double.valueOf;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameter;

import de.ossi.modbustcp.data.ModbusResultInt;
import de.ossi.modbustcp.data.unit.AccessMode;
import de.ossi.modbustcp.data.unit.Category;
import de.ossi.modbustcp.data.unit.DBusUnit;
import de.ossi.modbustcp.data.unit.Type;

/**
 * Definiert die Tests für die ModbusOperationen. Die erbenden Tests müssen nur
 * die public static data Methode implementieren mit den Daten zum Testen. Man
 * kann sie die statische Methode nicht abstrakt machen.
 * 
 * @author ossi
 *
 */
public abstract class ModbusOperationTest {

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

	@Parameter(0)
	public ModbusOperation operation;

	@Parameter(1)
	public Integer registerwert;

	@Parameter(2)
	public Double messwert;

	@Test
	public void pruefeDaten() throws Exception {
		ModbusResultInt result = new ModbusResultInt(operation, CAN_BUS_BMS, registerwert);
		assertThat(operation.getValueInRange(result.getValue()), Matchers.is(valueOf(messwert)));
	}

	protected static double d(Integer i) {
		return Double.valueOf(i);
	}
}
