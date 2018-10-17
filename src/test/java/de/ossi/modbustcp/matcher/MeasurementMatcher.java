package de.ossi.modbustcp.matcher;

import java.util.Objects;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import de.ossi.modbustcp.db.data.Device;
import de.ossi.modbustcp.db.data.Measurement;
import de.ossi.modbustcp.db.data.Type;
import de.ossi.modbustcp.db.data.Unit;

public class MeasurementMatcher extends TypeSafeMatcher<Measurement> {

	private Double value;
	private Unit unit;
	private Type type;
	private Device device;

	public MeasurementMatcher(Type type, Unit unit, Double value, Device device) {
		this.value = value;
		this.unit = unit;
		this.type = type;
		this.device = device;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("value: ");
		description.appendText(nullSafe(value));
		description.appendText("; unit: ");
		description.appendText(unit.getName());
		description.appendText("; type: ");
		description.appendText(type.getName());
		description.appendText("; deviceId: ");
		description.appendText(String.valueOf(device.getId()));
	}

	private String nullSafe(Double d) {
		return d != null ? d.toString() : "null";
	}

	@Override
	protected boolean matchesSafely(Measurement item) {
		return Objects.equals(value, item.getValue()) && Objects.equals(device.getId(), item.getDevice().getId())
				&& Objects.equals(type, item.getType()) && Objects.equals(unit, item.getUnit());
	}

	public static MeasurementMatcher is(Type type, Unit unit, Double value, Device device) {
		return new MeasurementMatcher(type, unit, value, device);
	}

}
