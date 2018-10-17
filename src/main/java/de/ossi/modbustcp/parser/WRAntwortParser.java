package de.ossi.modbustcp.parser;

import java.io.IOException;
import java.io.Reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import de.ossi.modbustcp.xml.XRoot;

public class WRAntwortParser {

	public XRoot leseEin(Reader reader) throws JAXBException, IOException {
		XRoot root;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(XRoot.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			root = (XRoot) jaxbUnmarshaller.unmarshal(reader);
		} finally {
			reader.close();
		}
		return root;
	}
}
