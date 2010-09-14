package de.gzockoll.accounting;

import de.gzockoll.common.types.Timepoint;
import de.gzockoll.quantity.Quantity;

public class EveningDaySplitPR<T extends Quantity> extends EachEntryPR<T> {
	@Override
	protected void processEntry(Entry e) {
		AccountingTransaction tx=new AccountingTransaction(Timepoint.now());
		tx.add(e.getQuantity().negate(), e.getAccount(), "Day/Night split");
		tx.add(e.getQuantity(), getAccountFor(e), e.getText());
		tx.post();
	}

	private Account<T> getAccountFor(Entry<T> e) {
		int hours = e.getWhenCharged().getHours();
		System.out.println(e.getWhenCharged());
		System.out.println(hours);
		
		if (hours>18 || hours<8)
			return AccountingPlan.getAccount("#night");
		else
			return AccountingPlan.getAccount("#day");
	}

	@Override
	public void process(AccountingEvent e) {
		throw new UnsupportedOperationException("Not yet implemented");		
	}
}
