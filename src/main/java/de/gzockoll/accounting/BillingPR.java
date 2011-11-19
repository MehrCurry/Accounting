package de.gzockoll.accounting;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;

public class BillingPR extends EachEntryPR {

	private static final double RATE = 0.19;
	private TaxRate taxRate=TaxRate.NORMAL;

	@SuppressWarnings("unchecked")
	@Override
	protected void processEntry(Entry e) {
		AccountingTransaction tx = new AccountingTransaction(new DateTime());
		Money money=transform(e);
		Money tax=money.multipliedBy(taxRate.getRate().longValue());
		
		tx.add(money.negated(), AccountingPlan.getAccount("Herr Meier"), e.getText());
		tx.add(money, AccountingPlan.getAccount("Network Income"), e.getText());
		tx.add(tax.negated(), AccountingPlan.getAccount("Herr Meier"), e.getText() + " " + taxRate);
		tx.add(tax, AccountingPlan.getAccount("Taxes"), e.getText() + " " + taxRate);
		tx.post();
	}

	private Money transform(Entry e) {
		assertThatUnitIsOk(e);
		double calculatedAmount = e.getQuantity().getAmount().doubleValue()*RATE;
		return Money.of(CurrencyUnit.EUR,calculatedAmount);
	}

	private void assertThatUnitIsOk(Entry e) {
		if (e.getQuantity().getCurrencyUnit()!=CurrencyUnit.EUR)
			throw new IllegalArgumentException("Illegale Unit for billing plan:" + e.getQuantity().getCurrencyUnit());		
	}

	public void process(AccountingEvent e) {
		throw new UnsupportedOperationException("Not yet implemented");		
	}
}
