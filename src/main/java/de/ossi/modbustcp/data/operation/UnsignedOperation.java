package de.ossi.modbustcp.data.operation;

import de.ossi.modbustcp.data.unit.DBusUnit;

public class UnsignedOperation extends ModbusOperation {

	UnsignedOperation(Category category, int address, String description, double scaleFactor, DBusUnit dbusUnit, AccessMode mode) {
		super(category, address, description, scaleFactor, dbusUnit, mode);
	}

	@Override
	public Double getScaledValueInRange(Integer registerValue) {
		return scaleValue(Double.valueOf(registerValue));
	}

}
