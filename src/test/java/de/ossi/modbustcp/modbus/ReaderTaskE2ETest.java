package de.ossi.modbustcp.modbus;

import static de.ossi.modbustcp.data.ModbusDevice.CAN_BUS_BMS;
import static de.ossi.modbustcp.data.ModbusDevice.CCGX_VE_DIRECT_1;
import static de.ossi.modbustcp.data.ModbusDevice.CCGX_VE_DIRECT_2;
import static de.ossi.modbustcp.data.operation.ModbusOperation.GRI_GRID_L1_CURRENT;
import static de.ossi.modbustcp.data.operation.ModbusOperation.GRI_GRID_L2_CURRENT;
import static de.ossi.modbustcp.data.operation.ModbusOperation.HUB_ESS_CONTROL_LOOP_SETPOINT;
import static java.time.Duration.ofMillis;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.ossi.modbustcp.connection.ModbusTCPReader;
import de.ossi.modbustcp.data.ModbusResultInt;
import de.ossi.modbustcp.db.InfluxDBModel;
import lombok.Getter;

/**
 * Test for manual E2E Tests
 * 
 * No Asserts! DONT ADD TO A TESTSUITE
 * 
 * @author ossi
 *
 */
public class ReaderTaskE2ETest {

	private static final String DBNAME = "wolfsbauTestDB";
	private static final LocalDateTime NOW = LocalDateTime.now();
	private static final ReadContainer CONT_1 = ReadContainer.builder().device(CCGX_VE_DIRECT_1).operation(GRI_GRID_L1_CURRENT).build();
	private static final ReadContainer CONT_2 = ReadContainer.builder().device(CCGX_VE_DIRECT_2).operation(GRI_GRID_L2_CURRENT).build();
	private static final ReadContainer CONT_3 = ReadContainer.builder().device(CAN_BUS_BMS).operation(HUB_ESS_CONTROL_LOOP_SETPOINT).build();
	private static final long PERIOD = 1000L;
	private final InfluxDBModel model = new InfluxDBModel("http://localhost:8086", DBNAME, "root", "root");
	private final ModbusTCPReader modbusReader = new ModbusTCPReader("192.168.0.81", 502);
	private final ModbusTCPReader modbusReaderMock = mock(ModbusTCPReader.class);
	private final Timer timer = new Timer();
	private ReaderTask modbusReaderTask;

	@Getter
	private final List<ReadContainer> readValues = new ArrayList<>();

	@AfterEach
	void cleanUp() {
		model.deleteDatabase(DBNAME);
	}

	@Test
	void e2eTest() throws Exception {
		modbusReaderTask = new ReaderTask(model, modbusReader, this::getReadValues);
		timer.schedule(modbusReaderTask, 0, PERIOD);
		Thread.sleep(PERIOD * 3);
		timer.cancel();
	}

	@Test
	void e2eTestMockedModbusTCPReader() throws Exception {
		modbusReaderTask = new ReaderTask(model, modbusReaderMock, this::getReadValues);
		timer.schedule(modbusReaderTask, 0, PERIOD);
		Thread.sleep(PERIOD * 3);
		timer.cancel();

	}

	@AfterEach
	void printAll() {
		model.writeAllPoints();
		System.err.println(model.getAllMeasurements());
	}

	@BeforeEach
	private void initTestData() throws Exception {
		readValues.add(CONT_1);
		readValues.add(CONT_2);
		readValues.add(CONT_3);
		given3ResultsForContainer(CONT_1, 1);
		given3ResultsForContainer(CONT_2, 2);
		given3ResultsForContainer(CONT_3, 3);
	}

	private void given3ResultsForContainer(ReadContainer c, int value) throws Exception {
		//Every Point musst have a differenz time, otherwise it will be overwritten
		ModbusResultInt result1 = ModbusResultInt.builder().operation(c.getOperation()).device(c.getDevice()).value(value).timestamp(NOW.plus(ofMillis(value))).build();
		ModbusResultInt result2 = ModbusResultInt.builder().operation(c.getOperation()).device(c.getDevice()).value(value).timestamp(NOW.plus(ofMillis(PERIOD + value))).build();
		ModbusResultInt result3 = ModbusResultInt.builder().operation(c.getOperation()).device(c.getDevice()).value(value).timestamp(NOW.plus(ofMillis((PERIOD + value) * 2)))
				.build();
		when(modbusReaderMock.readOperationFromDevice(c.getOperation(), c.getDevice())).thenReturn(result1).thenReturn(result2).thenReturn(result3);
	}

}
