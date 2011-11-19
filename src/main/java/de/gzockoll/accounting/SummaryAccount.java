package de.gzockoll.accounting;

import java.util.Collection;
import java.util.HashSet;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class SummaryAccount implements Account {
	private Collection<Account> accounts=new HashSet<Account>();
	private CurrencyUnit unit;
	private String name;

	public CurrencyUnit getUnit() {
		return unit;
	}

	
	public String getName() {
		return name;
	}


	public SummaryAccount(String name,CurrencyUnit unit) {
		super();
		this.name=name;
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

	@SuppressWarnings("unchecked")
	public Money balance() {
		Money result = Money.zero(unit);
		for (Account a:accounts)
				result=result.plus(a.balance());
		return result;
	}


	public void post(Entry entry) {
		throw new UnsupportedOperationException("Write operations on summary accounts are unsupported!");		
	}
}
