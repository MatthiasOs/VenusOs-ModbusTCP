package de.ossi.wolfsbau.modbus.data;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

public class ModbusResultIntTest {

	private static final Integer MAX_GRID_L1_POWER = 32668;
	private static final Integer MIN_GRID_L1_POWER = -32668;
	private static final Integer MAX_AC_CONSUMPTION_L1 = 65336;
	private static final Integer MIN_AC_CONSUMPTION_L1 = 0;

	@Test
	public void ermittleWertIstZero() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, 0);
		assertThat(result.ermittleWertInRange(), Matchers.is(0));
	}

	@Test
	public void ermittleWertInNegativerRangeIstGenauMax() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, MAX_GRID_L1_POWER);
		assertThat(result.ermittleWertInRange(), Matchers.is(MAX_GRID_L1_POWER));
	}

	@Test
	public void ermittleWertInNegativerRangeIstGenauMin() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, MIN_GRID_L1_POWER);
		assertThat(result.ermittleWertInRange(), Matchers.is(MIN_GRID_L1_POWER));
	}

	@Test
	public void ermittleWertInNegativerRangeIstKleinerAlsMin() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, MIN_GRID_L1_POWER - 1);
		assertThat(result.ermittleWertInRange(), Matchers.is(MAX_GRID_L1_POWER - 1));
	}

	@Test
	public void ermittleWertInNegativerRangeIstGroeserAlsMax() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.GRID_L1_POWER, MAX_GRID_L1_POWER + 1);
		assertThat(result.ermittleWertInRange(), Matchers.is(MIN_GRID_L1_POWER + 1));
	}

	@Test
	public void ermittleWertInRangeIstGroeserAlsMax() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.AC_CONSUMPTION_L1, MAX_AC_CONSUMPTION_L1 + 1);
		assertThat(result.ermittleWertInRange(), Matchers.is(MIN_AC_CONSUMPTION_L1 + 1));
	}

	@Test
	public void ermittleWertInRangeIstKleinerAlsMin() throws Exception {
		ModbusResultInt result = new ModbusResultInt(ModbusOperation.AC_CONSUMPTION_L1, MIN_AC_CONSUMPTION_L1 - 1);
		assertThat(result.ermittleWertInRange(), Matchers.is(MAX_AC_CONSUMPTION_L1 - 1));
	}

}
