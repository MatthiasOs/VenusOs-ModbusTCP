package de.ossi.modbustcp.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import de.ossi.modbustcp.parser.WRAntwortParser;
import de.ossi.modbustcp.xml.XDevice;
import de.ossi.modbustcp.xml.XMeasurement;
import de.ossi.modbustcp.xml.XRoot;
import de.ossi.modbustcp.xml.XType;
import de.ossi.modbustcp.xml.XUnit;

public class WRAntwortParserTest {

	private final WRAntwortParser parser = new WRAntwortParser();

	@Test
	public void parsenLiesstWerteAusXMLFileEin() throws JAXBException, IOException {
		InputStream inputStream = new FileInputStream("res/TestAntwort.xml");
		Reader reader = new InputStreamReader(inputStream, "UTF-8");
		XRoot root = parser.leseEin(reader);
		assertNotNull(root);
		XDevice dev = root.getDevice();
		assertNotNull(dev);
		assertDevice(dev, "StecaGrid 2500", new Integer(2500), "Inverter", "754523BD005958060013", new Integer(3),
				"INV005958060013", "192.168.0.106", LocalDateTime.parse("2018-02-11T14:50:04"));
		List<XMeasurement> mes = dev.getMeasurements();
		assertMeasurement(mes.get(0), 232.0D, XUnit.V, XType.AC_VOLTAGE);
		assertMeasurement(mes.get(1), 0.287D, XUnit.A, XType.AC_CURRENT);
		assertMeasurement(mes.get(2), 48.8D, XUnit.W, XType.AC_POWER);
		assertMeasurement(mes.get(3), 49.987D, XUnit.HZ, XType.AC_FREQUENCY);
		assertMeasurement(mes.get(4), 325.2D, XUnit.V, XType.DC_VOLTAGE);
		assertMeasurement(mes.get(5), 0.210D, XUnit.A, XType.DC_CURRENT);
		assertMeasurement(mes.get(6), 26.1D, XUnit.C, XType.TEMP);
		assertMeasurement(mes.get(7), null, XUnit.W, XType.GRID_POWER);
		assertMeasurement(mes.get(8), 100D, XUnit.PERCENT, XType.DERATING);
	}

	private void assertDevice(XDevice dev, String name, Integer nominalPower, String type, String serial,
			Integer busAddress, String netBiosName, String ipAddress, LocalDateTime dateTime) {
		assertEquals(busAddress, dev.getBusAddress());
		assertEquals(dateTime, dev.getRequestTime());
		assertEquals(ipAddress, dev.getIpAddress());
		assertEquals(name, dev.getName());
		assertEquals(netBiosName, dev.getNetBiosName());
		assertEquals(nominalPower, dev.getNominalPower());
		assertEquals(serial, dev.getSerial());
		assertEquals(type, dev.getType());
	}

	private void assertMeasurement(XMeasurement mes, Double value, XUnit unit, XType type) {
		assertEquals(value, mes.getValue());
		assertEquals(type, mes.getType());
		assertEquals(unit, mes.getUnit());
	}

}
