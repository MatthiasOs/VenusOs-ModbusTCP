package de.ossi.modbustcp.connection;

import java.time.LocalDateTime;

import com.ghgande.j2mod.modbus.ModbusException;

import de.ossi.modbustcp.data.ModbusDevice;
import de.ossi.modbustcp.data.ModbusResultInt;
import de.ossi.modbustcp.data.operation.ModbusOperation;

public class ModbusTCPReader extends ModbusReaderConnectionHandler {

	public ModbusTCPReader(String ip, int port) {
		super(ip, port);
	}

	@Override
	protected ModbusResultInt readInternal(ModbusOperation operation, ModbusDevice device) throws ModbusException {
		int result = readOperationFromDeviceInternal(operation, device);
		return ModbusResultInt.builder().operation(operation).device(device).value(result).timestamp(LocalDateTime.now()).build();
	}

	private int readOperationFromDeviceInternal(ModbusOperation operation, ModbusDevice device) throws ModbusException {
		return modbusMaster.readMultipleRegisters(device.getUnitId(), operation.getAddress(), 1)[0].getValue();
	}
}
