package de.ossi.wolfsbau.modbus.data;

import java.time.LocalDateTime;

import de.ossi.wolfsbau.modbus.data.operation.ModbusOperation;
import de.ossi.wolfsbau.modbus.data.unit.DBusUnit;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ModbusResultInt {

	private final ModbusOperation operation;
	private final Integer wert;
	private final LocalDateTime zeitpunkt;

	public ModbusResultInt(ModbusOperation operation, Integer wert) {
		this.operation = operation;
		this.wert = wert;
		this.zeitpunkt = LocalDateTime.now();
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
		DBusUnit einheit = operation.getDbusUnit();
		if (einheit != null && einheit.hatSpezielleEinheit()) {
			return einheit.getMesswertMitEinheit(wert);
		} else {
			return new StringBuilder().append(operation.getSkaliertenWertInWertebreich(wert)).append(" ").append(einheit != null ? einheit.toString() : "").toString();
		}
	}
}
