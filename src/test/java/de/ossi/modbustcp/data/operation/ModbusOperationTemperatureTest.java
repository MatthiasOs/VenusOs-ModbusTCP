package de.ossi.modbustcp.data.operation;

import static de.ossi.modbustcp.data.operation.ModbusOperation.TEM_TEMPERATURE;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ModbusOperationTemperatureTest extends ModbusOperationTest {

	@Parameters(name = "{index}: Operation {0}; Registerwert={1}; Erwarteter Messwert={2}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { 
			/*TEM_TEMPERATURE: Signed, Scalefactor=100*/
			{ TEM_TEMPERATURE, MAX_SIGNED + 1, -327.68D }, 
			{ TEM_TEMPERATURE, 0, 0D }, 
			{ TEM_TEMPERATURE, MAX_SIGNED, 327.67D }, 
			{ TEM_TEMPERATURE, MAX_UNSIGNED, -0.01D }, 
			});
	}
}
