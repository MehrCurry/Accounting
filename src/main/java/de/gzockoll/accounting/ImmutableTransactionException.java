package de.gzockoll.accounting;

public class ImmutableTransactionException extends RuntimeException {

	public ImmutableTransactionException(String msg) {
		super(msg);
	}

}
