package de.gzockoll.accounting;

import java.util.Date;

import de.gzockoll.quantity.Quantity;

public class Usage extends AccountingEvent {
	private Quantity amount;

	public Usage(Quantity amount, Date whenOccurred, Date whenNoticed,
			Customer customer) {
		super(EventType.USAGE, whenOccurred, whenNoticed, customer);
		this.amount = amount;
	}

	public Quantity getAmount() {
		return amount;
	}

	public double getRate() {
		return ((Customer) getSubject()).getServiceAgreement().getRate(
				getWhenOccurred());
	}
}
