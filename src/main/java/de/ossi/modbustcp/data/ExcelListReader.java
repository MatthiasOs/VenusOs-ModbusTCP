package de.ossi.modbustcp.data;

import com.creditdatamw.zerocell.Reader;
import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Reads in the Data from the Excel Sheet CCGX-Modbus-TCP-register-list
 *
 * @author ossi
 */
@RequiredArgsConstructor
public class ExcelListReader {

    private final String fileName;

    private static final String OPERATIONS_SHEET_NAME = "Field list";
    private static final String DEVICES_SHEET_NAME = "Unit ID mapping";

    public List<ModbusOperation> readOperations() {
        try (InputStream inputStream = getInputStream()) {
            List<ModbusOperation> operations = Reader.of(ModbusOperation.class).from(inputStream).sheet(OPERATIONS_SHEET_NAME).list();
            // Filter not parseable rows
            return operations.stream().filter(o -> o.getAddress() != null).collect(toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ModbusDevice> readDevices() {
        try (InputStream inputStream = getInputStream()) {
            List<ModbusDevice> devices = Reader.of(ModbusDevice.class).from(inputStream).sheet(DEVICES_SHEET_NAME).list();
            // Filter not parseable rows
            return devices.stream().filter(d -> d.getUnitId() != null).collect(toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getInputStream() {
        if (fileName == null) {
            throw new IllegalArgumentException("Datei kann nicht leer sein!");
        }
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException(String.format("Datei [%s] nicht gefunden!", fileName));
        }
        return inputStream;
    }
}
