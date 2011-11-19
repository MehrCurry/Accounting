package de.gzockoll.accounting;

import java.util.Collection;
import java.util.HashSet;

import org.joda.money.Money;
import org.joda.time.DateTime;

public class AccountingTransaction implements Subject {
	private DateTime date;
	private Collection<Entry> entries = new HashSet<Entry>();
	private boolean posted = false;

	public AccountingTransaction(DateTime date) {
		super();
		this.date = date;
	}

	public void add(Money amount, Account account, String text) {
		assertNotPosted();
		entries.add(new Entry(account, amount, text));
	}

	private void assertCanPost() {
		if (!canPost())
			throw new UnableToPostException();
	}

	public boolean canPost() {
		return balance().isZero();
	}

	@SuppressWarnings("unchecked")
	public Money balance() {
		Money result=null;
		for (Entry e : entries) {
				if (result==null)
					result=e.getQuantity();
				else
					result = result.plus(e.getQuantity());
		}
		return result;
	}

	private void assertNotPosted() {
		if (posted)
			throw new ImmutableTransactionException(
					"Transaction has been posted already!");
	}

	public void post() {
		assertNotPosted();
		assertCanPost();
		AuditLog.add("transaction.post",this);
		for (Entry e : entries) {
			e.post();
		}
		posted = true;
	}

	public AccountingTransaction inverse(DateTime d) {
		AccountingTransaction trans = new AccountingTransaction(d);
		for (Entry e : entries) {
			trans.add(e.getQuantity().negated(), e.getAccount(), "Storno: "
					+ e.getText());
		}
		return trans;
	}

	@Override
	public String toString() {
		return "AccountingTransaction: posted=" + posted + ", Balance=" + balance() + ", Entries=" + entries.size();
	}

	public void addEntry(Entry newEntry, AccountType type) {
		throw new UnsupportedOperationException("Not yet implemented");		
	}

	public void process(AccountingEvent accountingEvent) {
		throw new UnsupportedOperationException("Not yet implemented");		
	}
}
