package de.ossi.wolfsbau.xml;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import de.ossi.wolfsbau.xml.adapter.LocalDateTimeAdapter;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class XDevice {

	@XmlElementWrapper(name = "Measurements")
	@XmlElement(name = "Measurement")
	private List<XMeasurement> measurements;

	@XmlAttribute(name = "Name")
	private String name;
	@XmlAttribute(name = "NominalPower")
	private Integer nominalPower;
	@XmlAttribute(name = "Type")
	private String type;
	@XmlAttribute(name = "Serial")
	private String serial;
	@XmlAttribute(name = "BusAddress")
	private Integer busAddress;
	@XmlAttribute(name = "NetBiosName")
	private String netBiosName;
	@XmlAttribute(name = "IpAddress")
	private String ipAddress;
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	@XmlAttribute(name = "DateTime")
	private LocalDateTime requestTime;

	public LocalDateTime getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(LocalDateTime requestTime) {
		this.requestTime = requestTime;
	}

	public List<XMeasurement> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(List<XMeasurement> measurements) {
		this.measurements = measurements;
	}

	public void addAll(Collection<XMeasurement> measurements) {
		measurements.forEach(this::add);
	}

	public void add(XMeasurement measurement) {
		if (this.measurements == null) {
			this.measurements = new ArrayList<>();
		}
		this.measurements.add(measurement);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNominalPower() {
		return nominalPower;
	}

	public void setNominalPower(Integer nominalPower) {
		this.nominalPower = nominalPower;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Integer getBusAddress() {
		return busAddress;
	}

	public void setBusAddress(Integer busAddress) {
		this.busAddress = busAddress;
	}

	public String getNetBiosName() {
		return netBiosName;
	}

	public void setNetBiosName(String netBiosName) {
		this.netBiosName = netBiosName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
