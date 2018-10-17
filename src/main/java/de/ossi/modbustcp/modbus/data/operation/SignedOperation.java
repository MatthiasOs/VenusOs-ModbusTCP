package de.ossi.modbustcp.modbus.data.operation;

import de.ossi.modbustcp.modbus.data.unit.DBusUnit;

public class SignedOperation extends ModbusOperation {
	private static final double MAX_SIGNED = 32767D;
	private static final double MAX_REGISTER = 65535D;

	SignedOperation(Category category, int address, String description, double scaleFactor, DBusUnit dbusUnit, AccessMode mode) {
		super(category, address, description, scaleFactor, dbusUnit, mode);
	}

	@Override
	public Double getSkaliertenWertInWertebreich(Integer registerWert) {
		double wertInRange = registerWert > MAX_SIGNED ? registerWert - MAX_REGISTER - 1 : registerWert;
		return skaliereWert(wertInRange);
	}

}
