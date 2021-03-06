package de.gzockoll.accounting;

import org.joda.money.Money;

public class MultiplyByRatePR extends AbstractPostingRule {

	protected MultiplyByRatePR(AccountType type, boolean isTaxable) {
		super(type, isTaxable);
	}

	public void processAccount(Account account) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Money calculateAmount(AccountingEvent evt) {
		Usage usageEvent = (Usage) evt;
		return Money.euros(usageEvent.getAmount().getAmount()
				* usageEvent.getRate());
	}

	@Override
	protected Money generateTax(AccountingEvent evt) {
		// TODO Auto-generated method stub
		return null;
	}

}
