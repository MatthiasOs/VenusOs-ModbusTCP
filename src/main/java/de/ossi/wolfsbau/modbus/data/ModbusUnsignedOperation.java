package de.ossi.wolfsbau.modbus.data;

import de.ossi.wolfsbau.modbus.data.unit.DBusUnit;

public class ModbusUnsignedOperation extends ModbusOperation {

	ModbusUnsignedOperation(int address, String description, double scaleFactor, DBusUnit dbusUnit) {
		super(address, description, scaleFactor, dbusUnit);
	}

	@Override
	public Double getSkaliertenWertInWertebreich(Integer registerWert) {
		return skaliereWert(Double.valueOf(registerWert));
	}

}
