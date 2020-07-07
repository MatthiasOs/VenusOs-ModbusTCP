package de.ossi.modbustcp.data;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;

import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;

public class ExcelListReaderTest {
	private static final String OPERATIONS_DEVICES_FILENAME = "CCGX-Modbus-TCP-register-list-2.53.xlsx";
	private final ExcelListReader reader = new ExcelListReader(getClass().getClassLoader().getResource(OPERATIONS_DEVICES_FILENAME).getFile());

	@Test
	public void operationsShouldBeRead() throws Exception {
		List<ModbusOperation> operations = reader.readOperations();
		assertThat(operations, Matchers.not(Matchers.empty()));
	}

	@Test
	public void operationsShouldBeTheExpectedAmount() throws Exception {
		List<ModbusOperation> operations = reader.readOperations();
		assertThat(operations, hasSize(343));
	}

	@Test
	public void devicesShouldBeRead() throws Exception {
		List<ModbusDevice> devices = reader.readDevices();
		assertThat(devices, Matchers.not(Matchers.empty()));
	}

	@Test
	public void devicesShouldBeTheExpectedAmount() throws Exception {
		List<ModbusDevice> devices = reader.readDevices();
		assertThat(devices, hasSize(57));
	}

}
