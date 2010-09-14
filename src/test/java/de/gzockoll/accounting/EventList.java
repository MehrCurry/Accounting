package de.gzockoll.accounting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

public class EventList {
	private List<AccountingEvent> events = new ArrayList<AccountingEvent>();
	private boolean shouldOnlyLogProcessingErrors;
	public void add(AccountingEvent event) {
		events.add(event);
		
	}
	public void process() {
        for (AccountingEvent event : unprocessedEvents()) {
            try {
                event.process();
            } catch (Exception e) {
                if (shouldOnlyLogProcessingErrors) logProcessingError (event, e);
                else throw new RuntimeException(e);
            }
        }
	}
	private void logProcessingError(AccountingEvent event, Exception e) {
		System.err.println(event.toString() + e.getLocalizedMessage());
		
	}
	@SuppressWarnings("unchecked")
	private Collection<AccountingEvent> unprocessedEvents() {
		return CollectionUtils.select(events, new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				AccountingEvent event=(AccountingEvent) arg0;
				return !event.isProcessed();
			}
		});
	}
}
