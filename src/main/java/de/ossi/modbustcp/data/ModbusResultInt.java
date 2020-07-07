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
			return String.valueOf(operation.getValueInRange(value));
		}
	}

	public String getValueOfOperationWithUnit() {
		return new StringBuilder(getValueOfOperation()).append(" ").append(operation.getDbusUnit().getValue()).toString();
	}
}
