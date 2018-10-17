package de.ossi.modbustcp.modbus;

import com.ghgande.j2mod.modbus.ModbusException;

import de.ossi.modbustcp.modbus.data.operation.ModbusOperation;
import de.ossi.modbustcp.modbustcp.data.ModbusDevice;

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
