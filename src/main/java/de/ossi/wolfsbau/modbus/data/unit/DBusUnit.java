package de.ossi.wolfsbau.modbus.data.unit;

import java.util.HashMap;
import java.util.Map;

public class DBusUnit {

	public static final DBusUnit V = new DBusUnit("V");
	public static final DBusUnit V_AC = new DBusUnit("V AC");
	public static final DBusUnit V_DC = new DBusUnit("V DC");
	public static final DBusUnit A_AC = new DBusUnit("A AC");
	public static final DBusUnit A_DC = new DBusUnit("A DC");
	public static final DBusUnit A = new DBusUnit("A");
	public static final DBusUnit W = new DBusUnit("W");
	public static final DBusUnit HZ = new DBusUnit("Hz");
	public static final DBusUnit KWH = new DBusUnit("kWh");
	public static final DBusUnit VA_OR_WATTS = new DBusUnit("VA or Watts");
	public static final DBusUnit PERCENT = new DBusUnit("%");
	public static final DBusUnit ACTIVE_INPUT = new ActiveInput("Active Input");
	public static final DBusUnit RELAY_STATE = new RelayState("Relay State");
	public static final DBusUnit SWITCH_POSITION = new SwitchPosition("Switch Position");
	public static final DBusUnit ALARM = new Alarm("Alarm");
	public static final DBusUnit CHARGE_FLAG = new ChargeFlag("Charge Flag");
	public static final DBusUnit FEEDBACK_FLAG = new FeedbackFlag("Feedback Flag");
	public static final DBusUnit PV_STATUS = new PvStatus("PV Status");
	public static final DBusUnit SOURCE = new Source("Source");
	public static final DBusUnit BATTERY_STATE = new BatteryState("Battery State");
	public static final DBusUnit AH = new DBusUnit("Ah");
	public static final DBusUnit S = new DBusUnit("s");
	public static final DBusUnit CELSIUS = new DBusUnit("Degrees celsius");
	public static final DBusUnit CHARGER_ON_OFF = new ChargerOnOff("Charger On/Off");
	public static final DBusUnit CHARGE_STATE = new ChargeState("Charge state");

	private final String name;
	protected final Map<Integer, String> values = new HashMap<>();

	protected DBusUnit(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Gibt den speziellen Wert (zB On, oder Off) zu einem Messwert zurück, 
	 * wenn es fuer diese {@link DBusUnit} spezielle Werte gibt.
	 * Falls die Methode aufgerufen wird, wenn es keine speziellen Werte gibt,
	 * oder keine Ausprägung für den Messwert gefunden wird, 
	 * wird ein String mit Unbekannter Einheit zurück gegeben.
	 */
	public String getMesswertMitEinheit(Integer messwert) {
		String einheit = values.get(messwert);
		return einheit != null ? einheit : unbekannteEinheit();
	}

	public boolean hatSpezielleEinheit() {
		return !values.isEmpty();
	}

	private String unbekannteEinheit() {
		return new StringBuilder().append("Unbekannte Einheit: ").append(name).toString();
	}
}
