package de.gzockoll.accounting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.osgi.util.measurement.Unit;

public class DetailAccount implements Account {
	private String name;
	private AccountType type;

	public DetailAccount(String name, CurrencyUnit unit) {
		super();
		this.name = name;
		this.unit = unit;
	}

	public DetailAccount(CurrencyUnit unit, AccountType type) {
		super();
		this.name = type.name();
		this.unit = unit;
		this.type = type;
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
	public Collection<Entry> getEntries() {
		ArrayList<Entry> e = (ArrayList<Entry>) entries;
		return Collections.unmodifiableCollection((Collection<Entry>) e
				.clone());
	}

	private CurrencyUnit unit;
	private Collection<Entry> entries = new ArrayList<Entry>();

	public void book(Entry entry) {
		assertSameUnit(entry);
		entries.add(entry);
	}

	private void assertSameUnit(Entry entry) {
		if (!unit.equals(entry.getQuantity().getCurrencyUnit()))
			throw new IllegalArgumentException();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.gzockoll.accounting.Account#saldo()
	 */
	public Money balance() {
		Money result = Money.zero(unit);
		for (Entry e : entries) {
			if (result == null)
				result = e.getQuantity();
			else
				result = result.plus(e.getQuantity());
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
		if (!unit.equals(entry.getQuantity().getCurrencyUnit()))
			throw new IllegalArgumentException("Entry has wrong unit: " + entry);
		entries.add(entry);
	}

	public CurrencyUnit getUnit() {
		return unit;
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("Account " + name + ": " + balance() + "\n");
		for (Entry e : entries) {
			s.append("  " + e + "\n");
		}
		return s.toString();
	}

	public void post(Entry entry) {
		add(entry);
		
	}

//	public Class typeClass() {
//		return ReflectionUtil.getTypeArguments(DetailAccount.class, getClass())
//				.get(0);
//	}
	
	
}
