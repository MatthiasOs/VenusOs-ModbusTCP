package de.ossi.modbustcp.modbus;

import java.util.List;
import java.util.TimerTask;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ghgande.j2mod.modbus.ModbusException;

import de.ossi.modbustcp.connection.ModbusTCPReader;
import de.ossi.modbustcp.data.ModbusDevice;
import de.ossi.modbustcp.data.ModbusResultInt;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import de.ossi.modbustcp.db.InfluxDBModel;
import lombok.RequiredArgsConstructor;

/**
 * Task which periodically reads the List of {@link ReadContainer}
 * ({@link ModbusOperation} on the {@link ModbusDevice}) provided by the
 * Supplier. And saves them through the {@link InfluxDBModel}.
 * 
 * @author ossi
 *
 */
@RequiredArgsConstructor
public class ReaderTask extends TimerTask {

	private final InfluxDBModel model;
	private final ModbusTCPReader modbusReader;
	private final Supplier<List<ReadContainer>> valuesSupplier;
	private static final Logger LOGGER = Logger.getLogger(ReaderTask.class.getName());

	@Override
	public void run() {
		valuesSupplier.get().stream().forEach((ReadContainer c) -> {
			try {
				ModbusResultInt result = modbusReader.readOperationFromDevice(c.getOperation(), c.getDevice());
				model.addPoint(result);
			} catch (ModbusException e) {
				LOGGER.log(Level.SEVERE, "Error when reading from ModbusTCP", e);
			}
		});
	}

}