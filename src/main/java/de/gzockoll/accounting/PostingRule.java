package de.gzockoll.accounting;

import de.gzockoll.quantity.Quantity;

public interface PostingRule<T extends Quantity> {
	void processAccount(Account<T> account);
}
