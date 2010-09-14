package de.gzockoll.accounting;

import static org.junit.Assert.*;

import org.junit.Test;

import de.gzockoll.quantity.SimpleQuantity;
import de.gzockoll.quantity.Units;
import de.gzockoll.types.money.CurrencyUnit;
import de.gzockoll.types.money.Money;
import static org.hamcrest.core.Is.*;

public class DetailAccountTest {

	@Test
	public void testBalanceSimpleQuantity() {
		DetailAccount<SimpleQuantity> a=new DetailAccount<SimpleQuantity>("Test", Units.KWH);
		assertThat(a.balance().getAmount(),is(0L));
		assertThat(a.balance().getUnit().toString(),is("KWH"));
	}

	@Test
	public void testBalanceMoney() {
		DetailAccount<Money> a=new DetailAccount<Money>("Test", CurrencyUnit.EURO);
		assertThat(a.balance().getAmount(),is(0L));
		assertThat(a.balance().getUnit().toString(),is("EUR"));
		assertThat(a.balance().equals(Money.euros(0)),is(true));
	}

	@Test
	public void testGetZeroBalance() {
		DetailAccount<SimpleQuantity> a=new DetailAccount<SimpleQuantity>("Test", Units.KWH);
		assertThat(a.getZeroBalance().getAmount(),is(0L));
	}

}
