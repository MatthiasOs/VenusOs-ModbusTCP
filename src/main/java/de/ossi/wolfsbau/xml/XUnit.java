package de.ossi.wolfsbau.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@XmlType
@XmlEnum(String.class)
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
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

	@ToString.Exclude
	private final int ordinal;
	private final String name;
}
