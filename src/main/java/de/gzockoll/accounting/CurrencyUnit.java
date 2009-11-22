package de.gzockoll.accounting;

import java.util.Currency;

import de.gzockoll.quantity.Unit;

public class CurrencyUnit implements Unit {
	public static CurrencyUnit EURO=new CurrencyUnit(Currency.getInstance("EUR"));

	private Currency currency;

	public CurrencyUnit(Currency currency) {
		this.currency=currency;
	}

	public Currency getCurrency() {
		return currency;
	}
	
	@Override
	public String toString() {
		return currency.toString();
	}
}	
