package de.ossi.modbustcp.data.unit.converter;

import com.creditdatamw.zerocell.converter.Converter;

import de.ossi.modbustcp.data.unit.AccessMode;

public class AccessModeConverter implements Converter<AccessMode> {

	@Override
	public AccessMode convert(String value, String columnName, int row) {
		return AccessMode.from(value);
	}
}
