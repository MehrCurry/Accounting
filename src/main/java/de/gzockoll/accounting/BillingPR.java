package de.gzockoll.accounting;

import java.util.Date;

import de.gzockoll.types.money.CurrencyUnit;
import de.gzockoll.types.money.Money;

public class BillingPR extends EachEntryPR {

	private static final double RATE = 0.19;

	@Override
	protected void processEntry(Entry e) {
		AccountingTransaction tx = new AccountingTransaction(new Date());
		Money money=transform(e);
		
		tx.add(money.negate(), AccountingPlan.getAccount("Herr Meier"), e.getText());
		tx.add(money, AccountingPlan.getAccount("Network Income"), e.getText());
		tx.post();
	}

	private Money transform(Entry e) {
		assertThatUnitIsOk(e);
		return new Money((e.getQuantity().getAmount()*RATE), CurrencyUnit.EURO);
	}

	private void assertThatUnitIsOk(Entry e) {
		if (e.getQuantity().getUnit()!=Units.KWH)
			throw new IllegalArgumentException("Illegale Unit for billing plan:" + e.getQuantity().getUnit());		
	}
}
