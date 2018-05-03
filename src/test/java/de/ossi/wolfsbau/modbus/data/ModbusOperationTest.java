package de.ossi.wolfsbau.modbus.data;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

public class ModbusOperationTest {
	private static final int MAX_SIGNED = 32767;
	private static final int MIN_SIGNED = -32768;
	private static final int MAX_UNSIGNED = 65535;
	private static final int MIN_UNSIGNED = 0;

	// signed
	@Test
	public void registerMinSignedRangeEntsprichtZero() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, MIN_UNSIGNED);
		assertThat(ModbusOperation.GRID_L1_POWER.getWert(result.getWert()), Matchers.is(Double.valueOf(MIN_UNSIGNED)));
	}

	@Test
	public void registerMaxSignedRangeEntsprichtMax() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, MAX_SIGNED);
		assertThat(ModbusOperation.GRID_L1_POWER.getWert(result.getWert()), Matchers.is(Double.valueOf(MAX_SIGNED)));
	}

	@Test
	public void registerEinsUeberSignedRangeMaxEntsprichtMinRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, MAX_SIGNED + 1);
		assertThat(ModbusOperation.GRID_L1_POWER.getWert(result.getWert()), Matchers.is(Double.valueOf(MIN_SIGNED)));
	}

	@Test
	public void registerMaxSingedRangeEntsprichtMinusEinsRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, MAX_UNSIGNED);
		assertThat(ModbusOperation.GRID_L1_POWER.getWert(result.getWert()), Matchers.is(-1D));
	}

	// unsigned
	@Test
	public void registerMinUnsignedRangeEntsprichtMinRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.AC_CONSUMPTION_L1, 0);
		assertThat(ModbusOperation.AC_CONSUMPTION_L1.getWert(result.getWert()), Matchers.is(0D));
	}

	@Test
	public void registerMaxEntsprichtMaxUnsignedRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.AC_CONSUMPTION_L1, MAX_UNSIGNED);
		assertThat(ModbusOperation.AC_CONSUMPTION_L1.getWert(result.getWert()), Matchers.is(Double.valueOf(MAX_UNSIGNED)));
	}

	@Test
	public void registerEinsUeberSignedRangeMaxEntsprichtWertBeiUnsignedRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.AC_CONSUMPTION_L1, MAX_SIGNED + 1);
		assertThat(ModbusOperation.AC_CONSUMPTION_L1.getWert(result.getWert()), Matchers.is(Double.valueOf(MAX_SIGNED + 1)));
	}
}
