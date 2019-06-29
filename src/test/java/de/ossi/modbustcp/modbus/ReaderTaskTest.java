package de.ossi.modbustcp.modbus;

import static de.ossi.modbustcp.data.ModbusDevice.CAN_BUS_BMS;
import static de.ossi.modbustcp.data.ModbusDevice.CCGX_VE_DIRECT_1;
import static de.ossi.modbustcp.data.ModbusDevice.CCGX_VE_DIRECT_2;
import static de.ossi.modbustcp.data.operation.ModbusOperation.GRI_GRID_L1_CURRENT;
import static de.ossi.modbustcp.data.operation.ModbusOperation.GRI_GRID_L2_CURRENT;
import static de.ossi.modbustcp.data.operation.ModbusOperation.HUB_ESS_CONTROL_LOOP_SETPOINT;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.ossi.modbustcp.connection.ModbusTCPReader;
import de.ossi.modbustcp.data.ModbusDevice;
import de.ossi.modbustcp.data.ModbusResultInt;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import de.ossi.modbustcp.db.InfluxDBModel;
import lombok.Getter;

public class ReaderTaskTest {

	private static final LocalDateTime NOW = LocalDateTime.now();
	private static final ReadContainer CONT_1 = ReadContainer.builder().device(CCGX_VE_DIRECT_1).operation(GRI_GRID_L1_CURRENT).build();
	private static final ReadContainer CONT_2 = ReadContainer.builder().device(CCGX_VE_DIRECT_2).operation(GRI_GRID_L2_CURRENT).build();
	private static final ReadContainer CONT_3 = ReadContainer.builder().device(CAN_BUS_BMS).operation(HUB_ESS_CONTROL_LOOP_SETPOINT).build();
	private static final ModbusResultInt RES_1 = ModbusResultInt.builder().operation(CONT_1.getOperation()).device(CONT_1.getDevice()).value(1).timestamp(NOW).build();
	private static final ModbusResultInt RES_2 = ModbusResultInt.builder().operation(CONT_2.getOperation()).device(CONT_2.getDevice()).value(2).timestamp(NOW).build();
	private static final ModbusResultInt RES_3 = ModbusResultInt.builder().operation(CONT_3.getOperation()).device(CONT_3.getDevice()).value(3).timestamp(NOW).build();
	private ReaderTask task;
	private final ModbusTCPReader modbusReader = mock(ModbusTCPReader.class);
	private final InfluxDBModel model = mock(InfluxDBModel.class);
	@Getter
	private List<ReadContainer> container = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
		givenResultForContainer(CONT_1, RES_1);
		givenResultForContainer(CONT_2, RES_2);
		givenResultForContainer(CONT_3, RES_3);
	}

	private void givenResultForContainer(ReadContainer c, ModbusResultInt res) throws Exception {
		when(modbusReader.readOperationFromDevice(c.getOperation(), c.getDevice())).thenReturn(res);
	}

	@Test
	void taskShouldRead3TimesFor3ReadContainer() throws Exception {
		// given
		container.addAll(asList(CONT_1, CONT_2, CONT_3));
		// when
		task = new ReaderTask(model, modbusReader, this::getContainer);
		task.run();
		// then
		verify(modbusReader, times(3)).readOperationFromDevice(any(ModbusOperation.class), any(ModbusDevice.class));
	}

	@Test
	void taskShouldAdd2DBPointsFor2ReadContainer() throws Exception {
		// given
		container.addAll(asList(CONT_1, CONT_2));
		// when
		task = new ReaderTask(model, modbusReader, this::getContainer);
		task.run();
		// then
		verify(model, times(2)).addPoint(any(ModbusResultInt.class));
	}

	@Test
	void taskShouldCallReadForCorrectModbusDeviceAndOperation() throws Exception {
		// given
		container.addAll(asList(CONT_1, CONT_2));
		// when
		task = new ReaderTask(model, modbusReader, this::getContainer);
		task.run();
		// then
		verify(modbusReader).readOperationFromDevice(CONT_1.getOperation(), CONT_1.getDevice());
		verify(modbusReader).readOperationFromDevice(CONT_2.getOperation(), CONT_2.getDevice());
	}

	@Test
	void taskShouldAddPointsForCorrectModbusDeviceAndOperation() throws Exception {
		// given
		container.addAll(asList(CONT_1, CONT_2));
		// when
		task = new ReaderTask(model, modbusReader, this::getContainer);
		task.run();
		// then
		verify(model).addPoint(RES_1);
		verify(model).addPoint(RES_2);
	}

}
