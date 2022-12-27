package de.ossi.modbustcp.data;


import java.util.List;


import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("Cant find Excel File in Resource Folder")
public class ExcelListReaderTest {
	private static final String OPERATIONS_DEVICES_FILENAME = "CCGX-Modbus-TCP-register-list-2.53.xlsx";
	private static ExcelListReader reader;

	@BeforeAll static void setUp(){
		reader = new ExcelListReader(ExcelListReaderTest.class.getClassLoader().getResource(OPERATIONS_DEVICES_FILENAME).getFile());
	}

	@Test
	public void operationsShouldBeRead() throws Exception {
		List<ModbusOperation> operations = reader.readOperations();
		assertThat(operations).isNotEmpty();
	}

	@Test
	public void operationsShouldBeTheExpectedAmount() throws Exception {
		List<ModbusOperation> operations = reader.readOperations();
		assertThat(operations).hasSize(343);
	}

	@Test
	public void devicesShouldBeRead() throws Exception {
		List<ModbusDevice> devices = reader.readDevices();
		assertThat(devices).isNotEmpty();
	}

	@Test
	public void devicesShouldBeTheExpectedAmount() throws Exception {
		List<ModbusDevice> devices = reader.readDevices();
		assertThat(devices).hasSize(57);
	}

}
