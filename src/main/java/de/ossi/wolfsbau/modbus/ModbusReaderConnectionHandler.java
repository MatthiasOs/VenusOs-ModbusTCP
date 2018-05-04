package de.ossi.wolfsbau.modbus;

import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;

import de.ossi.wolfsbau.modbus.data.ModbusDevice;
import de.ossi.wolfsbau.modbus.data.ModbusOperation;
import de.ossi.wolfsbau.modbus.data.ModbusResultInt;

/**
 * Abstrakte Überklasse zum Öffnen und Schließen der Verbindung. Für Reader
 * und Writer
 * 
 * ip:192.168.0.81; port:502
 * 
 * @author ossi
 *
 */
public abstract class ModbusReaderConnectionHandler {

	protected ModbusTCPMaster modbusMaster;

	protected ModbusReaderConnectionHandler(String ip, int port) {
		modbusMaster = new ModbusTCPMaster(ip, port);
	}
	
	protected abstract ModbusResultInt readInternal(ModbusOperation operation, ModbusDevice device);
	
	public ModbusResultInt readOperationFromDevice(ModbusOperation operation, ModbusDevice device) {
		ModbusResultInt result = null;
		connect();
		try {
			readInternal(operation, device);
		}finally {
			disconnect();
		}
		return result;
	}

	private void connect() {
		try {
			modbusMaster.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void disconnect() {
		try {
			modbusMaster.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
