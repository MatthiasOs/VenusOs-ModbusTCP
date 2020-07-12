package de.ossi.modbustcp.data;

import java.time.LocalDateTime;

import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import de.ossi.modbustcp.data.unit.DBusSpecialUnitParser;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ModbusResultInt {

	private final ModbusOperation operation;
	private final ModbusDevice device;
	private final Integer value;
	private final LocalDateTime timestamp;

	public ModbusResultInt(ModbusOperation operation, ModbusDevice device, Integer value) {
		this.operation = operation;
		this.value = value;
		this.device = device;
		this.timestamp = LocalDateTime.now();
	}

	@Override
	public String toString() {
		StringBuilder ausgabe = new StringBuilder();
		ausgabe.append(device.getName());
		ausgabe.append(System.lineSeparator());
		ausgabe.append(operation.toString());
		ausgabe.append(System.lineSeparator());
		ausgabe.append("Wert: ");
		ausgabe.append(getValue());
		ausgabe.append(" ");
		ausgabe.append(operation.getDbusUnit().getValue());
		ausgabe.append(System.lineSeparator());
		return ausgabe.toString();
	}

	// TODO remove Constants, can the Range be used instead?
	private static final double MAX_SIGNED = 32767D;
	private static final double MAX_REGISTER = 65535D;

	private Double getValueInRange() {
		if (operation.getType().isUnsigned()) {
			return scaleValue(operation, Double.valueOf(value));
		} else {
			double valueInRange = value > MAX_SIGNED ? value - MAX_REGISTER - 1 : value;
			return scaleValue(operation, valueInRange);
		}
	}

	public String getValueOfOperationWithUnit() {
		if (operation.getDbusUnit().isSpecialUnit()) {
			return new StringBuilder(DBusSpecialUnitParser.parse(operation.getDbusUnit(), value))
					.append(" [")
					.append(operation.getDbusUnit().getValue())
					.append("]")
					.toString();
		}
		return new StringBuilder(String.valueOf(getValueInRange()))
				.append(" ")
				.append(operation.getDbusUnit().getValue())
				.toString();
	}

	private double scaleValue(ModbusOperation operation, Double registerValue) {
		return registerValue / operation.getScaleFactor();
	}
}
