package de.ossi.wolfsbau.modbus.data;

import de.ossi.wolfsbau.modbus.data.unit.DBusUnit;

public class SignedOperation extends ModbusOperation {
	private static final double MAX_SIGNED = 32767D;
	private static final double MAX_REGISTER = 65535D;

	SignedOperation(int address, String description, double scaleFactor, DBusUnit dbusUnit) {
		super(address, description, scaleFactor, dbusUnit);
	}

	@Override
	public Double getSkaliertenWertInWertebreich(Integer registerWert) {
		double wertInRange = registerWert > MAX_SIGNED ? registerWert - MAX_REGISTER -1  : registerWert;
		return skaliereWert(wertInRange);
	}

}
