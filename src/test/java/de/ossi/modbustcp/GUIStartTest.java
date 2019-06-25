package de.ossi.modbustcp;

import org.junit.Test;

import de.ossi.modbustcp.GUIStart;

/**
 * Der Test ist für den einfachen E2E Test gedacht. Es werden keine Asserts
 * durchgeführt, es muss manuell in der DB nachgeschaut werden.
 * 
 * NICHT DER TESTSUITE HINZUFÜGEN!
 * 
 * @author ossi
 *
 */
public class GUIStartTest {

	private final GUIStart starter = new GUIStart();

	@Test
	public void mehrereAbfrageMit500msAbstand() throws Exception {
		starter.speichereAktuelleWRDaten();
		Thread.sleep(500);
		starter.speichereAktuelleWRDaten();
		Thread.sleep(500);
		starter.speichereAktuelleWRDaten();
	}
}
