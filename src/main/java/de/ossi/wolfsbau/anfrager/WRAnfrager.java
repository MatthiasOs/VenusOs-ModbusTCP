package de.ossi.wolfsbau.anfrager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Klasse zum Anfragen der Daten am Wechselrichter. über Http Get
 */
public class WRAnfrager {

	private final String ipAdress;
	private static final String PROTOCOL = "http";
	private static final String MEASUREMENTS = "measurements.xml";
	private static final String USER_AGENT = "Mozilla/5.0";

	public WRAnfrager(String ipAdress) {
		this.ipAdress = ipAdress;
	}

	public BufferedReader frageDatenAb() throws IOException {
		URL url = createURL(PROTOCOL, ipAdress, MEASUREMENTS);

		HttpURLConnection con = createConnection(url);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		return in;
	}

	private HttpURLConnection createConnection(URL url) throws IOException {
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		return con;
	}

	URL createURL(String protocol, String ipAdress, String page) throws MalformedURLException {
		StringBuilder url = new StringBuilder();
		url.append(protocol);
		url.append("://");
		url.append(ipAdress);
		url.append("/");
		url.append(page);
		return new URL(url.toString());
	}

}
