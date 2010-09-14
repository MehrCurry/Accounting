package de.gzockoll.accounting;

import de.gzockoll.common.types.Timepoint;
import de.gzockoll.quantity.Quantity;
import de.gzockoll.quantity.Units;
import de.gzockoll.types.money.CurrencyUnit;
import de.gzockoll.types.money.Money;

public class BillingPR<T extends Quantity> extends EachEntryPR<T> {

	private static final double RATE = 0.19;
	private TaxRate taxRate=TaxRate.NORMAL;

	@SuppressWarnings("unchecked")
	@Override
	protected void processEntry(Entry<T> e) {
		AccountingTransaction<Quantity> tx = new AccountingTransaction<Quantity>(Timepoint.now());
		Money money=transform(e);
		Money tax=money.multiply(taxRate.getRate());
		
		tx.add(money.negate(), AccountingPlan.getAccount("Herr Meier"), e.getText());
		tx.add(money, AccountingPlan.getAccount("Network Income"), e.getText());
		tx.add(tax.negate(), AccountingPlan.getAccount("Herr Meier"), e.getText() + " " + taxRate);
		tx.add(tax, AccountingPlan.getAccount("Taxes"), e.getText() + " " + taxRate);
		tx.post();
	}

	private Money transform(Entry e) {
		assertThatUnitIsOk(e);
		double calculatedAmount = e.getQuantity().getAmount()*RATE;
		return new Money(calculatedAmount, CurrencyUnit.EURO);
	}

	private void assertThatUnitIsOk(Entry e) {
		if (e.getQuantity().getUnit()!=Units.KWH)
			throw new IllegalArgumentException("Illegale Unit for billing plan:" + e.getQuantity().getUnit());		
	}

	@Override
	public void process(AccountingEvent e) {
		throw new UnsupportedOperationException("Not yet implemented");		
	}
}
