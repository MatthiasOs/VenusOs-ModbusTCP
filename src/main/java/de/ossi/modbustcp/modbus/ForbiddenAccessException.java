package de.ossi.modbustcp.modbus;

import de.ossi.modbustcp.modbus.data.operation.ModbusOperation;
import de.ossi.modbustcp.modbus.data.operation.ModbusOperation.AccessMode;
import de.ossi.modbustcp.modbustcp.data.ModbusDevice;

public class ForbiddenAccessException extends Exception {

	private static final long serialVersionUID = 1L;

	public ForbiddenAccessException(ModbusOperation operation, ModbusDevice device, AccessMode requiredMode) {
		super(createExceptionMessage(operation, device, requiredMode));
	}

	private static String createExceptionMessage(ModbusOperation operation, ModbusDevice device, AccessMode requiredMode) {
		return new StringBuilder().append("Tried to query ").append(operation.toString()).append(" on device ").append(device.toString())
				.append(".\n The operation has the AccessMode: ").append(operation.getMode()).append(". But AccessMode: ").append(requiredMode)
				.append(" is required!").toString();
	}
}
