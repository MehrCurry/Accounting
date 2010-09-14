package de.gzockoll.accounting;


import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.gzockoll.common.types.Timepoint;
import de.gzockoll.quantity.Quantity;
import de.gzockoll.quantity.Units;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AccountingTransactionTest {

	private DetailAccount kwh1,kwh2,kwh3;
	private Quantity tenKwh;
	private Quantity fiveKwh;

	@Before
	public void setUp() throws Exception {
		kwh1=AccountingPlan.createDetailAccount("kwh", Units.KWH);
		kwh2=AccountingPlan.createDetailAccount("kwh", Units.KWH);
		kwh3=AccountingPlan.createDetailAccount("kwh", Units.KWH);

		tenKwh = Units.KWH.amount(10);
		fiveKwh = Units.KWH.amount(5);
	}
	
	@Test
	public void testCanPost2Way() {
	AccountingTransaction<Quantity> tx=new AccountingTransaction<Quantity>(Timepoint.now());		
		tx.add(tenKwh, kwh1, "Test");
		assertThat(tx.canPost(),is(false));
		
		tx.add(tenKwh.negate(), kwh2, "Test");
		assertThat(tx.canPost(),is(true));
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testCanPost3Way() {
	AccountingTransaction tx=new AccountingTransaction(Timepoint.now());
		
		tx.add(tenKwh, kwh1, "Test");
		assertThat(tx.canPost(),is(false));
		
		tx.add(fiveKwh.negate(), kwh2, "Test");
		assertThat(tx.canPost(),is(false));
		
		tx.add(fiveKwh.negate(), kwh3, "Test");
		assertThat(tx.canPost(),is(true));
	}
	@Test
	public void testPost2Way() {
	AccountingTransaction tx=new AccountingTransaction(Timepoint.now());
		
		tx.add(tenKwh, kwh1, "Test");
		
		tx.add(tenKwh.negate(), kwh2, "Test");
		tx.post();
		assertThat(kwh1.entryCount(),is(1));
		assertThat(kwh1.balance(),is(tenKwh));
		assertThat(kwh2.entryCount(),is(1));
		assertThat(kwh2.balance(),is(tenKwh.negate()));
	}

	@Test
	public void testPost3Way() {
	AccountingTransaction tx=new AccountingTransaction(Timepoint.now());
		
		tx.add(tenKwh, kwh1, "Test");
		
		tx.add(fiveKwh.negate(), kwh2, "Test");
		
		tx.add(fiveKwh.negate(), kwh3, "Test");
		tx.post();
		assertThat(kwh1.entryCount(),is(1));
		assertThat(kwh1.balance(),is(tenKwh));
		assertThat(kwh2.entryCount(),is(1));
		assertThat(kwh3.balance(),is(fiveKwh.negate()));
		assertThat(kwh3.entryCount(),is(1));
		assertThat(kwh3.balance(),is(fiveKwh.negate()));
	}
	
	@Test(expected=UnableToPostException.class)
	public void testCanNotPost() {
		AccountingTransaction tx=new AccountingTransaction(Timepoint.now());
		tx.add(tenKwh, kwh1, "Test");
		tx.post();
	}
	
	@Test(expected=ImmutableTransactionException.class)
	public void testUnmutableTransactionException() {
		AccountingTransaction tx=new AccountingTransaction(Timepoint.now());
		tx.add(tenKwh, kwh1, "Test");
		tx.add(tenKwh.negate(), kwh2, "Test");
		tx.post();
		tx.post();
	}

}
