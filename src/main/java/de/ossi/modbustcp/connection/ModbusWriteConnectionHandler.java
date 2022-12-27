package de.ossi.modbustcp.connection;

import com.ghgande.j2mod.modbus.ModbusException;

import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;

public abstract class ModbusWriteConnectionHandler extends ModbusConnectionHandler {

	public ModbusWriteConnectionHandler(String ip, int port) {
		super(ip, port);
	}

	protected abstract boolean writeInternal(ModbusOperation operation, ModbusDevice device, int value) throws ForbiddenAccessException, ModbusException;

	public boolean writeOperationFromDevice(ModbusOperation operation, ModbusDevice device, int value) throws ForbiddenAccessException, ModbusException {
		connect();
		try {
			return writeInternal(operation, device, value);
		} finally {
			disconnect();
		}
	}

}
