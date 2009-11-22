package de.gzockoll.accounting;

import java.util.Currency;

import de.gzockoll.quantity.Quantity;

public class Money extends Quantity {

	public Money(long amount, Currency currency) {
		super(amount, new CurrencyUnit(currency));
	}

	public Money(long amount, CurrencyUnit cu) {
		super(amount,cu);
	}
}
