package de.ossi.wolfsbau.modbus;

import java.io.IOException;

import de.re.easymodbus.modbusclient.ModbusClient;

/**
 * Abstrakte Überklasse zum öffnen und Schließen der Verbindung. Für Reader und
 * Writer
 * 
 * D:\ownCloud\DE_WO_Wolfsbau
 * Speicher\3_Technical_offer\Victron\CCGX-Modbus-TCP-register-list-2.12.xlsx
 * com.victronenergy.system
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
		// modbusClient.setSerialFlag(false);
		// Siehe CSV; für jedes Gerät
		byte unitID = 32;
		modbusClient.setUnitIdentifier(unitID);
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
