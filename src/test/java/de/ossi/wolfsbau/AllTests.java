package de.ossi.wolfsbau;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.ossi.wolfsbau.anfrager.WRAnfragerTest;
import de.ossi.wolfsbau.db.DBModelTest;
import de.ossi.wolfsbau.db.util.EnumPairTest;
import de.ossi.wolfsbau.db.util.XMLtoDBConverterTest;
import de.ossi.wolfsbau.modbus.data.ModbusOperationTest;
import de.ossi.wolfsbau.parser.WRAntwortParserTest;

@RunWith(Suite.class)
@SuiteClasses({ WRAntwortParserTest.class, DBModelTest.class, WRAnfragerTest.class, EnumPairTest.class, XMLtoDBConverterTest.class, ModbusOperationTest.class })
public class AllTests {

}
