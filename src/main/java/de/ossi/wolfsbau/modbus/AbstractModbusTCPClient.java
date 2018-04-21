package de.ossi.wolfsbau.modbus;

import java.io.IOException;

import de.re.easymodbus.modbusclient.ModbusClient;

/**
 * Abstrakte Überklasse zum Öffnen und Schließen der Verbindung. 
 * Für Reader und Writer
 * 
 * ip:192.168.0.81; port:502
 * 
 * @author ossi
 *
 */
public abstract class AbstractModbusTCPClient {

	ModbusClient modbusClient;

	protected AbstractModbusTCPClient(String ip, int port) {
		modbusClient = new ModbusClient(ip, port);
		modbusClient.setUDPFlag(false);
	}

	void connect() {
		try {
			modbusClient.Connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void disconnect() {
		try {
			modbusClient.Disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
