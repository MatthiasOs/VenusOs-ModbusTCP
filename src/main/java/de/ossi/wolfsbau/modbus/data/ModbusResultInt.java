package de.ossi.wolfsbau.modbus.data;

import java.time.LocalDateTime;

import de.ossi.wolfsbau.modbus.data.unit.DBusUnit;

public class ModbusResultInt {

	private final ModbusOperation operation;
	private final Integer wert;
	private final LocalDateTime zeitpunkt;

	public ModbusResultInt(ModbusOperation operation, Integer wert) {
		this.operation = operation;
		this.wert = wert;
		this.zeitpunkt = LocalDateTime.now();
	}

	public ModbusOperation getOperation() {
		return operation;
	}

	public Integer getWert() {
		return wert;
	}

	public LocalDateTime getZeitpunkt() {
		return zeitpunkt;
	}

	@Override
	public String toString() {
		System.out.println("Wert: " + wert);
		StringBuilder ausgabe = new StringBuilder();
		ausgabe.append("Register: ");
		ausgabe.append(operation.getAddress());
		ausgabe.append(" ");
		ausgabe.append(operation.getDescription());
		ausgabe.append(" ");
		ausgabe.append(ermittleWertMitEinheit());
		ausgabe.append(System.lineSeparator());
		return ausgabe.toString();
	}

	private String ermittleWertMitEinheit() {
		DBusUnit einheit = operation.getDbusUnit();
		if (einheit.hatSpezielleEinheit()) {
			return einheit.getMesswertMitEinheit(wert);
		} else {
			return new StringBuilder().append(operation.getSkaliertenWertInWertebreich(wert)).append(" ").append(einheit.toString()).toString();
		}
	}
}
