package de.ossi.wolfsbau.modbus.data;

public class ModbusUnsignedOperation extends ModbusOperation {

	ModbusUnsignedOperation(int address, String description, double scaleFactor, DBusUnit dbusUnit) {
		super(address, description, scaleFactor, dbusUnit);
	}

	@Override
	public Double getWert(Integer registerWert) {
		return skaliereWert(registerWert);
	}

}
