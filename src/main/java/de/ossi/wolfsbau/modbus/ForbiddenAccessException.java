package de.ossi.wolfsbau.modbus;

import de.ossi.wolfsbau.modbus.data.ModbusDevice;
import de.ossi.wolfsbau.modbus.data.ModbusOperation;
import de.ossi.wolfsbau.modbus.data.ModbusOperation.AccessMode;

public class ForbiddenAccessException extends Exception {

	private static final long serialVersionUID = 1L;

	public ForbiddenAccessException(ModbusOperation operation, ModbusDevice device, AccessMode requiredMode) {
		super(createExceptionMessage(operation, device, requiredMode));
	}

	private static String createExceptionMessage(ModbusOperation operation, ModbusDevice device, AccessMode requiredMode) {
		return new StringBuilder().append("Es wurde versucht die Operation ").append(operation.toString()).append(" am Device ").append(device.toString())
				.append(" aufzurufen. Die Operation hat den AccessMode: ").append(operation.mode).append(". Es wird aber der AccessMode: ").append(requiredMode)
				.append(" benötigt!").toString();
	}
}
