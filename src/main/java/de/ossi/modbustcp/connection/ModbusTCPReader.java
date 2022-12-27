package de.ossi.modbustcp.connection;

import com.ghgande.j2mod.modbus.ModbusException;

import de.ossi.modbustcp.data.ModbusResultInt;
import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;

public class ModbusTCPReader extends ModbusReaderConnectionHandler {

	public ModbusTCPReader(String ip, int port) {
		super(ip, port);
	}

	@Override
	protected ModbusResultInt readInternal(ModbusOperation operation, ModbusDevice device) throws ModbusException {
		int result = readOperationFromDeviceInternal(operation, device);
		return new ModbusResultInt(operation, device, result);
	}

	private int readOperationFromDeviceInternal(ModbusOperation operation, ModbusDevice device) throws ModbusException {
		return modbusMaster.readMultipleRegisters(device.getUnitId(), operation.getAddress(), 1)[0].getValue();
	}
}
