package de.ossi.modbustcp.data;

import java.time.LocalDateTime;

import de.ossi.modbustcp.data.operation.ModbusOperation;
import de.ossi.modbustcp.data.unit.DBusSpecialUnit;
import de.ossi.modbustcp.data.unit.DBusUnit;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ModbusResultInt {

	private final ModbusOperation operation;
	private final Integer value;
	private final LocalDateTime timestamp;

	public ModbusResultInt(ModbusOperation operation, Integer value) {
		this.operation = operation;
		this.value = value;
		this.timestamp = LocalDateTime.now();
	}

	@Override
	public String toString() {
		StringBuilder ausgabe = new StringBuilder();
		ausgabe.append(operation.toString());
		ausgabe.append(System.lineSeparator());
		ausgabe.append("Wert: ");
		ausgabe.append(ermittleWertMitEinheit());
		ausgabe.append(System.lineSeparator());
		return ausgabe.toString();
	}

	public String ermittleWertMitEinheit() {
		DBusUnit dbusUnit = operation.getDbusUnit();
		String unit = dbusUnit.getUnit(value);
		if (dbusUnit instanceof DBusSpecialUnit) {
			return unit;
		} else {
			return new StringBuilder().append(operation.getSkaliertenWertInWertebreich(value)).append(" ").append(unit).toString();
		}
	}
}
