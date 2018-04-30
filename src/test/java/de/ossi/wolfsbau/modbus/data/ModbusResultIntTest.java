package de.ossi.wolfsbau.modbus.data;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * GRID_L1_POWER: 
 * - MIN_SIGNED = -32768D; 
 * - MAX_SIGNED = 32767D; 
 * AC_CONSUMPTION_L1:
 * - MIN_UNSIGNED = 0D; 
 * - MAX_UNSIGNED = 65335D;
 * 
 * @author ossi
 *
 */
public class ModbusResultIntTest {

	private static final Double MAX_GRID_L1_POWER = ModbusOperation.GRID_L1_POWER.getWertRange().getMinimum(); //32767
	private static final Double MIN_GRID_L1_POWER = ModbusOperation.GRID_L1_POWER.getWertRange().getMinimum();//-32768
	private static final Double MAX_AC_CONSUMPTION_L1 = ModbusOperation.AC_CONSUMPTION_L1.getWertRange().getMaximum();//65335
	private static final Double MIN_AC_CONSUMPTION_L1 = ModbusOperation.AC_CONSUMPTION_L1.getWertRange().getMinimum(); //0
	
	//signed
	@Test
	public void registerMinSignedRangeEntsprichtZero() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, 0);
		assertThat(result.berechneSkaliertenWertInRange(), Matchers.is(0D));
	}

	@Test
	public void registerMaxSignedRangeEntsprichtMax() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, MAX_GRID_L1_POWER.intValue());
		assertThat(result.berechneSkaliertenWertInRange(), Matchers.is(MAX_GRID_L1_POWER));
	}

	@Test
	public void registerEinsUeberSignedRangeMaxEntsprichtMinRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, MAX_GRID_L1_POWER.intValue()+1);
		assertThat(result.berechneSkaliertenWertInRange(), Matchers.is(MIN_GRID_L1_POWER));
	}
	
	@Test
	public void registerMaxSingedRangeEntsprichtMinusEinsRange() throws Exception{
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, 65335);
		assertThat(result.berechneSkaliertenWertInRange(), Matchers.is(-1));
	}
	
	//unsigned
	@Test
	public void registerMinUnsignedRangeEntsprichtMinRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.AC_CONSUMPTION_L1, 0);
		assertThat(result.berechneSkaliertenWertInRange(), Matchers.is(MIN_AC_CONSUMPTION_L1));
	}
	
	@Test
	public void registerMaxEntsprichtMaxUnsignedRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.AC_CONSUMPTION_L1, 65335);
		assertThat(result.berechneSkaliertenWertInRange(), Matchers.is(Double.valueOf(MAX_AC_CONSUMPTION_L1)));
	}
	
	@Test
	public void registerEinsUeberSignedRangeMaxEntsprichtWertBeiUnsignedRange() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.AC_CONSUMPTION_L1, MAX_GRID_L1_POWER.intValue()+1);
		assertThat(result.berechneSkaliertenWertInRange(), Matchers.is(MAX_GRID_L1_POWER+1));
	}
	
	@Test
	public void registerMaxUnsingedRangeEntsprichtMaxRange() throws Exception{
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.AC_CONSUMPTION_L1, MAX_AC_CONSUMPTION_L1.intValue());
		assertThat(result.berechneSkaliertenWertInRange(), Matchers.is(MAX_AC_CONSUMPTION_L1));
	}
	
}
