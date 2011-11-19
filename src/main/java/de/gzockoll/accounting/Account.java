package de.gzockoll.accounting;

import java.util.Collection;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public interface Account {
	public String getName();
	
	public abstract CurrencyUnit getUnit();

	public abstract Collection<Entry> getEntries();

	public abstract Money balance();

	public abstract int entryCount();

	public abstract void add(Entry entry);

	public void post(Entry entry);
}