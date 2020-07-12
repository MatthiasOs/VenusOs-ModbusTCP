package de.ossi.modbustcp.gui;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.swing.JButton;

import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Transportopjekt für den Client um die Daten der Tabelle zu halten.
 * 
 * @author ossi
 *
 */
@EqualsAndHashCode(exclude = "removeButton")
@Getter
public class DeviceOperationResultTO implements Serializable {
	private static final long serialVersionUID = 1L;
	@NonNull
	private final ModbusOperation operation;
	@NonNull
	private final ModbusDevice modbusDevice;
	@Setter
	private LocalDateTime zeit;
	@Setter
	private String ergebnis;
	@Setter
	private JButton removeButton;

	public DeviceOperationResultTO(ModbusOperation operation, ModbusDevice modbusDevice, JButton removeButton) {
		this.operation = operation;
		this.modbusDevice = modbusDevice;
		this.removeButton = removeButton;
	}
}
