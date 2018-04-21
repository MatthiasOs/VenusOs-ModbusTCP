package de.ossi.wolfsbau.db.util;

import java.util.ArrayList;
import java.util.List;

import de.ossi.wolfsbau.db.data.Device;
import de.ossi.wolfsbau.db.data.Measurement;
import de.ossi.wolfsbau.db.data.Type;
import de.ossi.wolfsbau.db.data.Unit;
import de.ossi.wolfsbau.xml.XDevice;
import de.ossi.wolfsbau.xml.XMeasurement;
import de.ossi.wolfsbau.xml.XType;
import de.ossi.wolfsbau.xml.XUnit;

public class XMLtoDBConverter {

	private static EnumPair<XType, Type> typePairs = new EnumPair<>();
	private static EnumPair<XUnit, Unit> unitPairs = new EnumPair<>();

	static {
		typePairs.add(XType.AC_CURRENT, Type.AC_CURRENT);
		typePairs.add(XType.AC_FREQUENCY, Type.AC_FREQUENCY);
		typePairs.add(XType.AC_POWER, Type.AC_POWER);
		typePairs.add(XType.AC_VOLTAGE, Type.AC_VOLTAGE);
		typePairs.add(XType.DC_CURRENT, Type.DC_CURRENT);
		typePairs.add(XType.DC_VOLTAGE, Type.DC_VOLTAGE);
		typePairs.add(XType.DERATING, Type.DERATING);
		typePairs.add(XType.GRID_POWER, Type.GRID_POWER);
		typePairs.add(XType.TEMP, Type.TEMP);
	}

	static {
		unitPairs.add(XUnit.A, Unit.A);
		unitPairs.add(XUnit.C, Unit.C);
		unitPairs.add(XUnit.HZ, Unit.HZ);
		unitPairs.add(XUnit.PERCENT, Unit.PERCENT);
		unitPairs.add(XUnit.V, Unit.V);
		unitPairs.add(XUnit.W, Unit.W);
	}

	public static Device from(XDevice xdevice) {
		Device device = new Device();
		device.setBusAddress(xdevice.getBusAddress());
		device.setIpAddress(xdevice.getIpAddress());
		device.setMeasurements(fromMeasurements(xdevice.getMeasurements(), device));
		device.setName(xdevice.getName());
		device.setNetBiosName(xdevice.getNetBiosName());
		device.setNominalPower(xdevice.getNominalPower());
		device.setRequestTime(xdevice.getRequestTime());
		device.setSerial(xdevice.getSerial());
		device.setType(xdevice.getType());

		return device;
	}

	static List<Measurement> fromMeasurements(List<XMeasurement> xmeasurement, Device device) {
		List<Measurement> measurements = new ArrayList<>();
		for (XMeasurement xmeasure : xmeasurement) {
			measurements.add(from(xmeasure, device));
		}
		return measurements;
	}

	/**
	 * das Device als Foreign Key setzt die DB dann selbst
	 */
	static Measurement from(XMeasurement xmeasurement, Device device) {
		Measurement measurement = new Measurement();
		measurement.setType(from(xmeasurement.getType()));
		measurement.setUnit(from(xmeasurement.getUnit()));
		measurement.setValue(xmeasurement.getValue());
		return measurement;
	}

	static Unit from(XUnit xunit) {
		return unitPairs.getV(xunit);
	}

	static Type from(XType xtype) {
		return typePairs.getV(xtype);
	}

}
