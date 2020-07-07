package de.ossi.modbustcp.data.unit.converter;

import com.creditdatamw.zerocell.converter.Converter;

import de.ossi.modbustcp.data.unit.Category;

public class CategoryConverter implements Converter<Category> {

	@Override
	public Category convert(String value, String columnName, int row) {
		return Category.from(value);
	}

}
