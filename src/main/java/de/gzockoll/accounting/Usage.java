package de.gzockoll.accounting;

import java.math.BigDecimal;
import java.util.Date;

public class Usage extends AccountingEvent {
	private BigDecimal amount;

	public Usage(BigDecimal amount, Date whenOccurred, Date whenNoticed,
			Customer customer) {
		super(EventType.USAGE, whenOccurred, whenNoticed, customer);
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public double getRate() {
		return ((Customer) getSubject()).getServiceAgreement().getRate(
				getWhenOccurred());
	}
}
