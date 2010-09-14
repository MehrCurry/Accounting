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

	public Quantity balance() {
		return new NullQuantity();
	}

	public Unit getUnit() {
		throw new UnsupportedOperationException(getClass().getName() + "does not understand method post()");
	}
	
	public String getName() {	
		return "<Null Account>";
	}

	@Override
	public void post(Entry entry) {
		throw new UnsupportedOperationException(getClass().getName() + "does not understand method post()");
		
	}

}
