package de.ossi.wolfsbau.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class XMeasurement {

	@XmlAttribute(name = "Value")
	private Double value;
	@XmlAttribute(name = "Unit")
	private XUnit unit;
	@XmlAttribute(name = "Type")
	private XType type;
}
