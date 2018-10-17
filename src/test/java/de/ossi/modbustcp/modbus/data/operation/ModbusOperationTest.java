package de.ossi.modbustcp.modbus.data.operation;

import static java.lang.Double.valueOf;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameter;

import de.ossi.modbustcp.modbus.data.operation.ModbusOperation;
import de.ossi.modbustcp.modbustcp.data.ModbusResultInt;

/**
 * Definiert die Tests für die ModbusOperationen. Die erbenden Tests müssen nur
 * die public static data Methode implementieren mit den Daten zum Testen. Man
 * kann sie die statische Methode nicht abstrakt machen.
 * 
 * @author ossi
 *
 */
public abstract class ModbusOperationTest {

	protected static final int MAX_SIGNED = 32767;
	protected static final int MIN_SIGNED = -32768;
	protected static final int MAX_UNSIGNED = 65535;
	protected static final int MIN_UNSIGNED = 0;

	@Parameter(0)
	public ModbusOperation operation;

	@Parameter(1)
	public Integer registerwert;

	@Parameter(2)
	public Double messwert;

	@Test
	public void pruefeDaten() throws Exception {
		ModbusResultInt result = new ModbusResultInt(operation, registerwert);
		assertThat(operation.getSkaliertenWertInWertebreich(result.getWert()), Matchers.is(valueOf(messwert)));
	}

	protected static double d(Integer i) {
		return Double.valueOf(i);
	}
}
