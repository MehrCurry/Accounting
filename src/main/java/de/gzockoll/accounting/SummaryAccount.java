package de.gzockoll.accounting;

import java.util.Collection;
import java.util.HashSet;

import de.gzockoll.quantity.Quantity;
import de.gzockoll.quantity.Unit;

public class SummaryAccount implements Account {
	private Collection<Account> accounts=new HashSet<Account>();
	private Unit unit;

	public Unit getUnit() {
		return unit;
	}

	public SummaryAccount(Unit unit) {
		this.unit=unit;
	}

	public void add(Entry entry) {
		throw new UnsupportedOperationException("Write operations on summary accounts are unsupported!");		
	}

	public void AddAccount(Account a) {
		assertThatAccountHasSameUnit(a);
		accounts.add(a);
	}
	
	private void assertThatAccountHasSameUnit(Account a) {
		if(!unit.equals(a.getUnit()))
			throw new IllegalArgumentException("Wrong unit");
		
	}

	public int entryCount() {
		int count=0;
		for (Account a:accounts)
			count+=a.entryCount();
		return count;
	}

	public Collection<Entry> getEntries() {
		Collection<Entry> entries = new HashSet<Entry>();
		for (Account a:accounts)
			entries.addAll(a.getEntries());
		return entries;
	}

	public Quantity saldo() {
		Quantity result=Quantity.ZERO;
		for (Account a:accounts)
			result=result.add(a.saldo());
		return result;
	}
}
