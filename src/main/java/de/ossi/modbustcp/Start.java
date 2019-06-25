package de.ossi.modbustcp;

import java.util.Timer;

import de.ossi.modbustcp.connection.task.ModbusReaderTask;
import de.ossi.modbustcp.data.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import de.ossi.modbustcp.db.InfluxDBModel;

public class Start {

	private static final InfluxDBModel model = new InfluxDBModel("http://localhost:8086", "testDB", "root", "root");

	public static void main(String[] args) {

		long period = 1000; // milliseconds
		Timer timer = new Timer();
		ModbusReaderTask modbusReaderTask = new ModbusReaderTask(model)
				.with(ModbusOperation.GRI_GRID_L1_VOLTAGE, ModbusDevice.VE_DIRECT_USB0)
				.with(ModbusOperation.GRI_GRID_L1_CURRENT, ModbusDevice.VE_DIRECT_USB1);
		timer.schedule(modbusReaderTask, 0, period);
	}

}
