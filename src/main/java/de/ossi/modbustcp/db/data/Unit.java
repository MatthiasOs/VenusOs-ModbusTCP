package de.ossi.modbustcp.db.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public enum Unit {
	// all persisted classes must define a no-arg constructor
	// with at least package visibility
	V("V"),

	A("A"),

	W("W"),

	HZ("Hz"),

	C("°C"),

	PERCENT("%");

	private String name;

}
