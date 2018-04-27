package de.ossi.wolfsbau.modbus;

import de.ossi.wolfsbau.modbus.data.ModbusDevice;
import de.ossi.wolfsbau.modbus.data.ModbusOperation;
import de.ossi.wolfsbau.modbus.data.ModbusResultInt;

/**
 * Stellt eine Read Methode zum Überschreiben zur Verfügung. Aufgerufen zum
 * Benützen von außen wird die nicht abstrakte read-Methode.
 * 
 * @author ossi
 *
 */
public class ModbusTCPReader extends ModbusConnection {

	/**
	 * Modubs TCP Client mit Ip und Port erstellen
	 */
	public ModbusTCPReader(String ip, int port) {
		super(ip, port);
	}

	public ModbusResultInt readOperationFromDevice(ModbusOperation operation, ModbusDevice device) {
		int result = Integer.MIN_VALUE;
		connect();
		try {
			result = readOperationFromDeviceInternal(operation, device);
		} finally {
			disconnect();
		}
		return new ModbusResultInt(operation, result);
	}

	private int readOperationFromDeviceInternal(ModbusOperation operation, ModbusDevice device) {
		try {
			return modbusMaster.readMultipleRegisters(device.getUnitId(), operation.getAddress(), 1)[0].getValue();
		} catch (com.ghgande.j2mod.modbus.ModbusException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
