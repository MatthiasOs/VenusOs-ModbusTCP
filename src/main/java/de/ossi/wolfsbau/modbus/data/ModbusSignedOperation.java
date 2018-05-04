package de.ossi.wolfsbau.modbus.data;

public class ModbusSignedOperation extends ModbusOperation {
	private static final double MAX_SIGNED = 32767D;
	private static final double MAX_REGISTER = 65535D;

	ModbusSignedOperation(int address, String description, double scaleFactor, DBusUnit dbusUnit) {
		super(address, description, scaleFactor, dbusUnit);
	}

	@Override
	public Double getSkaliertenWertInWertebreich(Integer registerWert) {
		double wertInRange = registerWert > MAX_SIGNED ? registerWert - MAX_REGISTER -1  : registerWert;
		return skaliereWert(wertInRange);
	}

}
