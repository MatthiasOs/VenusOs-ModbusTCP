package de.ossi.modbustcp.db.util;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;

import de.ossi.modbustcp.db.data.Unit;
import de.ossi.modbustcp.db.util.EnumPair;
import de.ossi.modbustcp.xml.XUnit;

public class EnumPairTest {

	EnumPair<Unit, XUnit> enumPair = new EnumPair<>();

	@After
	public void cleanUp() {
		enumPair.clear();
	}

	@Test
	public void istPaarWirdNichtAusgelesen() {
		enumPair.add(Unit.C, XUnit.A);
		assertThat(enumPair.isPair(Unit.A, XUnit.A), Matchers.is(false));
	}

	@Test
	public void istPaarWirdAusgelesen() {
		enumPair.add(Unit.A, XUnit.A);
		assertThat(enumPair.isPair(Unit.A, XUnit.A), Matchers.is(true));
	}

	@Test
	public void mehrereUnterschiedlicheEnumPairsWerdenHinzugefuegt() {
		enumPair.add(Unit.A, XUnit.A);
		enumPair.add(Unit.V, XUnit.V);
		enumPair.add(Unit.C, XUnit.C);
		assertThat(enumPair.size(), Matchers.is(3));
		assertThat(enumPair.isPair(Unit.A, XUnit.A), Matchers.is(true));
		assertThat(enumPair.isPair(Unit.V, XUnit.V), Matchers.is(true));
		assertThat(enumPair.isPair(Unit.C, XUnit.C), Matchers.is(true));
	}

	@Test
	public void bereitsVorhandesEnumAlsValueHinzufuegenSchlaegtFehl() {
		enumPair.add(Unit.A, XUnit.A);
		assertThat(enumPair.size(), Matchers.is(1));
		enumPair.add(Unit.V, XUnit.A);
		assertThat(enumPair.size(), Matchers.is(1));
	}

	@Test
	public void bereitsVorhandesEnumAlsKeyHinzufuegenSchlaegtFehl() {
		enumPair.add(Unit.A, XUnit.A);
		assertThat(enumPair.size(), Matchers.is(1));
		enumPair.add(Unit.A, XUnit.C);
		assertThat(enumPair.size(), Matchers.is(1));
	}

	@Test
	public void bereitsVorhandesPaarHinzufuegenSchlaegtFehl() {
		enumPair.add(Unit.A, XUnit.A);
		assertThat(enumPair.size(), Matchers.is(1));
		enumPair.add(Unit.A, XUnit.A);
		assertThat(enumPair.size(), Matchers.is(1));
	}

	@Test
	public void einEnumPairWirdHinzugefuegt() {
		enumPair.add(Unit.A, XUnit.A);
		assertThat(enumPair.size(), Matchers.is(1));
	}

	@Test
	public void enumPairsIstLeerInitalLeer() {
		assertThat(enumPair.size(), Matchers.is(0));
	}

}
