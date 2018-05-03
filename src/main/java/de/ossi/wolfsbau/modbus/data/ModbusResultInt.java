package de.ossi.wolfsbau.modbus.data;

import java.time.LocalDateTime;

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
		ausgabe.append(ermittleWertMitUnitAusgabe());
		ausgabe.append(System.lineSeparator());
		return ausgabe.toString();
	}

	private String ermittleWertMitUnitAusgabe() {
		switch (operation.getDbusUnit()) {
		case RELAY_STATE:
			return relayStateToString();
		case BATTERY_STATE:
			return batteryStateToString();
		case SOURCE:
			return sourceToString();
		default:
			return new StringBuilder().append(operation.getWert(this.wert)).append(" ")
					.append(operation.getDbusUnit().toString()).toString();
		}
	}

	/** 0=Available;1=Grid;2=Generator;3=Shore Power;240=Not Connected **/
	private String sourceToString() {
		switch (wert) {
		case 0:
			return "Not Available";
		case 1:
			return "Grid";
		case 2:
			return "Generator";
		case 3:
			return "Shore Power";
		case 240:
			return "Not Connected";

		}
		return "Unknown Unit!";
	}

	/** 0=Idle;1=Charging;2=Discharging **/
	private String batteryStateToString() {
		switch (wert) {
		case 0:
			return "Idle";
		case 1:
			return "Charging";
		case 2:
			return "Discharging";
		}
		return "Unknown Battery State!";
	}

	/** 0=Open;1=Closed **/
	private String relayStateToString() {
		switch (wert) {
		case 0:
			return "Open";
		case 1:
			return "Closed";
		}
		return "Unknown Relay State!";
	}

}
