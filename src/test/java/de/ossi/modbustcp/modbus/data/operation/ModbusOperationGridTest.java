package de.ossi.modbustcp.modbus.data.operation;

import static de.ossi.modbustcp.modbus.data.operation.ModbusOperation.GRI_GRID_L1_ENERGY_TO_NET;
import static de.ossi.modbustcp.modbus.data.operation.ModbusOperation.GRI_GRID_L1_POWER;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ModbusOperationGridTest extends ModbusOperationTest{
	
	@Parameters(name = "{index}: Operation {0}; Registerwert={1}; Erwarteter Messwert={2}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { 
			/*GRI_GRID_L1_POWER: Signed, Scalefactor=1*/
			{ GRI_GRID_L1_POWER, MIN_UNSIGNED, d(MIN_UNSIGNED) }, 
			{ GRI_GRID_L1_POWER, MAX_SIGNED, d(MAX_SIGNED) },
			{ GRI_GRID_L1_POWER, MAX_SIGNED + 1, d(MIN_SIGNED) },
			{ GRI_GRID_L1_POWER, MAX_UNSIGNED, -1D },
			/*GRI_GRID_L1_ENERGY_TO_NET: Unsigned, Scalefactor=100*/
			{ GRI_GRID_L1_ENERGY_TO_NET, MIN_UNSIGNED, d(MIN_UNSIGNED) }, 
			{ GRI_GRID_L1_ENERGY_TO_NET, MAX_SIGNED, 327.67 },
			{ GRI_GRID_L1_ENERGY_TO_NET, MAX_SIGNED + 1, 327.68D },
			{ GRI_GRID_L1_ENERGY_TO_NET, MAX_UNSIGNED, 655.35 },
			});
	}

}
