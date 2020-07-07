package de.ossi.modbustcp.data.unit.converter;

import com.creditdatamw.zerocell.converter.Converter;

public class DoubleConverter implements Converter<Double> {

	@Override
	public Double convert(String value, String columnName, int row) {
		// Replace the German Seperator "," with the US "." so the double can we parsed
		return Double.parseDouble(value.replace(",", "."));
	}

}
