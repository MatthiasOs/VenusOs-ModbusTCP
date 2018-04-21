package de.ossi.wolfsbau.modbus;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import de.ossi.wolfsbau.modbus.data.ModbusDevice;
import de.ossi.wolfsbau.modbus.data.ModbusOperation;
import de.ossi.wolfsbau.modbus.data.ModbusResult;
import de.re.easymodbus.exceptions.ModbusException;

/**
 * Stellt eine Read Methode zum Überschreiben zur Verfügung. Aufgerufen zum
 * Benützen von außen wird die nicht abstrakte read-Methode.
 * 
 * @author ossi
 *
 */
public class ModbusTCPReader extends AbstractModbusTCPClient {

	/**
	 * Modubs TCP Client mit Ip und Port erstellen
	 */
	public ModbusTCPReader(String ip, int port) {
		super(ip, port);
	}

	public ModbusResult<Long> readOperationFromDevice(ModbusOperation operation, ModbusDevice device) {
		long result = Long.MIN_VALUE;
		connect();
		try {
			result = readOperationFromDeviceInternal(operation, device);
		} catch (ModbusException | IOException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return new ModbusResult<Long>(operation, result);
	}

	private long readOperationFromDeviceInternal(ModbusOperation operation, ModbusDevice device)
			throws UnknownHostException, SocketException, ModbusException, IOException {
		modbusClient.setUnitIdentifier(device.getUnitId());
		return modbusClient.ReadHoldingRegisters(operation.getAddress(), 1)[0];
	}

}
