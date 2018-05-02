package de.ossi.wolfsbau.modbus;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

import de.ossi.wolfsbau.modbus.data.ModbusOperation;
import de.ossi.wolfsbau.modbus.data.ModbusResultInt;

/**
 * @author ossi
 *
 */
public class RegisterZuWertUmwandlerTest {
	private static final int MAX_SIGNED = 32767;
	private static final int MIN_SIGNED = -32768;
	private static final int MAX_UNSIGNED = 65535;
	private final RegisterZuWertUmwandler umwandler = new RegisterZuWertUmwandler();

	// signed
	@Test
	public void registerMinSignedRangeEntsprichtZero() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, 0);
		assertThat(umwandler.getWert(result), Matchers.is(0D));
	}

	@Test
	public void registerMaxSignedRangeEntsprichtMax() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, MAX_SIGNED);
		assertThat(umwandler.getWert(result), Matchers.is(Double.valueOf(MAX_SIGNED)));
	}

	@Test
	public void registerEinsUeberSignedRangeMaxEntsprichtMinRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, MAX_SIGNED + 1);
		assertThat(umwandler.getWert(result), Matchers.is(Double.valueOf(MIN_SIGNED)));
	}

	@Test
	public void registerMaxSingedRangeEntsprichtMinusEinsRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, MAX_UNSIGNED);
		assertThat(umwandler.getWert(result), Matchers.is(-1D));
	}

	// unsigned
	@Test
	public void registerMinUnsignedRangeEntsprichtMinRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.AC_CONSUMPTION_L1, 0);
		assertThat(umwandler.getWert(result), Matchers.is(0D));
	}

	@Test
	public void registerMaxEntsprichtMaxUnsignedRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.AC_CONSUMPTION_L1, MAX_UNSIGNED);
		assertThat(umwandler.getWert(result), Matchers.is(Double.valueOf(MAX_UNSIGNED)));
	}

	@Test
	public void registerEinsUeberSignedRangeMaxEntsprichtWertBeiUnsignedRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.AC_CONSUMPTION_L1, MAX_SIGNED + 1);
		assertThat(umwandler.getWert(result), Matchers.is(Double.valueOf(MAX_SIGNED + 1)));
	}
}
