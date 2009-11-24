package de.gzockoll.accounting;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsInstanceOf.*;
import static org.junit.Assert.*;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Test;

import de.gzockoll.quantity.NullQuantity;
import de.gzockoll.quantity.Quantity;
import de.gzockoll.quantity.SimpleQuantity;

public class AccountTest {

	private Account account;

	@Before
	public void setUp() throws Exception {
		account=new DetailAccount("JUnit",Units.KWH);
		assertThat(account.entryCount(),is(0));
	}

	@Test
	public void testBook() {
		new Entry(account, new SimpleQuantity(10,Units.KWH),"JUnit").post();
		assertThat(account.entryCount(),is(1));
		new Entry(account, new SimpleQuantity(10,Units.KWH),"JUnit").post();
		assertThat(account.entryCount(),is(2));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testBookWrongUnit() {
		new Entry(account, new SimpleQuantity(10,Units.KG),"JUnit").post();
	}

	@Test
	public void testSaldo() {
		new Entry(account, new SimpleQuantity(10,Units.KWH),"JUnit").post();
		assertThat(account.saldo(),is((Quantity)new SimpleQuantity(10, Units.KWH)));
		new Entry(account, new SimpleQuantity(10,Units.KWH),"JUnit").post();
		assertThat(account.saldo(),is((Quantity)new SimpleQuantity(20, Units.KWH)));
		new Entry(account, new SimpleQuantity(100,Units.KWH),"JUnit").post();
		assertThat(account.saldo(),is((Quantity)new SimpleQuantity(120, Units.KWH)));
	}
	
	@Test
	public void testSaldoWithEmptyAccount() {
		Account<SimpleQuantity> account=new DetailAccount<SimpleQuantity>("JUnit", Units.KWH);
		assertThat(account.saldo(),instanceOf(NullQuantity.class));
	}
}
