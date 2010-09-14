package de.gzockoll.accounting;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import de.gzockoll.quantity.NullQuantity;
import de.gzockoll.quantity.Quantity;
import de.gzockoll.quantity.SimpleQuantity;
import de.gzockoll.quantity.Unit;
import de.gzockoll.types.money.CurrencyUnit;
import de.gzockoll.types.money.Money;

public class DetailAccount<T extends Quantity> implements Account<T> {
	private String name;
	private AccountType type;

	public DetailAccount(String name, Unit unit) {
		super();
		this.name = name;
		this.unit = unit;
	}

	public DetailAccount(Unit unit, AccountType type) {
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
	public Quantity balance() {
		T result = getZeroBalance();
		for (Entry<T> e : entries) {
			if (result == null)
				result = e.getQuantity();
			else
				result = (T) result.add(e.getQuantity());
		}
		return result;
	}

	public T getZeroBalance() {
		return (T) unit.getZeroQuantity();
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
		s.append("Account " + name + ": " + balance() + "\n");
		for (Entry e : entries) {
			s.append("  " + e + "\n");
		}
		return s.toString();
	}

	@Override
	public void post(Entry<T> entry) {
		add(entry);
		
	}

//	public Class typeClass() {
//		return ReflectionUtil.getTypeArguments(DetailAccount.class, getClass())
//				.get(0);
//	}
	
	
}
