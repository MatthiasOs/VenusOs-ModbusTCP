package de.ossi.modbustcp.inverter;

import static org.junit.Assert.assertThat;

import java.net.URL;

import org.hamcrest.Matchers;
import org.junit.Test;

import de.ossi.modbustcp.inverter.InverterRequester;

public class InverterRequesterTest {

	private final InverterRequester anfrager = new InverterRequester("localhost");

	@Test
	public void urlWirdZusammengebaut() throws Exception {
		URL url = anfrager.createURL("http", "127.0.0.1", "testpage.test");
		assertThat(url.getHost(), Matchers.is("127.0.0.1"));
		assertThat(url.getProtocol(), Matchers.is("http"));
		assertThat(url.getPath(), Matchers.is("/testpage.test"));
	}

}
