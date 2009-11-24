package de.gzockoll.accounting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import de.gzockoll.quantity.Quantity;
import de.gzockoll.quantity.NullQuantity;
import de.gzockoll.quantity.Unit;

public class DetailAccount<T extends Quantity> implements Account<T> {
	private String name;

	DetailAccount(String name, Unit unit) {
		super();
		this.name = name;
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.gzockoll.accounting.Account#getEntries()
	 */
	@SuppressWarnings("unchecked")
	public Collection<Entry<T>> getEntries() {
		ArrayList<Entry<T>> e = (ArrayList<Entry<T>>) entries;
		return Collections.unmodifiableCollection((Collection<Entry<T>>) e
				.clone());
	}

	private Unit unit;
	private Collection<Entry<T>> entries = new ArrayList<Entry<T>>();

	public void book(Entry<T> entry) {
		assertSameUnit(entry);
		entries.add(entry);
	}

	private void assertSameUnit(Entry entry) {
		if (!unit.equals(entry.getQuantity().getUnit()))
			throw new IllegalArgumentException();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.gzockoll.accounting.Account#saldo()
	 */
	public Quantity saldo() {
		T result = (T) new NullQuantity();
		for (Entry<T> e : entries) {
			if (result == null)
				result = e.getQuantity();
			else
				result = (T) result.add(e.getQuantity());
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.gzockoll.accounting.Account#entryCount()
	 */
	public int entryCount() {
		return entries.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.gzockoll.accounting.Account#add(de.gzockoll.accounting.Entry)
	 */
	public void add(Entry entry) {
		if (!unit.equals(entry.getQuantity().getUnit()))
			throw new IllegalArgumentException("Entry has wrong unit: " + entry);
		entries.add(entry);
	}

	public Unit getUnit() {
		return unit;
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("Account " + name + ": " + saldo() + "\n");
		for (Entry e : entries) {
			s.append("  " + e + "\n");
		}
		return s.toString();
	}

	public Class typeClass() {
		return ReflectionUtil.getTypeArguments(DetailAccount.class, getClass())
				.get(0);
	}
}
