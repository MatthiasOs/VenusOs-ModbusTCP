package de.ossi.modbustcp.data;

import java.time.LocalDateTime;

import de.ossi.modbustcp.data.operation.ModbusDevice;
import de.ossi.modbustcp.data.operation.ModbusOperation;
import de.ossi.modbustcp.data.unit.DBusSpecialUnitParser;
import de.ossi.modbustcp.data.unit.DBusUnit;
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

	/**
	 * Returns the Value scaled in the range of the operation or if the result has a
	 * SpecialUnit, the text of the special unit
	 * 
	 * @return
	 */
	public String getValueOfOperation() {
		DBusUnit dbusUnit = operation.getDbusUnit();
		if (dbusUnit.isSpecialUnit()) {
			return DBusSpecialUnitParser.parse(dbusUnit, value);
		} else {
			return String.valueOf(getValueInRange(operation, value));
		}
	}

	// TODO remove Constants, can the Range be used instead?
	private static final double MAX_SIGNED = 32767D;
	private static final double MAX_REGISTER = 65535D;

	public Double getValueInRange(ModbusOperation operation, Integer registerValue) {
		if (operation.getType().isUnsigned()) {
			return scaleValue(operation, Double.valueOf(registerValue));
		} else {
			double valueInRange = registerValue > MAX_SIGNED ? registerValue - MAX_REGISTER - 1 : registerValue;
			return scaleValue(operation, valueInRange);
		}
	}

	private double scaleValue(ModbusOperation operation, Double registerValue) {
		return registerValue / operation.getScaleFactor();
	}

	public String getValueOfOperationWithUnit() {
		return new StringBuilder(getValueOfOperation()).append(" ").append(operation.getDbusUnit().getValue()).toString();
	}
}
