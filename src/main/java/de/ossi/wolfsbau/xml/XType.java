package de.ossi.wolfsbau.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)
public enum XType {
	@XmlEnumValue("AC_Voltage")
	AC_VOLTAGE(0, "AC_Voltage"),

	@XmlEnumValue("AC_Current")
	AC_CURRENT(1, "AC_Current"),

	@XmlEnumValue("AC_Power")
	AC_POWER(2, "AC_Power"),

	@XmlEnumValue("AC_Frequency")
	AC_FREQUENCY(3, "AC_Frequency"),

	@XmlEnumValue("DC_Voltage")
	DC_VOLTAGE(4, "DC_Voltage"),

	@XmlEnumValue("DC_Current")
	DC_CURRENT(5, "DC_Current"),

	@XmlEnumValue("Temp")
	TEMP(6, "Temp"),

	@XmlEnumValue("GridPower")
	GRID_POWER(7, "GridPower"),

	@XmlEnumValue("Derating")
	DERATING(8, "Derating");

	private final String name;
	private final int ordinal;

	XType(final int ordinal, final String name) {
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
