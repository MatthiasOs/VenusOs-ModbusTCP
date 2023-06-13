package de.ossi.modbustcp.data;

import com.creditdatamw.zerocell.Reader;
import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.List;

import static java.util.stream.Collectors.*;

/**
 * Reads in the Data from the Excel Sheet CCGX-Modbus-TCP-register-list
 *
 * @author ossi
 */
@RequiredArgsConstructor
public class ExcelListReader {

    private final File file;

    private static final String OPERATIONS_SHEET_NAME = "Field list";
    private static final String DEVICES_SHEET_NAME = "Unit ID mapping";

    public List<ModbusOperation> readOperations() {
        if (file == null) {
            throw new IllegalArgumentException("Datei nicht gefunden!");
        }
        List<ModbusOperation> operations = Reader.of(ModbusOperation.class)
                                                 .from(file)
                                                 //Skipping the "NOTE" row
                                                 .skipFirstNRows(1)
                                                 .sheet(OPERATIONS_SHEET_NAME)
                                                 .list();
        return operations.stream()
                         .filter(ModbusOperation::isValid)
                         .collect(toList());
    }

    public List<ModbusDevice> readDevices() {
        if (file == null) {
            throw new IllegalArgumentException("Datei nicht gefunden!");
        }
        List<ModbusDevice> devices = Reader.of(ModbusDevice.class)
                                           .from(file)
                                           .sheet(DEVICES_SHEET_NAME)
                                           .list();
        return devices.stream()
                      .filter(ModbusDevice::isValid)
                      .collect(toList());
    }
}
