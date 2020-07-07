package de.ossi.modbustcp.data;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import com.creditdatamw.zerocell.Reader;

import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;

/**
 * Reads in the Data from the Excel Sheet CCGX-Modbus-TCP-register-list TODO it
 * shouldnt be needed to filter leading and trailing rows which cant be parsed
 * correctly
 * 
 * @author ossi
 *
 */
public class ExcelListReader {

	private File file;

	// TODO Remove Filename and search for file in resource folder with regex
	// TODO The leading and trailing rows with no parsable Data have to be removed
	// from the Excel file!!!
	private static final String OPERATIONS_SHEET_NAME = "Field list";
	private static final String DEVICES_SHEET_NAME = "Unit ID mapping";

	public ExcelListReader(String fileName) {
		if (fileName != null) {
			file = new File(fileName);
		}
	}

	public List<ModbusOperation> readOperations() {
		List<ModbusOperation> operations = Reader.of(ModbusOperation.class).from(file).sheet(OPERATIONS_SHEET_NAME).list();
		// Filter not parseable rows
		return operations.stream().filter(o -> o.getAddress() != null).collect(Collectors.toList());
	}

	public List<ModbusDevice> readDevices() {
		List<ModbusDevice> devices = Reader.of(ModbusDevice.class).from(file).sheet(DEVICES_SHEET_NAME).list();
		// Filter not parseable rows
		return devices.stream().filter(d -> d.getUnitId() != null).collect(Collectors.toList());
	}
}
