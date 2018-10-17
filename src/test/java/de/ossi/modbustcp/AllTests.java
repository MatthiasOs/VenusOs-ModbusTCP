package de.ossi.modbustcp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.ossi.modbustcp.db.DBModelTest;
import de.ossi.modbustcp.db.util.EnumPairTest;
import de.ossi.modbustcp.db.util.XMLtoDBConverterTest;
import de.ossi.modbustcp.inverter.InverterRequesterTest;
import de.ossi.modbustcp.modbus.data.operation.ModbusOperationBatteryTest;
import de.ossi.modbustcp.modbus.data.operation.ModbusOperationGridTest;
import de.ossi.modbustcp.modbus.data.operation.ModbusOperationSolarTest;
import de.ossi.modbustcp.modbus.data.operation.ModbusOperationSystemTest;
import de.ossi.modbustcp.modbus.data.operation.ModbusOperationTemperatureTest;
import de.ossi.modbustcp.modbus.data.operation.ModbusOperationVebusTest;
import de.ossi.modbustcp.parser.WRAntwortParserTest;

@RunWith(Suite.class)
@SuiteClasses({ WRAntwortParserTest.class, DBModelTest.class, InverterRequesterTest.class, EnumPairTest.class, XMLtoDBConverterTest.class, ModbusOperationSystemTest.class,
		ModbusOperationGridTest.class, ModbusOperationVebusTest.class, ModbusOperationBatteryTest.class, ModbusOperationSolarTest.class, ModbusOperationTemperatureTest.class })
public class AllTests {

}
