package de.gzockoll.accounting;

import de.gzockoll.types.money.Money;

public abstract class AbstractPostingRule implements PostingRule {
    private boolean isTaxable;
	private AccountType type;
    protected AbstractPostingRule(AccountType type, boolean isTaxable) {
        this.type = type;
        this.isTaxable = isTaxable;
    }
    
    public void process(AccountingEvent evt) {
        makeEntry(evt, calculateAmount(evt));
        if (isTaxable) generateTax(evt);
    }
    abstract protected Money calculateAmount(AccountingEvent evt);
    abstract protected Money generateTax(AccountingEvent evt);
    private void makeEntry(AccountingEvent evt, Money amount) {
        Entry newEntry = new Entry(amount, evt.getWhenNoticed());
        evt.getSubject().addEntry(newEntry, type);
        evt.addResultingEntry(newEntry);
    }

}
