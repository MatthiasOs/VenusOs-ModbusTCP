package de.ossi.modbustcp.data;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import de.ossi.modbustcp.data.unit.AccessMode;
import de.ossi.modbustcp.data.unit.Category;
import de.ossi.modbustcp.data.unit.DBusUnit;
import de.ossi.modbustcp.data.unit.Type;

@DisplayName("When ModbusResultInt")
public class ModbusResultIntTest {

	private static final DBusUnit V_AC = new DBusUnit("V AC");
	private static final DBusUnit BATTERYLIFE_STATE = new DBusUnit("0=Unused, BL disabled;1=Restarting;2=Self-consumption;3=Self-consumption;4=Self-consumption;5=Discharge");
	private static final ModbusDevice CAN_BUS_BMS = new ModbusDevice(225, 512, "CAN-bus BMS");
	private static final ModbusOperation SET_ESS_BATTERY_LIFE_STATE = new ModbusOperation(Category.SETTINGS, "ESS BatteryLife state", 2900, Type.UINT16, 1D, "",
			"/Settings/CGwacs/BatteryLife/State", AccessMode.READ_WRITE, BATTERYLIFE_STATE, "");
	private static final ModbusOperation GRI_GRID_L1_VOLTAGE = new ModbusOperation(Category.GRID, "Grid L1 – Voltage", 2616, Type.UINT32, 10D, "", "/Ac/L1/Voltage",
			AccessMode.READ_ONLY, V_AC, "");
	private ModbusResultInt modbusResult;

	@DisplayName("Parsing Special Unit")
	@Nested
	class ParsingSpecialUnit {
		@DisplayName("BATTERYLIFE_STATE")
		@Test
		void test1() throws Exception {
			modbusResult = new ModbusResultInt(SET_ESS_BATTERY_LIFE_STATE, CAN_BUS_BMS, 1);
			assertThat(modbusResult.getValueOfOperationWithUnit(), startsWith("Restarting"));
		}
	}

	@DisplayName("Parsing Unit")
	@Nested
	class ParsingUnit {

		@DisplayName("V_AC")
		@Test
		void test1() throws Exception {
			modbusResult = new ModbusResultInt(GRI_GRID_L1_VOLTAGE, CAN_BUS_BMS, 1);
			assertThat(modbusResult.getValueOfOperationWithUnit(), is("0.1 V AC"));
		}
	}
}
