package de.ossi.modbustcp.db.util;

import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import org.hamcrest.Matchers;
import org.junit.Test;

import de.ossi.modbustcp.db.data.Device;
import de.ossi.modbustcp.db.data.Measurement;
import de.ossi.modbustcp.db.data.Type;
import de.ossi.modbustcp.db.data.Unit;
import de.ossi.modbustcp.db.util.XMLtoDBConverter;
import de.ossi.modbustcp.xml.XDevice;
import de.ossi.modbustcp.xml.XMeasurement;
import de.ossi.modbustcp.xml.XType;
import de.ossi.modbustcp.xml.XUnit;

public class XMLtoDBConverterTest {

	private final LocalDateTime time = LocalDateTime.now();

	@Test
	public void convXDevice() {
		XDevice xDevice = new XDevice();
		xDevice.setBusAddress(1);
		xDevice.setIpAddress("192.168.1.1");
		xDevice.setName("name");
		xDevice.setNetBiosName("netBiosName");
		xDevice.setNominalPower(12);
		xDevice.setRequestTime(time);
		xDevice.setSerial("serial");
		xDevice.setType("type");
		xDevice.add(createXMeasurement(33.3D, XUnit.V, XType.AC_VOLTAGE));

		Device device = new Device();
		device.setBusAddress(1);
		device.setIpAddress("192.168.1.1");
		device.setName("name");
		device.setNetBiosName("netBiosName");
		device.setNominalPower(12);
		device.setRequestTime(time);
		device.setSerial("serial");
		device.setType("type");
		device.add(createMeasurement(33.3D, Unit.V, Type.AC_VOLTAGE));

		assertXDevice(xDevice, device);

	}

	private void assertXDevice(XDevice xDevice, Device device) {
		assertThat(xDevice.getBusAddress(), Matchers.is(device.getBusAddress()));
		assertThat(xDevice.getIpAddress(), Matchers.is(device.getIpAddress()));
		assertThat(xDevice.getName(), Matchers.is(device.getName()));
		assertThat(xDevice.getNetBiosName(), Matchers.is(device.getNetBiosName()));
		assertThat(xDevice.getNominalPower(), Matchers.is(device.getNominalPower()));
		assertThat(xDevice.getRequestTime(), Matchers.is(device.getRequestTime()));
		assertThat(xDevice.getSerial(), Matchers.is(device.getSerial()));
		assertThat(xDevice.getType(), Matchers.is(device.getType()));
		assertThat(xDevice.getMeasurements(), Matchers.hasSize(1));
		assertThat(device.getMeasurements(), Matchers.hasSize(1));
		assertMeasurement(xDevice.getMeasurements().get(0), device.getMeasurements().iterator().next());
	}

	@Test
	public void convXMeasurement() {
		XMeasurement xMeasurement = createXMeasurement(12.12D, XUnit.HZ, XType.AC_FREQUENCY);
		Measurement measurement = createMeasurement(12.12D, Unit.HZ, Type.AC_FREQUENCY);
		assertMeasurement(xMeasurement, measurement);
	}

	private Measurement createMeasurement(Double value, Unit unit, Type type) {
		Measurement measurement = new Measurement();
		measurement.setValue(value);
		measurement.setType(type);
		measurement.setUnit(unit);
		return measurement;
	}

	private XMeasurement createXMeasurement(Double value, XUnit xunit, XType xtype) {
		XMeasurement xMeasurement = new XMeasurement();
		xMeasurement.setValue(value);
		xMeasurement.setUnit(xunit);
		xMeasurement.setType(xtype);
		return xMeasurement;
	}

	private void assertMeasurement(XMeasurement xMeasurement, Measurement measurement) {
		assertThat(xMeasurement.getValue(), Matchers.is(measurement.getValue()));
		assertType(xMeasurement.getType(), measurement.getType());
		assertUnit(xMeasurement.getUnit(), measurement.getUnit());
	}

	@Test
	public void convXType() {
		assertType(XType.AC_CURRENT, Type.AC_CURRENT);
		assertType(XType.AC_FREQUENCY, Type.AC_FREQUENCY);
		assertType(XType.AC_POWER, Type.AC_POWER);
		assertType(XType.AC_VOLTAGE, Type.AC_VOLTAGE);
		assertType(XType.DC_VOLTAGE, Type.DC_VOLTAGE);
		assertType(XType.DERATING, Type.DERATING);
		assertType(XType.GRID_POWER, Type.GRID_POWER);
		assertType(XType.TEMP, Type.TEMP);
	}

	private void assertType(XType xtype, Type type) {
		assertThat(XMLtoDBConverter.from(xtype), Matchers.is(type));
	}

	@Test
	public void convXUnit() {
		assertUnit(XUnit.A, Unit.A);
		assertUnit(XUnit.C, Unit.C);
		assertUnit(XUnit.HZ, Unit.HZ);
		assertUnit(XUnit.PERCENT, Unit.PERCENT);
		assertUnit(XUnit.V, Unit.V);
		assertUnit(XUnit.W, Unit.W);
	}

	private void assertUnit(XUnit xunit, Unit unit) {
		assertThat(XMLtoDBConverter.from(xunit), Matchers.is(unit));
	}
}
