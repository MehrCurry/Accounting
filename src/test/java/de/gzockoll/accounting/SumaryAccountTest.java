package de.gzockoll.accounting;


import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.gzockoll.quantity.Quantity;
import de.gzockoll.quantity.SimpleQuantity;
import de.gzockoll.quantity.Unit;

public class SumaryAccountTest {

	private SummaryAccount main;
	private DetailAccount day;
	private DetailAccount night;

	@Before
	public void setUp() throws Exception {
		Unit unit = Units.KWH;
		main=AccountingPlan.createSummaryAccount("Main",unit);
		day=AccountingPlan.createDetailAccount("#day", unit);
		main.AddAccount(day);
		night=AccountingPlan.createDetailAccount("#day", unit);
		main.AddAccount(night);
	}
	
	@Test
	public void testEntryCount() {
		assertThat(main.entryCount(),is(0));
		new Entry(day, new SimpleQuantity(10, Units.KWH), "Test").post();
		assertThat(main.entryCount(),is(1));
		assertThat(day.entryCount(),is(1));
		assertThat(night.entryCount(),is(0));
		
		new Entry(night, new SimpleQuantity(10, Units.KWH), "Test").post();
		assertThat(main.entryCount(),is(2));
		assertThat(day.entryCount(),is(1));
		assertThat(night.entryCount(),is(1));
	}
	
	@Test
	public void testSaldo() {
		Quantity tenKwh = new SimpleQuantity(10, Units.KWH);
		new Entry(day, tenKwh, "Test").post();
		assertThat(main.saldo(),is(tenKwh));
		assertThat(day.saldo(),is(tenKwh));
		assertThat(night.saldo().isZero(),is(true));
		
		new Entry(night, tenKwh, "Test").post();
		assertThat(main.saldo(),is((Quantity)new SimpleQuantity(20, Units.KWH)));
		assertThat(day.saldo(),is(tenKwh));
		assertThat(night.saldo(),is(tenKwh));
	}

	@Test
	public void testSaldoWithNoDetailAccounts() {
		assertThat(main.saldo().isZero(),is(true));
	}
}
