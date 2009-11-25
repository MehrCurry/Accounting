package de.gzockoll.accounting;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.gzockoll.quantity.Quantity;
import de.gzockoll.quantity.SimpleQuantity;
import de.gzockoll.quantity.Units;
import de.gzockoll.types.money.CurrencyUnit;
import de.gzockoll.types.money.Money;


@SuppressWarnings({ "unchecked", "rawtypes" })
public class BillingPRTest {

	private DetailAccount meier;
	private DetailAccount kwh;
	private DetailAccount income;

	@Before
	public void setUp() throws Exception {
		kwh = AccountingPlan.createDetailAccount("kwh", Units.KWH);
		meier = AccountingPlan.createDetailAccount("Herr Meier",
				CurrencyUnit.EURO);
		income = AccountingPlan.createDetailAccount("Network Income",
				CurrencyUnit.EURO);
	}

	@Test
	public void testBilling() {
		new Entry(kwh, new SimpleQuantity(100, Units.KWH), "Verbrauch").post();

		BillingPR pr = new BillingPR();
		pr.processAccount(kwh);

		assertThat(meier.entryCount(), is(1));
		assertThat(meier.saldo(), is((Quantity) new Money(-19.00,
				CurrencyUnit.EURO)));
		assertThat(income.entryCount(), is(1));
		assertThat(income.saldo(), is((Quantity) new Money(19.00,
				CurrencyUnit.EURO)));

		System.out.println(kwh);
		System.out.println(meier);
		System.out.println(income);
	}
}
