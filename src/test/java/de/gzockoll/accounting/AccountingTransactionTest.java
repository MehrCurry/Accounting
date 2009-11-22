package de.gzockoll.accounting;


import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import de.gzockoll.quantity.Quantity;

public class AccountingTransactionTest {

	private DetailAccount kwh1,kwh2,kwh3;
	private Quantity tenKwh;
	private Quantity fiveKwh;

	@Before
	public void setUp() throws Exception {
		kwh1=AccountingPlan.createDetailAccount("kwh", Units.KWH);
		kwh2=AccountingPlan.createDetailAccount("kwh", Units.KWH);
		kwh3=AccountingPlan.createDetailAccount("kwh", Units.KWH);

		tenKwh = new Quantity(10, Units.KWH);
		fiveKwh = new Quantity(5, Units.KWH);
	}
	
	@Test
	public void testCanPost2Way() {
	AccountingTransaction tx=new AccountingTransaction(new Date());		
		tx.add(tenKwh, kwh1, "Test");
		assertThat(tx.canPost(),is(false));
		
		tx.add(tenKwh.negate(), kwh2, "Test");
		assertThat(tx.canPost(),is(true));
	}

	@Test
	public void testCanPost3Way() {
	AccountingTransaction tx=new AccountingTransaction(new Date());
		
		tx.add(tenKwh, kwh1, "Test");
		assertThat(tx.canPost(),is(false));
		
		tx.add(fiveKwh.negate(), kwh2, "Test");
		assertThat(tx.canPost(),is(false));
		
		tx.add(fiveKwh.negate(), kwh3, "Test");
		assertThat(tx.canPost(),is(true));
	}
	@Test
	public void testPost2Way() {
	AccountingTransaction tx=new AccountingTransaction(new Date());
		
		tx.add(tenKwh, kwh1, "Test");
		
		tx.add(tenKwh.negate(), kwh2, "Test");
		tx.post();
		assertThat(kwh1.entryCount(),is(1));
		assertThat(kwh1.saldo(),is(tenKwh));
		assertThat(kwh2.entryCount(),is(1));
		assertThat(kwh2.saldo(),is(tenKwh.negate()));
	}

	@Test
	public void testPost3Way() {
	AccountingTransaction tx=new AccountingTransaction(new Date());
		
		tx.add(tenKwh, kwh1, "Test");
		
		tx.add(fiveKwh.negate(), kwh2, "Test");
		
		tx.add(fiveKwh.negate(), kwh3, "Test");
		tx.post();
		assertThat(kwh1.entryCount(),is(1));
		assertThat(kwh1.saldo(),is(tenKwh));
		assertThat(kwh2.entryCount(),is(1));
		assertThat(kwh3.saldo(),is(fiveKwh.negate()));
		assertThat(kwh3.entryCount(),is(1));
		assertThat(kwh3.saldo(),is(fiveKwh.negate()));
	}
	
	@Test(expected=UnableToPostException.class)
	public void testCanNotPost() {
		AccountingTransaction tx=new AccountingTransaction(new Date());
		tx.add(tenKwh, kwh1, "Test");
		tx.post();
	}

}
