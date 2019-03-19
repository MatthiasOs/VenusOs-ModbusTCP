package de.ossi.modbustcp.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.ossi.modbustcp.data.operation.ModbusOperation;

@DisplayName("When ModbusResultInt")
public class ModbusResultIntTest {

	private ModbusResultInt modbusResult;

	@DisplayName("Parsing Special Unit")
	@Nested
	class ParsingSpecialUnit {
		@DisplayName("BATTERYLIFE_STATE")
		@Test
		void test1() throws Exception {
			modbusResult = new ModbusResultInt(ModbusOperation.SET_ESS_BATTERY_LIFE_STATE, 1);
			assertEquals("Restarting", modbusResult.ermittleWertMitEinheit());
		}
	}

	@DisplayName("Parsing Unit")
	@Nested
	class ParsingUnit {

		@DisplayName("V_AC")
		@Test
		void test1() throws Exception {
			modbusResult = new ModbusResultInt(ModbusOperation.GRI_GRID_L1_VOLTAGE, 1);
			assertEquals("0.1 V AC", modbusResult.ermittleWertMitEinheit());
		}

	}

}
