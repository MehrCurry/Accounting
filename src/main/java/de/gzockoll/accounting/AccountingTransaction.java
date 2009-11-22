package de.gzockoll.accounting;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import de.gzockoll.quantity.Quantity;
import de.gzockoll.types.money.Money;

public class AccountingTransaction implements Subject {
	private Date date;
	private Collection<Entry> entries = new HashSet<Entry>();
	private boolean posted = false;

	public AccountingTransaction(Date date) {
		super();
		this.date = date;
	}

	public void add(Quantity amount, Account account, String text) {
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

	public Quantity balance() {
		Quantity result = Quantity.ZERO;
		for (Entry e : entries) {
			result = result.add(e.getQuantity());
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

	public AccountingTransaction inverse(Date d) {
		AccountingTransaction trans = new AccountingTransaction(d);
		for (Entry e : entries) {
			trans.add(e.getQuantity().negate(), e.getAccount(), "Storno: "
					+ e.getText());
		}
		return trans;
	}

	@Override
	public String toString() {
		return "AccountingTransaction: posted=" + posted + ", Balance=" + balance() + ", Entries=" + entries.size();
	}
}
