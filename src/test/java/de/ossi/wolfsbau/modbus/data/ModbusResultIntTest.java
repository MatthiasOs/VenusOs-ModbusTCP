package de.ossi.wolfsbau.modbus.data;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Schreibtischtests:
 * {Cloud}\DE_WO_Wolfsbau Speicher\3_Technical_offer\Victron\Schreibtischtests_Modbus.xlsx 
 * @author ossi
 *
 */
public class ModbusResultIntTest {

	private static final Double MIN_GRID_L1_POWER = ModbusOperation.GRID_L1_POWER.getWertRange().getMinimum();// -32768
	private static final Double MAX_GRID_L1_POWER = ModbusOperation.GRID_L1_POWER.getWertRange().getMaximum(); // 32767
	private static final Double MIN_AC_CONSUMPTION_L1 = ModbusOperation.AC_CONSUMPTION_L1.getWertRange().getMinimum(); // 0
	private static final Double MAX_AC_CONSUMPTION_L1 = ModbusOperation.AC_CONSUMPTION_L1.getWertRange().getMaximum();// 65335

	// signed
	/**
	 * Schreibtischtest #1
	 */
	@Test
	public void registerMinSignedRangeEntsprichtZero() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, 0);
		assertThat(result.berechneSkaliertenWertInRange(), Matchers.is(0D));
	}

	/**
	 * Schreibtischtest #2
	 */
	@Test
	public void registerMaxSignedRangeEntsprichtMax() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, MAX_GRID_L1_POWER.intValue());
		assertThat(result.berechneSkaliertenWertInRange(), Matchers.is(MAX_GRID_L1_POWER));
	}

	/**
	 * Schreibtischtest #3 
	 * java.lang.AssertionError: 
	 * Expected: is <-32768.0> 
	 * but: was <-32766.0>
	 * 
	 */
	@Test
	public void registerEinsUeberSignedRangeMaxEntsprichtMinRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, MAX_GRID_L1_POWER.intValue() + 1);
		assertThat(result.berechneSkaliertenWertInRange(), Matchers.is(MIN_GRID_L1_POWER));
	}

	/**
	 * Schreibtischtest #4 
	 * java.lang.AssertionError: 
	 * Expected: is <-1> 
	 * but: was <-199.0>
	 * 
	 */
	@Test
	public void registerMaxSingedRangeEntsprichtMinusEinsRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, 65335);
		assertThat(result.berechneSkaliertenWertInRange(), Matchers.is(-1));
	}

	// unsigned
	/**
	 * Schreibtischtest #5
	 */
	@Test
	public void registerMinUnsignedRangeEntsprichtMinRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.AC_CONSUMPTION_L1, 0);
		assertThat(result.berechneSkaliertenWertInRange(), Matchers.is(MIN_AC_CONSUMPTION_L1));
	}

	/**
	 * Schreibtischtest #6
	 */
	@Test
	public void registerMaxEntsprichtMaxUnsignedRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.AC_CONSUMPTION_L1, MAX_AC_CONSUMPTION_L1.intValue());
		assertThat(result.berechneSkaliertenWertInRange(), Matchers.is(Double.valueOf(MAX_AC_CONSUMPTION_L1)));
	}

	/**
	 * Schreibtischtest #7
	 */
	@Test
	public void registerEinsUeberSignedRangeMaxEntsprichtWertBeiUnsignedRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.AC_CONSUMPTION_L1, MAX_GRID_L1_POWER.intValue() + 1);
		assertThat(result.berechneSkaliertenWertInRange(), Matchers.is(MAX_GRID_L1_POWER + 1));
	}
}
