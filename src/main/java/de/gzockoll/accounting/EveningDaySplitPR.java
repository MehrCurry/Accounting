package de.gzockoll.accounting;

import org.joda.time.DateTime;


public class EveningDaySplitPR extends EachEntryPR {
	@Override
	protected void processEntry(Entry e) {
		AccountingTransaction tx=new AccountingTransaction(new DateTime());
		tx.add(e.getQuantity().negated(), e.getAccount(), "Day/Night split");
		tx.add(e.getQuantity(), getAccountFor(e), e.getText());
		tx.post();
	}

	private Account getAccountFor(Entry e) {
		int hours = e.getWhenCharged().getHours();
		System.out.println(e.getWhenCharged());
		System.out.println(hours);
		
		if (hours>18 || hours<8)
			return AccountingPlan.getAccount("#night");
		else
			return AccountingPlan.getAccount("#day");
	}

	public void process(AccountingEvent e) {
		throw new UnsupportedOperationException();
		
	}
}
