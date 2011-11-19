package de.gzockoll.product;

import org.joda.money.Money;
import org.joda.time.ReadableDateTime;

public class RevenueRecognition {
	Money quantity;
	ReadableDateTime date;
	
	public RevenueRecognition(Money quantity, ReadableDateTime date) {
		super();
		this.quantity = quantity;
		this.date = date;
	}

	public boolean isRecognizableByDate(ReadableDateTime d) {
		return !date.isAfter(d);
	}
}
