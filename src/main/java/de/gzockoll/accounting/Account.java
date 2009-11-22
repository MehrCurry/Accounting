package de.gzockoll.accounting;

import java.util.Collection;

import de.gzockoll.quantity.Quantity;
import de.gzockoll.quantity.Unit;

public interface Account {
	public abstract Unit getUnit();

	public abstract Collection<Entry> getEntries();

	public abstract Quantity saldo();

	public abstract int entryCount();

	public abstract void add(Entry entry);
}