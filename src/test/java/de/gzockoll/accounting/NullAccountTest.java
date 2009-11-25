package de.gzockoll.accounting;


import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.IsEqual.*;

import org.junit.Before;
import org.junit.Test;

import de.gzockoll.quantity.Quantity;
import de.gzockoll.quantity.Units;

public class NullAccountTest {

	private NullAccount account;

	@Before
	public void setUp() throws Exception {
		account=new NullAccount();
	}
	
	@Test
	public void testEntryCount() {
		assertThat(account.entryCount(),is(0));
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testAddEntry() {
		Entry<Quantity> e=new Entry<Quantity>(account, Units.KG.amount(10), "Test");
		e.post();
	}
	
	@Test
	public void testGetSaldo() {
		Quantity saldo = account.saldo();
		assertThat(saldo,is(not(nullValue())));
	}
	
	@Test
	public void testGetEntries() {
		assertThat(account.getEntries(),is(not(nullValue())));
	}
	@Test
	public void testGetUnit() {
		assertThat(account.getUnit(),is(not(nullValue())));
	}
}
