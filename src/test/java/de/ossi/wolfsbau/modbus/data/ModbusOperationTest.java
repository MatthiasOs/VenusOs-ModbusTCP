package de.ossi.wolfsbau.modbus.data;

import static de.ossi.wolfsbau.modbus.data.ModbusOperation.AC_CONSUMPTION_L1;
import static de.ossi.wolfsbau.modbus.data.ModbusOperation.BATTERY_CURRENT_SYSTEM;
import static de.ossi.wolfsbau.modbus.data.ModbusOperation.GRID_L1_POWER;
import static java.lang.Double.valueOf;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ModbusOperationTest {
	private static final int MAX_SIGNED = 32767;
	private static final int MIN_SIGNED = -32768;
	private static final int MAX_UNSIGNED = 65535;
	private static final int MIN_UNSIGNED = 0;

	@Parameters(name = "{index}: Operation {0}; Registerwert={1}; Erwarteter Messwert={2}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { 
			{ BATTERY_CURRENT_SYSTEM, MAX_SIGNED + 1, -3276.8D }, 
			{ GRID_L1_POWER, MIN_UNSIGNED, d(MIN_UNSIGNED) }, 
			{ GRID_L1_POWER, MAX_SIGNED, d(MAX_SIGNED) },
			{ GRID_L1_POWER, MAX_SIGNED + 1, d(MIN_SIGNED) },
			{ GRID_L1_POWER, MAX_UNSIGNED, -1D },
			{ AC_CONSUMPTION_L1, MIN_UNSIGNED, d(MIN_UNSIGNED) },
			{ AC_CONSUMPTION_L1, MAX_UNSIGNED, d(MAX_UNSIGNED) },
			{ AC_CONSUMPTION_L1, MAX_SIGNED + 1, d(MAX_SIGNED + 1) },
			});
	}

	@Parameter(0)
	public ModbusOperation operation;

	@Parameter(1)
	public Integer registerwert;

	@Parameter(2)
	public Double messwert;

	@Test
	public void pruefeDaten() throws Exception {
		ModbusResultInt result = new ModbusResultInt(operation, registerwert);
		assertThat(operation.getSkaliertenWertInWertebreich(result.getWert()), Matchers.is(valueOf(messwert)));
	}
	
	private static double d(Integer i) {
		return Double.valueOf(i);
	}

}
