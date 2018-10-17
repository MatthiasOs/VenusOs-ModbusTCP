package de.ossi.modbustcp.db.data;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@DatabaseTable(tableName = "measurement")
public class Measurement {
	// all persisted classes must define a no-arg constructor
	// with at least package visibility

	@DatabaseField(generatedId = true)
	private long id;

	// FIXME beim E2E Test aufgefallen: DeviceId Spalte ist immer null
	@Setter
	@DatabaseField(foreign = true)
	private Device device;

	@Setter
	@DatabaseField(dataType = DataType.ENUM_STRING)
	private Unit unit;
	@Setter
	@DatabaseField(dataType = DataType.ENUM_STRING)
	private Type type;
	@Setter
	@DatabaseField
	private Double value;
}
