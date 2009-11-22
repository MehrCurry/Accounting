package de.gzockoll.accounting;

import java.util.Date;

import de.gzockoll.quantity.Quantity;

public class Entry {
	private Date whenCharged;
	private Date whenBooked;

	private Quantity quantity;
	private String text;
	private Account account;

	void setWhenCharged(Date whenCharged) {
		this.whenCharged = whenCharged;
	}

	public Entry(Account a,Quantity quantity, String text) {
		super();
		this.account=a;
		this.whenCharged = new Date();
		this.quantity = quantity;
		this.text = text;
	}

	public Date getWhenCharged() {
		return whenCharged;
	}

	public Date getWhenBooked() {
		return whenBooked;
	}

	public Quantity getQuantity() {
		return quantity;
	}

	public String getText() {
		return text;
	}

	public Entry post() {
		if (whenBooked != null)
			throw new IllegalStateException("Already booked!");
		whenBooked = new Date();
		account.add(this);
		return this;
	}

	public Account getAccount() {
		return account;
	}
	
	@Override
	public String toString() {
		return whenCharged + "|" + whenBooked + "|" + text + "|" + quantity;
	}
}
