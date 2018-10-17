package de.ossi.modbustcp.matcher;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import de.ossi.modbustcp.db.data.Device;

public class DeviceMatcher extends TypeSafeMatcher<Device> {
	private String name;
	private Integer nominalPower;
	private String type;
	private String serial;
	private Integer busAddress;
	private String netBiosName;
	private String ipAddress;
	private LocalDateTime requestTime;

	public DeviceMatcher(String name, Integer nominalPower, String type, String serial, Integer busAddress,
			String netBiosName, String ipAddress, LocalDateTime requestTime) {
		this.name = name;
		this.nominalPower = nominalPower;
		this.type = type;
		this.serial = serial;
		this.busAddress = busAddress;
		this.netBiosName = netBiosName;
		this.ipAddress = ipAddress;
		this.requestTime = requestTime;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("name: ");
		description.appendText(name);
		description.appendText("; nominalPower: ");
		description.appendText(nullsafe(nominalPower));
		description.appendText("; type: ");
		description.appendText(type);
		description.appendText("; serial: ");
		description.appendText(serial);
		description.appendText("; busAddress: ");
		description.appendText(nullsafe(busAddress));
		description.appendText("; netBiosName: ");
		description.appendText(netBiosName);
		description.appendText("; ipAddress: ");
		description.appendText(ipAddress);
		description.appendText("; requestTime: ");
		description.appendText(requestTime.toString());
	}

	private String nullsafe(Integer i) {
		return i != null ? i.toString() : "null";
	}

	@Override
	protected boolean matchesSafely(Device item) {
		return Objects.equals(busAddress, item.getBusAddress()) && Objects.equals(name, item.getName())
				&& Objects.equals(ipAddress, item.getIpAddress())
				&& Objects.equals(nominalPower, item.getNominalPower()) && Objects.equals(type, item.getType())
				&& Objects.equals(serial, item.getSerial()) && Objects.equals(netBiosName, item.getNetBiosName())
				&& Objects.equals(requestTime, item.getRequestTime());
	}

	public static DeviceMatcher is(String name, Integer nominalPower, String type, String serial, Integer busAddress,
			String netBiosName, String ipAddress, LocalDateTime requestTime) {
		return new DeviceMatcher(name, nominalPower, type, serial, busAddress, netBiosName, ipAddress, requestTime);
	}

}
