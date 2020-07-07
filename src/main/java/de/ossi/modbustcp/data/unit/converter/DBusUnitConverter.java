package de.ossi.modbustcp.data.unit.converter;

import com.creditdatamw.zerocell.converter.Converter;

import de.ossi.modbustcp.data.unit.DBusUnit;

public class DBusUnitConverter implements Converter<DBusUnit> {

	@Override
	public DBusUnit convert(String value, String columnName, int row) {
		return new DBusUnit(value);
	}
}
