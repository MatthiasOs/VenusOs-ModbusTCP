package de.ossi.wolfsbau.modbus.data;

public enum DBusUnit {
	W("W"),

	V_DC("V DC"),

	A_DV("A DC"),

	PERCENT("%"),

	AH("Ah"),

	S("s"),

	// TODO

	RELAY_STATE(""),

	BATTERY_STATE(""),

	SOURCE(""),;

	private final String name;

	private DBusUnit(String name) {
		this.name = name;
	}

	/**
	 * 0=Open;1=Closed
	 */
	private enum RelayState {
		OPEN(0), CLOSED(1);
		private final int value;

		private RelayState(int value) {
			this.value = value;
		}
	}

	/**
	 * 0=idle;1=charging;2=discharging
	 */
	private enum BatteryState {
		IDLE(0), CHARGING(1), DISCHARGING(2);
		private final int value;

		private BatteryState(int value) {
			this.value = value;
		}
	}

	/**
	 * 0=Not available;1=Grid;2=Generator;3=Shore power;240=Not connected
	 */
	private enum Source {
		NOT_AVAILABLE(0), GRID(1), GENERATOR(2), SHORE_POWER(3), NOT_CONNECTED(240);
		private final int value;

		private Source(int value) {
			this.value = value;
		}
	}
}
