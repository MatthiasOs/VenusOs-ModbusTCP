package de.ossi.wolfsbau.modbus;

import com.ghgande.j2mod.modbus.ModbusException;

import de.ossi.wolfsbau.modbus.data.ModbusDevice;
import de.ossi.wolfsbau.modbus.data.ModbusOperation;

public abstract class ModbusWriteConnectionHandler extends ModbusConnectionHandler {

	public ModbusWriteConnectionHandler(String ip, int port) {
		super(ip, port);
	}

	protected abstract boolean writeInternal(ModbusOperation operation, ModbusDevice device, int wert) throws ForbiddenAccessException, ModbusException;

	public boolean writeOperationFromDevice(ModbusOperation operation, ModbusDevice device, int wert) throws ForbiddenAccessException, ModbusException {
		connect();
		try {
			return writeInternal(operation, device, wert);
		} finally {
			disconnect();
		}
	}

}
