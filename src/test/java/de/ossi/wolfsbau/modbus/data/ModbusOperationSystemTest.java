package de.ossi.wolfsbau.modbus.data;

import static de.ossi.wolfsbau.modbus.data.ModbusOperation.SYS_AC_CONSUMPTION_L1;
import static de.ossi.wolfsbau.modbus.data.ModbusOperation.SYS_BATTERY_CURRENT_SYSTEM;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ModbusOperationSystemTest extends ModbusOperationTest{

	@Parameters(name = "{index}: Operation {0}; Registerwert={1}; Erwarteter Messwert={2}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { 
			/*SYS_BATTERY_CURRENT_SYSTEM: Signed, Scalefactor=10*/
			{ SYS_BATTERY_CURRENT_SYSTEM, MIN_UNSIGNED, 0D }, 
			{ SYS_BATTERY_CURRENT_SYSTEM, MAX_SIGNED, 3276.7D },
			{ SYS_BATTERY_CURRENT_SYSTEM, MAX_SIGNED + 1, -3276.8D },
			{ SYS_BATTERY_CURRENT_SYSTEM, MAX_UNSIGNED, -0.1D },
			/*SYS_AC_CONSUMPTION_L1: Unsigned, Scalefactor=1*/
			{ SYS_AC_CONSUMPTION_L1, MIN_UNSIGNED, d(MIN_UNSIGNED) }, 
			{ SYS_AC_CONSUMPTION_L1, MAX_SIGNED, d(MAX_SIGNED) },
			{ SYS_AC_CONSUMPTION_L1, MAX_SIGNED + 1, d(MAX_SIGNED+1) },
			{ SYS_AC_CONSUMPTION_L1, MAX_UNSIGNED, d(MAX_UNSIGNED)},
			});
	}
}
