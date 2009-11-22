package de.gzockoll.accounting;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import de.gzockoll.quantity.Quantity;

public class AccountTest {

	private Account account;

	@Before
	public void setUp() throws Exception {
		account=new DetailAccount("JUnit",Units.KWH);
		assertThat(account.entryCount(),is(0));
	}

	@Test
	public void testBook() {
		new Entry(account, new Quantity(10,Units.KWH),"JUnit").post();
		assertThat(account.entryCount(),is(1));
		new Entry(account, new Quantity(10,Units.KWH),"JUnit").post();
		assertThat(account.entryCount(),is(2));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testBookWrongUnit() {
		new Entry(account, new Quantity(10,Units.KG),"JUnit").post();
	}

	@Test
	public void testSaldo() {
		new Entry(account, new Quantity(10,Units.KWH),"JUnit").post();
		assertThat(account.saldo(),is(new Quantity(10, Units.KWH)));
		new Entry(account, new Quantity(10,Units.KWH),"JUnit").post();
		assertThat(account.saldo(),is(new Quantity(20, Units.KWH)));
		new Entry(account, new Quantity(100,Units.KWH),"JUnit").post();
		assertThat(account.saldo(),is(new Quantity(120, Units.KWH)));
	}
}
