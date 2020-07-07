package de.ossi.modbustcp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.ossi.modbustcp.data.OperationDevicesReaderTest;
import de.ossi.modbustcp.data.operation.ModbusOperationBatteryTest;
import de.ossi.modbustcp.data.operation.ModbusOperationGridTest;
import de.ossi.modbustcp.data.operation.ModbusOperationSolarTest;
import de.ossi.modbustcp.data.operation.ModbusOperationSystemTest;
import de.ossi.modbustcp.data.operation.ModbusOperationTemperatureTest;
import de.ossi.modbustcp.data.operation.ModbusOperationVebusTest;
import de.ossi.modbustcp.data.unit.DBusSpecialUnitParserTest;

@RunWith(Suite.class)
@SuiteClasses({ ModbusOperationSystemTest.class, ModbusOperationGridTest.class, ModbusOperationVebusTest.class, ModbusOperationBatteryTest.class, ModbusOperationSolarTest.class,
		ModbusOperationTemperatureTest.class, /* ModbusResultIntTest.class JUnit5 Test, */ OperationDevicesReaderTest.class, DBusSpecialUnitParserTest.class })
public class AllTests {

}
