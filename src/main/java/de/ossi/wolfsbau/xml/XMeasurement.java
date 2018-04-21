package de.ossi.wolfsbau.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class XMeasurement {

	@XmlAttribute(name = "Value")
	private Double value;
	@XmlAttribute(name = "Unit")
	private XUnit unit;
	@XmlAttribute(name = "Type")
	private XType type;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public XUnit getUnit() {
		return unit;
	}

	public void setUnit(XUnit unit) {
		this.unit = unit;
	}

	public XType getType() {
		return type;
	}

	public void setType(XType type) {
		this.type = type;
	}

}
