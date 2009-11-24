package de.gzockoll.accounting;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import de.gzockoll.quantity.Quantity;
import de.gzockoll.types.money.Money;

public class AccountingTransaction<T extends Quantity> implements Subject {
	private Date date;
	private Collection<Entry<T>> entries = new HashSet<Entry<T>>();
	private boolean posted = false;

	public AccountingTransaction(Date date) {
		super();
		this.date = date;
	}

	public void add(T amount, Account<T> account, String text) {
		assertNotPosted();
		entries.add(new Entry<T>(account, amount, text));
	}

	private void assertCanPost() {
		if (!canPost())
			throw new UnableToPostException();
	}

	public boolean canPost() {
		return balance().isZero();
	}

	public T balance() {
		T result=null;
		for (Entry<T> e : entries) {
			if (result==null)
				result=e.getQuantity();
			else
				result = (T) result.add(e.getQuantity());
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

	public AccountingTransaction<T> inverse(Date d) {
		AccountingTransaction<T> trans = new AccountingTransaction<T>(d);
		for (Entry<T> e : entries) {
			trans.add((T) e.getQuantity().negate(), e.getAccount(), "Storno: "
					+ e.getText());
		}
		return trans;
	}

	@Override
	public String toString() {
		return "AccountingTransaction: posted=" + posted + ", Balance=" + balance() + ", Entries=" + entries.size();
	}
}
