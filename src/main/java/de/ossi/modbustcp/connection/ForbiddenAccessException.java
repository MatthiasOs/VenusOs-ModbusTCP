package de.ossi.modbustcp.connection;

import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import de.ossi.modbustcp.data.unit.AccessMode;

public class ForbiddenAccessException extends Exception {

	private static final long serialVersionUID = 1L;

	public ForbiddenAccessException(ModbusOperation operation, ModbusDevice device, AccessMode requiredMode) {
		super(createExceptionMessage(operation, device, requiredMode));
	}

	private static String createExceptionMessage(ModbusOperation operation, ModbusDevice device, AccessMode requiredMode) {
		return "Tried to query " + operation.toString() + " on device " + device.toString() +
                ".\n The operation has the AccessMode: " + operation.getMode() + ". But AccessMode: " + requiredMode +
                " is required!";
	}
}
