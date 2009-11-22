package de.gzockoll.accounting;

public abstract class EachEntryPR implements PostingRule {

	public void processAccount(Account account) {
		for (Entry e:account.getEntries()) {
			processEntry(e);
		}
	}

	protected abstract void processEntry(Entry e);
}
