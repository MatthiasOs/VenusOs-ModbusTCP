package de.ossi.modbustcp.data.unit.converter;

import com.creditdatamw.zerocell.converter.Converter;

import de.ossi.modbustcp.data.unit.Type;

public class TypeConverter implements Converter<Type> {

	@Override
	public Type convert(String value, String columnName, int row) {
		return Type.from(value);
	}

}
