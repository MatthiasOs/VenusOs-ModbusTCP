package de.ossi.wolfsbau;

import org.junit.Test;

/**
 * Der Test ist für den einfachen E2E Test gedacht. Es werden keine Asserts
 * durchgeführt, es muss manuell in der DB nachgeschaut werden.
 * 
 * NICHT DER TESTSUITE HINZUFÜGEN!
 * 
 * @author ossi
 *
 */
public class StarterTest {

	private final Starter starter = new Starter();

	@Test
	public void mehrereAbfrageMit500msAbstand() throws Exception {
		starter.speichereAktuelleWRDaten();
		Thread.sleep(500);
		starter.speichereAktuelleWRDaten();
		Thread.sleep(500);
		starter.speichereAktuelleWRDaten();
	}
}
