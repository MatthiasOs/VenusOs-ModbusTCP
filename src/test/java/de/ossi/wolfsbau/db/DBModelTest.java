package de.ossi.wolfsbau.db;

import static org.junit.Assert.assertThat;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.ossi.wolfsbau.db.data.Device;
import de.ossi.wolfsbau.db.data.Measurement;
import de.ossi.wolfsbau.db.data.Type;
import de.ossi.wolfsbau.db.data.Unit;
import de.ossi.wolfsbau.matcher.DeviceMatcher;
import de.ossi.wolfsbau.matcher.MeasurementMatcher;

public class DBModelTest {

	private static final double VALUE = 12.12D;
	private static final String DEVICE_NAME = "DeviceName";
	private static final String DB_NAME = "testDB.sqlite";
	private static final String DB_PATH = "jdbc:sqlite:";
	private static final LocalDateTime REQUEST_TIME = LocalDateTime.now();
	private DBModel db;

	@Before
	public void setUp() throws Exception {
		db = new DBModel(DB_PATH + DB_NAME);
	}

	@After
	public void cleanUp() throws Exception {
		File db = new File(DB_NAME);
		db.delete();
	}

	@Test
	public void esWurdeEinDeviceMitEinemMeasurementUndUnitUndTypeGespeichert() throws Exception {
		// given
		Device device = createDevice();
		Measurement measurement = createMeasurement(Type.AC_CURRENT, Unit.A, VALUE);
		device.add(measurement);
		// when
		db.saveDevice(device);
		// then
		List<Device> readDevices = db.getDevicesByInterval(REQUEST_TIME, REQUEST_TIME);
		assertThat(readDevices, Matchers.hasSize(1));
		assertThat(readDevices.get(0), DeviceMatcher.is(DEVICE_NAME, null, null, null, null, null, null, REQUEST_TIME));
		assertThat(readDevices.get(0).getMeasurements(), Matchers.hasSize(1));
		assertThat(readDevices.get(0).getMeasurements().iterator().next(),
				MeasurementMatcher.is(Type.AC_CURRENT, Unit.A, VALUE, readDevices.get(0)));
	}

	@Test
	public void esWurdeEinDeviceMitMehrerenMeasurementGespeichert() throws SQLException {
		// given
		Device device = createDevice();
		Measurement measurement = createMeasurement(null, null, VALUE);
		device.add(measurement);
		Measurement measurement2 = createMeasurement(null, null, VALUE);
		device.add(measurement2);
		// when
		db.saveDevice(device);
		// then
		List<Device> readDevices = db.getDevicesByInterval(REQUEST_TIME, REQUEST_TIME);
		assertThat(readDevices, Matchers.hasSize(1));
		Device readDevice = readDevices.get(0);
		assertThat(readDevice, DeviceMatcher.is(DEVICE_NAME, null, null, null, null, null, null, REQUEST_TIME));
		assertThat(readDevice.getMeasurements(), Matchers.hasSize(2));
		Iterator<Measurement> measurementsIterator = readDevice.getMeasurements().iterator();
		assertThat(measurementsIterator.next(), MeasurementMatcher.is(null, null, VALUE, readDevice));
		assertThat(measurementsIterator.next(), MeasurementMatcher.is(null, null, VALUE, readDevice));
	}

	@Test
	public void esWurdeEinDeviceMitEinerMeasurementGespeichert() throws SQLException {
		// given
		Device device = createDevice();
		Measurement measurement = createMeasurement(null, null, VALUE);
		device.add(measurement);
		// when
		db.saveDevice(device);
		// then
		List<Device> readDevices = db.getDevicesByInterval(REQUEST_TIME, REQUEST_TIME);
		assertThat(readDevices, Matchers.hasSize(1));
		assertThat(readDevices.get(0), DeviceMatcher.is(DEVICE_NAME, null, null, null, null, null, null, REQUEST_TIME));
		assertThat(readDevices.get(0).getMeasurements(), Matchers.hasSize(1));
		assertThat(readDevices.get(0).getMeasurements().iterator().next(),
				MeasurementMatcher.is(null, null, VALUE, readDevices.get(0)));
	}

	@Test
	public void deviceMitAllenWertenWurdeGespeichert() throws Exception {
		// given
		Device device = createDevice(DEVICE_NAME, 12, "type", "serial", 3, "biosName", "192.168.1.1", REQUEST_TIME);
		// when
		db.saveDevice(device);
		// then
		List<Device> readDevices = db.getDevicesByInterval(REQUEST_TIME, REQUEST_TIME);
		assertThat(readDevices, Matchers.hasSize(1));
		assertThat(readDevices.get(0),
				DeviceMatcher.is(DEVICE_NAME, 12, "type", "serial", 3, "biosName", "192.168.1.1", REQUEST_TIME));
	}

	@Test
	public void esWurdeEinDeviceGespeichert() throws SQLException {
		db.saveDevice(createDevice());
		List<Device> readDevices = db.getDevicesByInterval(REQUEST_TIME, REQUEST_TIME);
		assertThat(readDevices, Matchers.hasSize(1));
		assertThat(readDevices.get(0), DeviceMatcher.is(DEVICE_NAME, null, null, null, null, null, null, REQUEST_TIME));
	}

	@Test
	public void esWirdEinDeviceInDerDBGespeichert() throws SQLException {
		db.saveDevice(createDevice());
		assertThat(db.getAll(), Matchers.hasSize(1));
	}

	@Test
	public void esWirdNichtsGespeichertBeiNull() throws SQLException {
		db.saveDevice(null);
		assertThat(db.getAll(), Matchers.empty());
	}

	@Test
	public void konstruktorErzeugtEinDBModelMitPath() {
		assertThat(db.getSource(), Matchers.is(DB_PATH + DB_NAME));
	}

	@Test
	public void konstruktorErzeugtEinDBModel() {
		assertThat(db, Matchers.notNullValue());
	}

	/**
	 * ID ist technisch und wird vom Framework gesetzt, deshalb nicht getestet.
	 */
	private Device createDevice(String name, Integer nominalPower, String type, String serial, Integer busAddress,
			String netBiosName, String ipAddress, LocalDateTime requestTime) {
		Device dev = new Device();
		dev.setRequestTime(requestTime);
		dev.setName(name);
		dev.setBusAddress(busAddress);
		dev.setIpAddress(ipAddress);
		dev.setNetBiosName(netBiosName);
		dev.setNominalPower(nominalPower);
		dev.setSerial(serial);
		dev.setType(type);
		return dev;
	}

	private Device createDevice() {
		Device dev = new Device();
		dev.setRequestTime(REQUEST_TIME);
		dev.setName(DEVICE_NAME);
		return dev;
	}

	private Measurement createMeasurement(Type type, Unit unit, Double value) {
		Measurement measurement = new Measurement();
		measurement.setValue(value);
		measurement.setUnit(unit);
		measurement.setType(type);
		return measurement;
	}

}
