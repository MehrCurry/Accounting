package de.gzockoll.accounting;

import java.util.Collection;
import java.util.Collections;

import de.gzockoll.quantity.Quantity;
import de.gzockoll.quantity.Unit;

public class NullAccount implements Account {

	public void add(Entry entry) {
	}

	public int entryCount() {
		return 0;
	}

	@SuppressWarnings("unchecked")
	public Collection<Entry> getEntries() {
		return Collections.EMPTY_LIST;
	}

	public Quantity saldo() {
		return Quantity.ZERO;
	}

	public Unit getUnit() {
		return new None();
	}
	
	private static class None implements Unit {
		
	}
}
