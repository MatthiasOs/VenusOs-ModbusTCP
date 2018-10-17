package de.ossi.modbustcp.xml.adapter;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

	@Override
	public LocalDateTime unmarshal(String dateString) throws Exception {
		return LocalDateTime.parse(dateString);
	}

	@Override
	public String marshal(LocalDateTime date) throws Exception {
		return date.toString();
	}

}