package de.ossi.modbustcp.data;


import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ExcelListReaderTest {
    private static final String OPERATIONS_DEVICES_FILENAME = "CCGX-Modbus-TCP-register-list-2.53.xlsx";
    private final ExcelListReader reader = new ExcelListReader(OPERATIONS_DEVICES_FILENAME);

    @Test
    public void operationsShouldBeRead() {
        List<ModbusOperation> operations = reader.readOperations();
        assertThat(operations).isNotEmpty();
    }

    @Test
    public void operationsShouldBeTheExpectedAmount() {
        List<ModbusOperation> operations = reader.readOperations();
        assertThat(operations).hasSize(343);
    }

    @Test
    public void devicesShouldBeRead() {
        List<ModbusDevice> devices = reader.readDevices();
        assertThat(devices).isNotEmpty();
    }

    @Test
    public void devicesShouldBeTheExpectedAmount() {
        List<ModbusDevice> devices = reader.readDevices();
        assertThat(devices).hasSize(57);
    }

}
