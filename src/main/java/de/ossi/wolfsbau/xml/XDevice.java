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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
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

	public void addAll(Collection<XMeasurement> measurements) {
		measurements.forEach(this::add);
	}

	public void add(XMeasurement measurement) {
		if (this.measurements == null) {
			this.measurements = new ArrayList<>();
		}
		this.measurements.add(measurement);
	}

}
