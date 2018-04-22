package de.ossi.wolfsbau.modbus;

import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;

/**
 * Abstrakte Überklasse zum Öffnen und Schließen der Verbindung. Für Reader
 * und Writer
 * 
 * ip:192.168.0.81; port:502
 * 
 * @author ossi
 *
 */
public abstract class AbstractModbusTCPClient {

	ModbusTCPMaster modbusMaster;

	protected AbstractModbusTCPClient(String ip, int port) {
		modbusMaster = new ModbusTCPMaster(ip, port);
	}

	void connect() {
		try {
			modbusMaster.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void disconnect() {
		try {
			modbusMaster.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
