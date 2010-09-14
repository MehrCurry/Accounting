package de.gzockoll.accounting;

import java.math.BigDecimal;

public enum TaxRate {
	NORMAL("0.19"),HALF("0.07"),NONE("0.0");
	
	private BigDecimal rate;

	TaxRate(String rate) {
		this.rate=new BigDecimal(rate);
	}

	public BigDecimal getRate() {
		return rate;
	}
}
