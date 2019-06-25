package de.ossi.modbustcp.data;

import static de.ossi.modbustcp.data.ModbusDevice.CAN_BUS_BMS;
import static de.ossi.modbustcp.data.operation.ModbusOperation.GRI_GRID_L1_CURRENT;
import static de.ossi.modbustcp.data.operation.ModbusOperation.GRI_GRID_L1_VOLTAGE;
import static de.ossi.modbustcp.data.operation.ModbusOperation.SET_ESS_BATTERY_LIFE_STATE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("When ModbusResultInt")
public class ModbusResultIntTest {

	private ModbusResultInt modbusResult;

	@DisplayName("Parsing Special Unit")
	@Nested
	class ParsingSpecialUnit {
		@DisplayName("BATTERYLIFE_STATE")
		@Test
		void test1() throws Exception {
			modbusResult = new ModbusResultInt(SET_ESS_BATTERY_LIFE_STATE, CAN_BUS_BMS, 1);
			assertEquals("Restarting", modbusResult.getValueOfOperation());
		}
	}

	@DisplayName("Parsing with Unit V")
	@Nested
	class ParsingUnitV {
		@DisplayName("V_AC")
		@Test
		void test1() throws Exception {
			modbusResult = new ModbusResultInt(GRI_GRID_L1_VOLTAGE, CAN_BUS_BMS, 1);
			assertEquals("0.1 V AC", modbusResult.getValueOfOperationWithUnit());
		}
	}

	@DisplayName("Parsing with Unit")
	@Nested
	class ParsingUnitA {
		@DisplayName("A_AC")
		@Test
		void test1() throws Exception {
			modbusResult = new ModbusResultInt(GRI_GRID_L1_CURRENT, CAN_BUS_BMS, 1);
			assertEquals("0.1 A AC", modbusResult.getValueOfOperationWithUnit());
		}
	}

	@DisplayName("Parsing without Unit")
	@Nested
	class ParsingWithoutUnit {
		@Test
		void test1() throws Exception {
			modbusResult = new ModbusResultInt(GRI_GRID_L1_VOLTAGE, CAN_BUS_BMS, 1);
			assertEquals("0.1", modbusResult.getValueOfOperation());
		}
	}

}
