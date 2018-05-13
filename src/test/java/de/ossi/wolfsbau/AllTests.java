package de.ossi.wolfsbau;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.ossi.wolfsbau.anfrager.WRAnfragerTest;
import de.ossi.wolfsbau.db.DBModelTest;
import de.ossi.wolfsbau.db.util.EnumPairTest;
import de.ossi.wolfsbau.db.util.XMLtoDBConverterTest;
import de.ossi.wolfsbau.modbus.data.operation.ModbusOperationBatteryTest;
import de.ossi.wolfsbau.modbus.data.operation.ModbusOperationGridTest;
import de.ossi.wolfsbau.modbus.data.operation.ModbusOperationSolarTest;
import de.ossi.wolfsbau.modbus.data.operation.ModbusOperationSystemTest;
import de.ossi.wolfsbau.modbus.data.operation.ModbusOperationTemperatureTest;
import de.ossi.wolfsbau.modbus.data.operation.ModbusOperationVebusTest;
import de.ossi.wolfsbau.parser.WRAntwortParserTest;

@RunWith(Suite.class)
@SuiteClasses({ WRAntwortParserTest.class, DBModelTest.class, WRAnfragerTest.class, EnumPairTest.class, XMLtoDBConverterTest.class, ModbusOperationSystemTest.class,
		ModbusOperationGridTest.class, ModbusOperationVebusTest.class, ModbusOperationBatteryTest.class, ModbusOperationSolarTest.class, ModbusOperationTemperatureTest.class })
public class AllTests {

}
