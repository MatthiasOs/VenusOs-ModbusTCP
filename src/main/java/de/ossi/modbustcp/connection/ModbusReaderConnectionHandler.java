package de.ossi.modbustcp.connection;

import com.ghgande.j2mod.modbus.ModbusException;

import de.ossi.modbustcp.data.ModbusDevice;
import de.ossi.modbustcp.data.ModbusResultInt;
import de.ossi.modbustcp.data.operation.ModbusOperation;

/**
 * Abstrakte Überklasse zum Öffnen und Schließen der Verbindung. Für Reader und
 * Writer
 * 
 * ip:192.168.0.81; port:502
 * 
 * @author ossi
 *
 */
public abstract class ModbusReaderConnectionHandler extends ModbusConnectionHandler {

	public ModbusReaderConnectionHandler(String ip, int port) {
		super(ip, port);
	}

	protected abstract ModbusResultInt readInternal(ModbusOperation operation, ModbusDevice device) throws ModbusException;

	public ModbusResultInt readOperationFromDevice(ModbusOperation operation, ModbusDevice device) throws ModbusException {
		ModbusResultInt result = null;
		connect();
		try {
			result = readInternal(operation, device);
		} finally {
			disconnect();
		}
		return result;
	}

}
