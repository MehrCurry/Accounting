package de.gzockoll.accounting;

import de.gzockoll.quantity.Quantity;

public abstract class EachEntryPR<T extends Quantity> implements PostingRule<T> {

	public void processAccount(Account<T> account) {
		for (Entry<T>  e:account.getEntries()) {
			processEntry((Entry<T>) e);
		}
	}

	protected abstract void processEntry(Entry<T> e);
}
