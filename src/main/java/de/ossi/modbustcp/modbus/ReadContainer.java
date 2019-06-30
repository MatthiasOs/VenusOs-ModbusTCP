package de.ossi.modbustcp.modbus;

import de.ossi.modbustcp.data.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Container for a ModbusRead Operation which consits of an {@link ModbusDevice}
 * and a {@link ModbusOperation}
 * 
 * @author ossi
 *
 */
@Builder
@ToString
public class ReadContainer {
	@Getter
	private final ModbusOperation operation;
	@Getter
	private final ModbusDevice device;
}
