package de.gzockoll.accounting;

public interface Subject {

	void addEntry(Entry newEntry, AccountType type);

	void process(AccountingEvent accountingEvent);

}
