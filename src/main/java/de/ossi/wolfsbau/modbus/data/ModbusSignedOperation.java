package de.ossi.wolfsbau.modbus.data;

public class ModbusSignedOperation extends ModbusOperation {
	private static final double MAX_SIGNED = 32767D;
	private static final double MAX_REGISTER = 65535D;

	ModbusSignedOperation(int address, String description, double scaleFactor, DBusUnit dbusUnit) {
		super(address, description, scaleFactor, dbusUnit);
	}

	@Override
	public Double getWert(Integer registerWert) {
		double skalierterWert = skaliereWert(registerWert);
		return skalierterWert > MAX_SIGNED ? skalierterWert - MAX_REGISTER -1  : skalierterWert;
	}

}
