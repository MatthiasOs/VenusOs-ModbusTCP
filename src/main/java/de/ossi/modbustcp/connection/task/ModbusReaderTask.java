package de.ossi.modbustcp.connection.task;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import de.ossi.modbustcp.connection.ModbusTCPReader;
import de.ossi.modbustcp.data.ModbusDevice;
import de.ossi.modbustcp.data.ModbusResultInt;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import de.ossi.modbustcp.db.InfluxDBModel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ModbusReaderTask extends TimerTask {

	private static final String IP_VICTRON = "192.168.0.81";
	private static final int MODBUS_DEFAULT_PORT = 502;

	private final InfluxDBModel model;

	private final ModbusTCPReader modbusReader = new ModbusTCPReader(IP_VICTRON, MODBUS_DEFAULT_PORT);
	private final List<ReadContainer> readValues = new ArrayList<>();

	private int value = 12;

	@Override
	public void run() {

		readValues.stream().forEach((ReadContainer c) -> {
			//TODO entfernen
			// ModbusResultInt result = modbusReader.readOperationFromDevice(v.operation,
			// v.device);
			ModbusResultInt result = new ModbusResultInt(c.operation, c.device, value);
			value++;
			model.addPoint(result);
		});
		model.writePoints();
		model.readAllMeasurements();
	}

	public ModbusReaderTask with(ModbusOperation operation, ModbusDevice device) {
		readValues.add(ReadContainer.builder().device(device).operation(operation).build());
		return this;
	}

	@Builder
	private static class ReadContainer {
		private final ModbusOperation operation;
		private final ModbusDevice device;
	}
}