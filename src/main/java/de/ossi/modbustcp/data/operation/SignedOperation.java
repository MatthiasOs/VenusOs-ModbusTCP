package de.ossi.modbustcp.data.operation;

import de.ossi.modbustcp.data.unit.DBusUnit;

public class SignedOperation extends ModbusOperation {
	private static final double MAX_SIGNED = 32767D;
	private static final double MAX_REGISTER = 65535D;

	SignedOperation(Category category, int address, String description, double scaleFactor, DBusUnit dbusUnit, AccessMode mode) {
		super(category, address, description, scaleFactor, dbusUnit, mode);
	}

	@Override
	public Double getScaledValueInRange(Integer registerValue) {
		double wertInRange = registerValue > MAX_SIGNED ? registerValue - MAX_REGISTER - 1 : registerValue;
		return scaleValue(wertInRange);
	}

}
