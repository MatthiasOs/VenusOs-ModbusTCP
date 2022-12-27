package de.ossi.modbustcp.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import com.creditdatamw.zerocell.Reader;

import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;

/**
 * Reads in the Data from the Excel Sheet CCGX-Modbus-TCP-register-list
 * 
 * @author ossi
 *
 */
public class ExcelListReader {

	private File file;

	private static final String OPERATIONS_SHEET_NAME = "Field list";
	private static final String DEVICES_SHEET_NAME = "Unit ID mapping";

	public ExcelListReader(String fileName) {
		if (fileName != null) {
			URL resource = ExcelListReader.class.getClassLoader().getResource(fileName);
			if(resource==null){
				throw new IllegalArgumentException("Datei nicht gefunden: "+fileName);
			}
			file = new File(resource.getFile());
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
