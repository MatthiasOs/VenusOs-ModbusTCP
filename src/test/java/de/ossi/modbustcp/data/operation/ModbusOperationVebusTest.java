package de.ossi.modbustcp.data.operation;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ModbusOperationVebusTest extends ModbusOperationTest {

	@Parameters(name = "{index}: Operation {0}; Registerwert={1}; Erwarteter Messwert={2}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { 
			/*VEB_INPUT_POWER_1: Signed, Scalefactor=0.1*/
			{ VEB_INPUT_POWER_1, MIN_UNSIGNED, 0D }, 
			{ VEB_INPUT_POWER_1, MAX_SIGNED, 327670D },
			{ VEB_INPUT_POWER_1, MAX_SIGNED + 1, -327680D },
			{ VEB_INPUT_POWER_1, MAX_UNSIGNED, -10D },
			/*VEB_BATTERY_VOLTAGE: Unsigned, Scalefactor=100*/
			{ VEB_BATTERY_VOLTAGE, MIN_UNSIGNED, 0D }, 
			{ VEB_BATTERY_VOLTAGE, MAX_SIGNED, 327.67D },
			{ VEB_BATTERY_VOLTAGE, MAX_SIGNED + 1, 327.68D },
			{ VEB_BATTERY_VOLTAGE, MAX_UNSIGNED, 655.35D},
			});
	}

}
