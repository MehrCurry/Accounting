package de.gzockoll.accounting;

public interface PostingRule {
	void processAccount(Account account);
}
