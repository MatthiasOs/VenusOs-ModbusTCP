package de.ossi.modbustcp.data.unit;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DBusSpecialUnitParserTest {
	private final DBusUnit dbusUnit = new DBusUnit("W");
	private final DBusUnit dbusSpecialUnit = new DBusUnit("0=No alarm;2=Alarm");
	private final DBusUnit invalidUnitWithSeperator = new DBusUnit("asdsad;asd");
	private final DBusUnit invalidUnit = new DBusUnit("asdsad");

	@Test
	public void parseSpecialUnit0() throws Exception {
		String str = DBusSpecialUnitParser.parse(dbusSpecialUnit, 0);
		assertThat(str).isEqualTo("No alarm");
	}

	@Test
	public void parseSpecialUnit1() throws Exception {
		String str = DBusSpecialUnitParser.parse(dbusSpecialUnit, 2);
		assertThat(str).isEqualTo("Alarm");
	}

	@Test
	public void parseSpecialUnit2() throws Exception {
		String str = DBusSpecialUnitParser.parse(dbusSpecialUnit, 99);
		assertThat(str).isNull();
	}

	@Test
	public void parseInvalidUnitWithSeperator() throws Exception {
		String str = DBusSpecialUnitParser.parse(invalidUnitWithSeperator, 0);
		assertThat(str).isNull();
	}

	@Test
	public void parseInvalidUnit() throws Exception {
		String str = DBusSpecialUnitParser.parse(invalidUnit, 0);
		assertThat(str).isNull();
	}

	@Test
	public void parseUnit() throws Exception {
		String str = DBusSpecialUnitParser.parse(dbusUnit, 0);
		assertThat(str).isNull();
	}

}
