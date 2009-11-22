package de.gzockoll.accounting;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import de.gzockoll.quantity.Quantity;

public class BillingPRTest {

	private DetailAccount meier;
	private DetailAccount kwh;
	private DetailAccount income;

	@Before
	public void setUp() throws Exception {
		kwh=AccountingPlan.createDetailAccount("kwh", Units.KWH);
		meier=AccountingPlan.createDetailAccount("Herr Meier", CurrencyUnit.EURO);
		income=AccountingPlan.createDetailAccount("Network Income", CurrencyUnit.EURO);
	}

	@Test
	public void testBilling() {
		new Entry(kwh, new Quantity(100, Units.KWH), "Verbrauch").post();
		
		BillingPR pr=new BillingPR();
		pr.processAccount(kwh);
		
		assertThat(meier.entryCount(),is(1));
		assertThat(meier.saldo(),is((Quantity)new Money(-19,CurrencyUnit.EURO)));
		assertThat(income.entryCount(),is(1));
		assertThat(income.saldo(),is((Quantity)new Money(19,CurrencyUnit.EURO)));
		
		System.out.println(kwh);
		System.out.println(meier);
		System.out.println(income);
		System.out.println(AuditLog.to_s());
	}
}
