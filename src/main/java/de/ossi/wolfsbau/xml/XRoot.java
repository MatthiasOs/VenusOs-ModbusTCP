package de.ossi.wolfsbau.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "root")
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class XRoot {

	@XmlElement(name = "Device")
	private XDevice device;

	public XDevice getDevice() {
		return device;
	}

	public void setDevice(XDevice device) {
		this.device = device;
	}

}
