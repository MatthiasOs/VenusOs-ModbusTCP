package de.ossi.wolfsbau.modbus;

import de.ossi.wolfsbau.modbus.data.ModbusOperation;
import de.ossi.wolfsbau.modbus.data.ModbusResultInt;

public class RegisterZuWertUmwandler {

	private static final double MAX_SIGNED = 32767D;

	/**
	 * =WENN(I4="nur postive Zahlen";J4;WENN(J4>H4;-J4+H4-1;J4))
	 */
	public double getWert(ModbusResultInt result) {
		double skalierterWert = skaliereWert(result);
		if (result.getOperation().getVorzeichen().equals(ModbusOperation.Sign.SIGNED)) {
			return skalierterWert > MAX_SIGNED ? MAX_SIGNED - skalierterWert -1 : skalierterWert;
		}
		// Unsigned
		return skalierterWert;
	}

	private double skaliereWert(ModbusResultInt result) {
		return result.getWert() / result.getOperation().getScaleFactor();
	}
}
