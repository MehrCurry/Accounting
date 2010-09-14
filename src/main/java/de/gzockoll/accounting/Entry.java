package de.gzockoll.accounting;

import java.util.Date;

import de.gzockoll.quantity.Quantity;
import de.gzockoll.types.money.Money;

public class Entry<T extends Quantity> {
	private Date whenCharged;
	private Date whenBooked;

	private T quantity;
	private String text;
	private Account<T> account;

	void setWhenCharged(Date whenCharged) {
		this.whenCharged = whenCharged;
	}

	public Entry(Account<T> a,T quantity, String text) {
		super();
		this.account=a;
		this.whenCharged = new Date();
		this.quantity = quantity;
		this.text = text;
	}

	public Entry(T quantity, Date whenNoticed) {
		this.quantity=quantity;
		this.whenBooked=whenNoticed;
	}

	public Date getWhenCharged() {
		return whenCharged;
	}

	public Date getWhenBooked() {
		return whenBooked;
	}

	public T getQuantity() {
		return quantity;
	}

	public String getText() {
		return text;
	}

	public Entry<T> post() {
		if (whenBooked != null)
			throw new IllegalStateException("Already booked!");
		whenBooked = new Date();
		account.add(this);
		return this;
	}

	public Account<T> getAccount() {
		return account;
	}
	
	@Override
	public String toString() {
		return whenCharged + "|" + whenBooked + "|" + text + "|" + quantity;
	}
}
