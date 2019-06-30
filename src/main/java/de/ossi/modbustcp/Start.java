package de.ossi.modbustcp;

import de.ossi.modbustcp.modbus.ModbusJobScheduler;

public class Start {

	public static void main(String[] args) {
		ModbusJobScheduler reader = new ModbusJobScheduler();
		reader.execute(readPeriod(args));
	}

	private static long readPeriod(String[] args) {
		long period = 1000; // milliseconds
		try {
			period = Long.valueOf(args[0]);
		} catch (NumberFormatException e) {
		}
		return period;
	}

}
