package de.ossi.modbustcp.connection;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.procimg.SimpleInputRegister;
import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import de.ossi.modbustcp.data.unit.AccessMode;

public class ModbusTCPWriter extends ModbusConnectionHandler {

    public ModbusTCPWriter(String ip, int port) {
        super(ip, port);
    }

    public void writeOperationFromDevice(ModbusOperation operation, ModbusDevice device, int value) throws ForbiddenAccessException, ModbusException {
        connect();
        try {
            if (operation.getMode() != AccessMode.READ_WRITE) {
                throw new ForbiddenAccessException(operation, device, AccessMode.READ_WRITE);
            }
            modbusMaster.writeSingleRegister(device.getUnitId(), operation.getAddress(), new SimpleInputRegister(value));
        } finally {
            disconnect();
        }
    }
}
