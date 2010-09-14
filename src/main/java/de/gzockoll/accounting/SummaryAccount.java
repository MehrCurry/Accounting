package de.gzockoll.accounting;

import java.util.Collection;
import java.util.HashSet;

import de.gzockoll.quantity.NullQuantity;
import de.gzockoll.quantity.Quantity;
import de.gzockoll.quantity.Unit;

public class SummaryAccount<T extends Quantity> implements Account<T> {
	private Collection<Account<T>> accounts=new HashSet<Account<T>>();
	private Unit unit;
	private String name;

	public Unit getUnit() {
		return unit;
	}

	
	public String getName() {
		return name;
	}


	public SummaryAccount(String name,Unit unit) {
		super();
		this.name=name;
		this.unit=unit;
	}

	public void add(Entry<T> entry) {
		throw new UnsupportedOperationException("Write operations on summary accounts are unsupported!");		
	}

	public void AddAccount(Account<T> a) {
		assertThatAccountHasSameUnit(a);
		accounts.add(a);
	}
	
	private void assertThatAccountHasSameUnit(Account<T> a) {
		if(!unit.equals(a.getUnit()))
			throw new IllegalArgumentException("Wrong unit");
		
	}

	public int entryCount() {
		int count=0;
		for (Account<T> a:accounts)
			count+=a.entryCount();
		return count;
	}

	public Collection<Entry<T>> getEntries() {
		Collection<Entry<T>> entries = new HashSet<Entry<T>>();
		for (Account<T> a:accounts)
			entries.addAll(a.getEntries());
		return entries;
	}

	@SuppressWarnings("unchecked")
	public Quantity balance() {
		T result = (T) new NullQuantity();
		for (Account<T> a:accounts)
				result=(T) result.add(a.balance());
		return result;
	}


	@Override
	public void post(Entry<T> entry) {
		throw new UnsupportedOperationException("Write operations on summary accounts are unsupported!");		
	}
}
