package de.ossi.wolfsbau.modbus.data;

import static de.ossi.wolfsbau.modbus.data.ModbusOperation.BAT_BATTERY_TEMPERATURE;
import static de.ossi.wolfsbau.modbus.data.ModbusOperation.BAT_MID_POINT_VOLTAGE_BATTERY_BANK;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ModbusOperationBatteryTest extends ModbusOperationTest {

	@Parameters(name = "{index}: Operation {0}; Registerwert={1}; Erwarteter Messwert={2}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { 
			/*BAT_BATTERY_TEMPERATURE: Signed; Scalefactor=10*/
			{ BAT_BATTERY_TEMPERATURE, MAX_SIGNED + 1, -3276.8D }, 
			{ BAT_BATTERY_TEMPERATURE, 0, 0D }, 
			{ BAT_BATTERY_TEMPERATURE, MAX_SIGNED, 3276.7D }, 
			{ BAT_BATTERY_TEMPERATURE, MAX_UNSIGNED, -0.1D }, 
			/*BAT_MID_POINT_VOLTAGE_BATTERY_BANK: Unsigned; Scalefactor=100*/
			{ BAT_MID_POINT_VOLTAGE_BATTERY_BANK, MAX_SIGNED + 1, 327.68 }, 
			{ BAT_MID_POINT_VOLTAGE_BATTERY_BANK, 0, 0D }, 
			{ BAT_MID_POINT_VOLTAGE_BATTERY_BANK, MAX_SIGNED, 327.67D }, 
			{ BAT_MID_POINT_VOLTAGE_BATTERY_BANK, MAX_UNSIGNED, 655.35 }, 
			});
	}

}
