package de.ossi.modbustcp.connection;

import com.ghgande.j2mod.modbus.ModbusException;
import de.ossi.modbustcp.data.ModbusResultInt;
import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;

import java.util.concurrent.*;

public class ModbusTCPReader extends ModbusConnectionHandler {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ModbusTCPReader(String ip, int port) {
        super(ip, port);
    }

    public ModbusResultInt readOperationFromDevice(ModbusOperation operation, ModbusDevice device) throws ModbusException {
        connect();
        try {
            int result = readOperationFromDeviceInternal(operation, device).get(10L, TimeUnit.SECONDS);
            return new ModbusResultInt(operation, device, result);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    private Future<Integer> readOperationFromDeviceInternal(ModbusOperation operation, ModbusDevice device) {
        return executor.submit(() -> modbusMaster.readMultipleRegisters(device.getUnitId(), operation.getAddress(), 1)[0].getValue());
    }
}
