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
		String wertMitEinheit = null;
		switch (operation.getDbusUnit()) {
		case RELAY_STATE:
			wertMitEinheit = relayState();
		case BATTERY_STATE:
			wertMitEinheit = batteryState();
		case SOURCE:
			wertMitEinheit = source();
		case ACTIVE_INPUT:
			wertMitEinheit = activeInput();
		case SWITCH_POSITION:
			wertMitEinheit = switchPosition();
		case ALARM:
			wertMitEinheit = alarm();
		case CHARGE_FLAG:
			wertMitEinheit = chargeFlag();
		case FEEDBACK_FLAG:
			wertMitEinheit = feedbackFlag();
		case PV:
			wertMitEinheit=pv();
		default:
			wertMitEinheit = new StringBuilder().append(operation.getSkaliertenWertInWertebreich(wert)).append(" ").append(operation.getDbusUnit().toString()).toString();
		}

		return wertMitEinheit != null ? wertMitEinheit : unbekannteEinheit(operation.getDbusUnit());
	}

	/**0=PV enabled;1=PV disabled*/
	private String pv() {
		switch (wert) {
		case 1:
			return "PV enabled";
		case 2:
			return "PV disabled";
		}
		return null;
	}

	/**0=Feed in allowed;1=Feed in disabled*/
	private String  feedbackFlag() {
		switch (wert) {
		case 1:
			return "Feed in allowed";
		case 2:
			return "Feed in disabled";
		}
		return null;
	}

	/**0=Charge allowed;1=Charge disabled*/
	private String chargeFlag() {
		switch (wert) {
		case 1:
			return "Charge allowed";
		case 2:
			return "Charge disabled";
		}
		return null;
	}

	private String unbekannteEinheit(DBusUnit dbusUnit) {
		return new StringBuilder().append("Unbekannte Einheit: ").append(dbusUnit.getName()).toString();
	}

	/** 1=Charger Only;2=Inverter Only;3=On;4=Off */
	private String switchPosition() {
		switch (wert) {
		case 1:
			return "Charger Only";
		case 2:
			return "Inverter Only";
		case 3:
			return "On";
		case 4:
			return "Off";
		}
		return null;
	}

	/** 0=Available;1=Grid;2=Generator;3=Shore Power;240=Not Connected **/
	private String source() {
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
		return null;
	}

	/** 0=Idle;1=Charging;2=Discharging **/
	private String batteryState() {
		switch (wert) {
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
	private String relayState() {
		switch (wert) {
		case 0:
			return "Open";
		case 1:
			return "Closed";
		}
		return null;
	}

	/** 0=AC Input 1;1=AC Input 2;240=Disconnected */
	private String activeInput() {
		switch (wert) {
		case 0:
			return "AC Input 1";
		case 1:
			return "AC Input 2";
		case 240:
			return "Disconnected";
		}
		return null;
	}

	/** 0=Ok;1=Warning;2=Alarm */
	private String alarm() {
		switch (wert) {
		case 0:
			return "Ok";
		case 1:
			return "Warning";
		case 2:
			return "Alarm";
		}
		return null;
	}

}
