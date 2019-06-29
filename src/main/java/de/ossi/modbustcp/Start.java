package de.ossi.modbustcp;

import de.ossi.modbustcp.modbus.ModbusJobScheduler;

public class Start {

	public static void main(String[] args) {
		
		ModbusJobScheduler reader = new ModbusJobScheduler();
		long period = 1000; // milliseconds
		reader.execute(period);
	}

}
