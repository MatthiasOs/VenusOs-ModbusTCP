package de.ossi.wolfsbau.db.data;

public enum Type {
	AC_VOLTAGE("AC_Voltage"),

	AC_CURRENT("AC_Current"),

	AC_POWER("AC_Power"),

	AC_FREQUENCY("AC_Frequency"),

	DC_VOLTAGE("DC_Voltage"),

	DC_CURRENT("DC_Current"),

	TEMP("Temp"),

	GRID_POWER("GridPower"),

	DERATING("Derating");

	private String name;

	Type() {
		// all persisted classes must define a no-arg constructor
		// with at least package visibility
	}

	Type(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
