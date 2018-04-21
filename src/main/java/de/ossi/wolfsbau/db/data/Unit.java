package de.ossi.wolfsbau.db.data;

public enum Unit {
	V("V"),

	A("A"),

	W("W"),

	HZ("Hz"),

	C("°C"),

	PERCENT("%");

	private String name;

	Unit() {
		// all persisted classes must define a no-arg constructor
		// with at least package visibility
	}

	Unit(final String name) {
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
