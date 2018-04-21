package de.ossi.wolfsbau.modbus.data;

import java.time.LocalDateTime;

public class ModbusResult<T extends Number> {

	private final ModbusOperation operation;
	private final T wert;
	private final LocalDateTime zeitpunkt;

	public ModbusResult(ModbusOperation operation, T value) {
		this.operation = operation;
		this.wert = value;
		this.zeitpunkt = LocalDateTime.now();
	}

	public ModbusOperation getOperation() {
		return operation;
	}

	public T getWert() {
		return wert;
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
		socAusgabe.append(ermittleDbusUnit());
		return socAusgabe.toString();
	}

	private double berechneSkaliertenWert() {
		if (wert instanceof Double) {
			return operation.getScaleFactor() * (Double) wert;
		} else if (wert instanceof Float) {
			return operation.getScaleFactor() * (Float) wert;
		} else if (wert instanceof Long) {
			return operation.getScaleFactor() * (Long) wert;
		} else if (wert instanceof Integer) {
			return operation.getScaleFactor() * (Integer) wert;
		}
		throw new RuntimeException("Datentyp nicht erkannt.");
	}

	private String ermittleDbusUnit() {
		switch (operation.getDbusUnit()) {
		case RELAY_STATE:
			return relayStateToString();
		case BATTERY_STATE:
			return batteryStateToString();
		case SOURCE:
			return sourceToString();
		default:
			return operation.getDbusUnit().toString();
		}
	}

	// TODO gibt es einen besseren Weg als hier davon auszugehen, dass der wert auch
	// wirklich in eine int Zahl passt?
	/** 0=Available;1=Grid;2=Generator;3=Shore Power;240=Not Connected **/
	private String sourceToString() {
		switch ((int) wert) {
		case 0:
			return "Available";
		case 1:
			return "Grid";
		case 2:
			return "Generator";
		case 3:
			return "Shore Power";
		case 240:
			return "Not Connected";

		}
		return null;
	}

	/** 0=Idle;1=Charging;2=Discharging **/
	private String batteryStateToString() {
		switch ((int) wert) {
		case 0:
			return "Idle";
		case 1:
			return "Charging";
		case 2:
			return "Discharging";
		}
		return null;
	}

	/** 0=Open;1=Closed **/
	private String relayStateToString() {
		switch ((int) wert) {
		case 0:
			return "Open";
		case 1:
			return "Closed";
		}
		return null;
	}

}
