package de.gzockoll.accounting;

import java.util.Collection;
import java.util.Collections;

import de.gzockoll.quantity.NullQuantity;
import de.gzockoll.quantity.Quantity;
import de.gzockoll.quantity.Unit;

@SuppressWarnings("rawtypes")
public class NullAccount implements Account {

	public void add(Entry entry) {
		throw new UnsupportedOperationException("Cannot add to a null account");
	}

	public int entryCount() {
		return 0;
	}

	@SuppressWarnings("unchecked")
	public Collection<Entry> getEntries() {
		return Collections.EMPTY_LIST;
	}

	public Quantity saldo() {
		return new NullQuantity();
	}

	public Unit getUnit() {
		return new Unit() {};
	}
	
	public String getName() {	
		return "<Null Account>";
	}

}
