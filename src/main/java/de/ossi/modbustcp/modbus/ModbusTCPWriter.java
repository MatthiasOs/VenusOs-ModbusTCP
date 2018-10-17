package de.ossi.modbustcp.modbus;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.procimg.SimpleInputRegister;

import de.ossi.modbustcp.modbus.data.operation.ModbusOperation;
import de.ossi.modbustcp.modbus.data.operation.ModbusOperation.AccessMode;
import de.ossi.modbustcp.modbustcp.data.ModbusDevice;

public class ModbusTCPWriter extends ModbusWriteConnectionHandler {

	public ModbusTCPWriter(String ip, int port) {
		super(ip, port);
	}

	@Override
	protected boolean writeInternal(ModbusOperation operation, ModbusDevice device, int wert) throws ForbiddenAccessException, ModbusException {
		if (operation.getMode() != AccessMode.READ_WRITE) {
			throw new ForbiddenAccessException(operation, device, AccessMode.READ_WRITE);
		}
		modbusMaster.writeSingleRegister(device.getUnitId(), operation.getAddress(), new SimpleInputRegister(wert));
		return true;
	}

}
