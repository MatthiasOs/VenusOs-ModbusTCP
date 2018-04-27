package de.ossi.wolfsbau;

import java.io.BufferedReader;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import de.ossi.wolfsbau.anfrager.WRAnfrager;
import de.ossi.wolfsbau.db.DBModel;
import de.ossi.wolfsbau.db.util.XMLtoDBConverter;
import de.ossi.wolfsbau.modbus.ModbusTCPReader;
import de.ossi.wolfsbau.modbus.data.ModbusDevice;
import de.ossi.wolfsbau.modbus.data.ModbusOperation;
import de.ossi.wolfsbau.modbus.data.ModbusResultInt;
import de.ossi.wolfsbau.parser.WRAntwortParser;
import de.ossi.wolfsbau.xml.XDevice;
import de.ossi.wolfsbau.xml.XRoot;

public class Starter {

	private static final String JDBC_PATH = "jdbc:sqlite:wr.sqlite";
	private static final String IP_WECHSELRICHTER = "192.168.0.101";
	private static final String IP_VICTRON = "192.168.0.81";
	private static final int MODBUS_DEFAULT_PORT = 502;

	private final WRAnfrager anfrager = new WRAnfrager(IP_WECHSELRICHTER);
	private final WRAntwortParser parser = new WRAntwortParser();
	private final DBModel schreiber = new DBModel(JDBC_PATH);
	private final ModbusTCPReader modbusReader = new ModbusTCPReader(IP_VICTRON, MODBUS_DEFAULT_PORT);

	public static void main(String[] args) throws IOException, JAXBException {
		Starter starter = new Starter();
//		 starter.speichereAktuelleWRDaten();
		starter.speichereAktuelleVictronDaten();
	}

	public void speichereAktuelleWRDaten() throws IOException, JAXBException {
		// REST Anfrage
		BufferedReader reader = anfrager.frageDatenAb();

		// REST Antwort (XML) Parsen
		XRoot root = parser.leseEin(reader);
		XDevice dev = root.getDevice();

		// XML Objekt in Entität umwandeln und un DB Speichern
		schreiber.saveDevice(XMLtoDBConverter.from(dev));
	}

	public void speichereAktuelleVictronDaten() {
		ModbusResultInt stateOfCharge = modbusReader
				.readOperationFromDevice(ModbusOperation.BATTERY_STATE_OF_CHARGE, ModbusDevice.VE_CAN_AND_SYSTEM_DEVICE_0);
		System.out.println(stateOfCharge.toString());
		ModbusResultInt gridPowerL1 = modbusReader
				.readOperationFromDevice(ModbusOperation.GRID_L1, ModbusDevice.VE_CAN_AND_SYSTEM_DEVICE_0);
		System.out.println(gridPowerL1.toString());
		ModbusResultInt gridPowerL2 = modbusReader
				.readOperationFromDevice(ModbusOperation.GRID_L2, ModbusDevice.VE_CAN_AND_SYSTEM_DEVICE_0);
		System.out.println(gridPowerL2.toString());
		ModbusResultInt gridPowerL3 = modbusReader
				.readOperationFromDevice(ModbusOperation.GRID_L3, ModbusDevice.VE_CAN_AND_SYSTEM_DEVICE_0);
		System.out.println(gridPowerL3.toString());
	}

}
