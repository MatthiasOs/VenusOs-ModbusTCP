package de.ossi.wolfsbau.db.data;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "measurement")
public class Measurement {

	@DatabaseField(generatedId = true)
	private long id;

	// FIXME beim E2E Test aufgefallen: DeviceId Spalte ist immer null
	@DatabaseField(foreign = true)
	private Device device;

	@DatabaseField(dataType = DataType.ENUM_STRING)
	private Unit unit;
	@DatabaseField(dataType = DataType.ENUM_STRING)
	private Type type;
	@DatabaseField
	private Double value;

	public Measurement() {
		// all persisted classes must define a no-arg constructor
		// with at least package visibility
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
