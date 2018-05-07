package de.ossi.wolfsbau.modbus;

import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;

public abstract class ModbusConnectionHandler {

	protected ModbusTCPMaster modbusMaster;

	protected ModbusConnectionHandler(String ip, int port) {
		modbusMaster = new ModbusTCPMaster(ip, port);
	}

	protected void connect() {
		try {
			modbusMaster.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void disconnect() {
		try {
			modbusMaster.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
