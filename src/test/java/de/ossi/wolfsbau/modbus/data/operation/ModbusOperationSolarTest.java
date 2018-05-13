package de.ossi.wolfsbau.modbus.data.operation;

import static de.ossi.wolfsbau.modbus.data.operation.ModbusOperation.SOL_PV_CURRENT;
import static de.ossi.wolfsbau.modbus.data.operation.ModbusOperation.SOL_YIELD_TODAY;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ModbusOperationSolarTest extends ModbusOperationTest {

	@Parameters(name = "{index}: Operation {0}; Registerwert={1}; Erwarteter Messwert={2}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { 
			/*SOL_PV_CURRENT: Signed, Scalefactor=10*/
			{ SOL_PV_CURRENT, MIN_UNSIGNED, 0D }, 
			{ SOL_PV_CURRENT, MAX_SIGNED, 3276.7D },
			{ SOL_PV_CURRENT, MAX_SIGNED + 1, -3276.8D },
			{ SOL_PV_CURRENT, MAX_UNSIGNED, -0.1D },
			/*SOL_YIELD_TODAY: Unsigned, Scalefactor=10*/
			{ SOL_YIELD_TODAY, MIN_UNSIGNED, d(MIN_UNSIGNED) }, 
			{ SOL_YIELD_TODAY, MAX_SIGNED, 3276.7 },
			{ SOL_YIELD_TODAY, MAX_SIGNED + 1, 3276.8D },
			{ SOL_YIELD_TODAY, MAX_UNSIGNED, 6553.5 },
			});
	}

}
