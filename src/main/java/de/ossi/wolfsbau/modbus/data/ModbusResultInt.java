package de.ossi.wolfsbau.modbus.data;

import java.time.LocalDateTime;

import de.ossi.wolfsbau.modbus.data.unit.DBusUnit;

public class ModbusResultInt {

	public final ModbusOperation operation;
	public final Integer wert;
	public final LocalDateTime zeitpunkt;

	public ModbusResultInt(ModbusOperation operation, Integer wert) {
		this.operation = operation;
		this.wert = wert;
		this.zeitpunkt = LocalDateTime.now();
	}

	@Override
	public String toString() {
		StringBuilder ausgabe = new StringBuilder();
		ausgabe.append(operation.toString());
		ausgabe.append(" ");
		ausgabe.append(ermittleWertMitEinheit());
		ausgabe.append(System.lineSeparator());
		return ausgabe.toString();
	}

	private String ermittleWertMitEinheit() {
		DBusUnit einheit = operation.dbusUnit;
		if (einheit != null && einheit.hatSpezielleEinheit()) {
			return einheit.getMesswertMitEinheit(wert);
		} else {
			return new StringBuilder().append(operation.getSkaliertenWertInWertebreich(wert))
					.append(" ").append(einheit != null ? einheit.toString() : "").toString();
		}
	}
}
