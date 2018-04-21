package de.ossi.wolfsbau.modbus.data;

import java.time.LocalDateTime;

public class ModbusResult<T extends Number> {

	private final ModbusOperation operation;
	private final T value;
	private final LocalDateTime zeitpunkt;

	public ModbusResult(ModbusOperation operation, T value) {
		this.operation = operation;
		this.value = value;
		this.zeitpunkt = LocalDateTime.now();
	}

	public ModbusOperation getOperation() {
		return operation;
	}

	public T getValue() {
		return value;
	}

	public LocalDateTime getZeitpunkt() {
		return zeitpunkt;
	}

	@Override
	public String toString() {
		StringBuilder socAusgabe = new StringBuilder();
		socAusgabe.append(operation.getDescription());
		socAusgabe.append(": ");
		socAusgabe.append(berechneSkaliertenWert());
		socAusgabe.append(" ");
		socAusgabe.append(operation.getDbusUnit());
		return socAusgabe.toString();
	}

	private double berechneSkaliertenWert() {
		if (value instanceof Double) {
			return operation.getScaleFactor() * (Double) value;
		} else if (value instanceof Float) {
			return operation.getScaleFactor() * (Float) value;
		} else if (value instanceof Long) {
			return operation.getScaleFactor() * (Long) value;
		} else if (value instanceof Integer) {
			return operation.getScaleFactor() * (Integer) value;
		}
		throw new RuntimeException("Datentyp nicht erkannt.");
	}

}
