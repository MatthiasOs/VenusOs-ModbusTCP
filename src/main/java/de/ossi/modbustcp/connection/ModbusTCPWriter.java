package de.ossi.modbustcp.connection;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.procimg.SimpleInputRegister;

import de.ossi.modbustcp.data.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import de.ossi.modbustcp.data.operation.ModbusOperation.AccessMode;

public class ModbusTCPWriter extends ModbusWriteConnectionHandler {

	public ModbusTCPWriter(String ip, int port) {
		super(ip, port);
	}

	@Override
	protected boolean writeInternal(ModbusOperation operation, ModbusDevice device, int value) throws ForbiddenAccessException, ModbusException {
		if (operation.getMode() != AccessMode.READ_WRITE) {
			throw new ForbiddenAccessException(operation, device, AccessMode.READ_WRITE);
		}
		modbusMaster.writeSingleRegister(device.getUnitId(), operation.getAddress(), new SimpleInputRegister(value));
		return true;
	}

}
