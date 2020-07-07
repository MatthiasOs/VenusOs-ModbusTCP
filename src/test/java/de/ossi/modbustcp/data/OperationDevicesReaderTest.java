package de.ossi.modbustcp.data;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;

import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;

public class OperationDevicesReaderTest {

	private final OperationDevicesReader reader = new OperationDevicesReader();

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
