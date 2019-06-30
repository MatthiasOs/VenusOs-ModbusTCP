package de.ossi.modbustcp.modbus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import de.ossi.modbustcp.connection.ModbusTCPReader;
import de.ossi.modbustcp.db.InfluxDBModel;
import lombok.Getter;

public class ModbusJobScheduler {

	private static final String IP_VICTRON = "192.168.0.81";
	private static final int MODBUS_DEFAULT_PORT = 502;

	private final InfluxDBModel model = new InfluxDBModel("http://localhost:8086", "wolfsbauDB", "root", "root");
	private final ModbusTCPReader modbusReader = new ModbusTCPReader(IP_VICTRON, MODBUS_DEFAULT_PORT);
	@Getter
	private final List<ReadContainer> readValues = new ArrayList<>();

	public void execute(long period) {
		Timer timer = new Timer();
		ReaderTask modbusReaderTask = new ReaderTask(model, modbusReader, this::getReadValues);
		timer.schedule(modbusReaderTask, 0, period);
	}
	
}
