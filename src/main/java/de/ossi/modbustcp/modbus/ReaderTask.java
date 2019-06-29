package de.ossi.modbustcp.modbus;

import java.util.List;
import java.util.TimerTask;
import java.util.function.Supplier;

import com.ghgande.j2mod.modbus.ModbusException;

import de.ossi.modbustcp.connection.ModbusTCPReader;
import de.ossi.modbustcp.data.ModbusDevice;
import de.ossi.modbustcp.data.ModbusResultInt;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import de.ossi.modbustcp.db.InfluxDBModel;
import lombok.RequiredArgsConstructor;

/**
 * Task which periodically reads the provided List of {@link ReadContainer}
 * ({@link ModbusOperation} on the {@link ModbusDevice}). And saves them through
 * the {@link InfluxDBModel}.
 * 
 * @author ossi
 *
 */
@RequiredArgsConstructor
public class ReaderTask extends TimerTask {

	private final InfluxDBModel model;
	private final ModbusTCPReader modbusReader;
	private final Supplier<List<ReadContainer>> valuesSupplier;

	@Override
	public void run() {
		valuesSupplier.get().stream().forEach((ReadContainer c) -> {
			try {
				ModbusResultInt result = modbusReader.readOperationFromDevice(c.getOperation(), c.getDevice());
				model.addPoint(result);
			} catch (ModbusException e) {
				e.printStackTrace();
			}
		});
		// model.writePoints();
		// model.readAllMeasurements();
	}

}