package de.gzockoll.product;

import org.joda.time.ReadableDateTime;

import de.gzockoll.quantity.Quantity;

public class RevenueRecognition {
	Quantity quantity;
	ReadableDateTime date;
	
	public RevenueRecognition(Quantity quantity, ReadableDateTime date) {
		super();
		this.quantity = quantity;
		this.date = date;
	}

	public boolean isRecognizableByDate(ReadableDateTime d) {
		return !date.isAfter(d);
	}
}
