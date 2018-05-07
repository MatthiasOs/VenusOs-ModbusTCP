package de.ossi.wolfsbau.modbus;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.procimg.SimpleInputRegister;

import de.ossi.wolfsbau.modbus.data.ModbusDevice;
import de.ossi.wolfsbau.modbus.data.ModbusOperation;
import de.ossi.wolfsbau.modbus.data.ModbusOperation.AccessMode;

public class ModbusTCPWriter extends ModbusWriteConnectionHandler {

	public ModbusTCPWriter(String ip, int port) {
		super(ip, port);
	}

	@Override
	protected boolean writeInternal(ModbusOperation operation, ModbusDevice device, int wert) throws ForbiddenAccessException {
		if (operation.mode != AccessMode.READ_WRITE) {
			throw new ForbiddenAccessException(operation, device, AccessMode.READ_WRITE);
		}
		try {
			modbusMaster.writeSingleRegister(device.unitId, operation.address, new SimpleInputRegister(wert));
			return true;
		} catch (ModbusException e) {
			e.printStackTrace();
		}
		return false;
	}

}
