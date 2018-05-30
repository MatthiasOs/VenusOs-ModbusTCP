package de.ossi.wolfsbau.gui;

import java.time.LocalDateTime;

import de.ossi.wolfsbau.modbus.data.ModbusDevice;
import de.ossi.wolfsbau.modbus.data.operation.ModbusOperation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
public class DeviceOperationResultTO {
	@NonNull
	private final ModbusOperation operation;
	@NonNull
	private final ModbusDevice modbusDevice;
	@Setter
	private LocalDateTime zeit;
	@Setter
	private String ergebnis;
}
