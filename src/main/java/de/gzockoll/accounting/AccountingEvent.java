package de.gzockoll.accounting;

import java.util.Date;

public class AccountingEvent {
    private EventType type;
    private Date whenOccurred;
    private Date whenNoticed;
    private Subject subject;
	private Entry resultingEntry;
	private boolean isProcessed=false;
	public AccountingEvent(EventType type, Date whenOccurred, Date whenNoticed,
			Subject subject) {
		super();
		this.type = type;
		this.whenOccurred = whenOccurred;
		this.whenNoticed = whenNoticed;
		this.subject = subject;
	}
	public EventType getType() {
		return type;
	}
	public Date getWhenOccurred() {
		return whenOccurred;
	}
	public Date getWhenNoticed() {
		return whenNoticed;
	}
	public Subject getSubject() {
		return subject;
	}
	public void addResultingEntry(Entry entry) {
		this.resultingEntry=entry;
		
	}
     
    public void process() {
        assert !isProcessed;
        // if (adjustedEvent != null) adjustedEvent.reverse();
        subject.process(this);
        markProcessed();
    }
	public void markProcessed() {
		isProcessed=true;	
	}
	public boolean isProcessed() {
		return isProcessed;
	}
}
