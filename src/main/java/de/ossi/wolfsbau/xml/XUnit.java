package de.ossi.wolfsbau.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)
public enum XUnit {
	@XmlEnumValue("V")
	V(0, "V"),

	@XmlEnumValue("A")
	A(1, "A"),

	@XmlEnumValue("W")
	W(2, "W"),

	@XmlEnumValue("Hz")
	HZ(3, "Hz"),

	@XmlEnumValue("°C")
	C(4, "°C"),

	@XmlEnumValue("%")
	PERCENT(5, "%");

	private final String name;
	private final int ordinal;

	XUnit(final int ordinal, final String name) {
		this.name = name;
		this.ordinal = ordinal;
	}

	public String getName() {
		return name;
	}

	public int getOrdinal() {
		return ordinal;
	}

	@Override
	public String toString() {
		return name;
	}
}
