package de.ossi.wolfsbau.modbus;

import com.ghgande.j2mod.modbus.ModbusException;

import de.ossi.wolfsbau.modbus.data.ModbusDevice;
import de.ossi.wolfsbau.modbus.data.ModbusOperation;
import de.ossi.wolfsbau.modbus.data.ModbusResultInt;

public class ModbusTCPReader extends ModbusReaderConnectionHandler {

	public ModbusTCPReader(String ip, int port) {
		super(ip, port);
	}

	@Override
	protected ModbusResultInt readInternal(ModbusOperation operation, ModbusDevice device) throws ModbusException{
		int result = readOperationFromDeviceInternal(operation, device);
		return new ModbusResultInt(operation, result);
	}

	private int readOperationFromDeviceInternal(ModbusOperation operation, ModbusDevice device) throws ModbusException {
		return modbusMaster.readMultipleRegisters(device.unitId, operation.address, 1)[0].getValue();
	}
}
