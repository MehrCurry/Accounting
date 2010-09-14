package de.gzockoll.accounting;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.gzockoll.common.types.TemporalCollection;

@SuppressWarnings("rawtypes")
public class ServiceAgreement {
	private Map postingRules = new HashMap();
	private TemporalCollection rates = new TemporalCollection();

	public void addPostingRule(EventType eventType, PostingRule rule, Date date) {
		if (postingRules.get(eventType) == null)
			postingRules.put(eventType, new TemporalCollection());
		getRulesTemporalCollectionFor(eventType).put(date, rule);
	}

	private TemporalCollection getRulesTemporalCollectionFor(EventType eventType) {
		TemporalCollection result = (TemporalCollection) postingRules
				.get(eventType);
		assert result != null;
		return result;
	}

	public double getRate(Date whenOccurred) {
		return (Double) rates.get(whenOccurred);
	}

	public void setRate(double d, Date date) {
		rates.put(date, d);
	}

	public void process(AccountingEvent e) {
		getPostingRule(e).process(e);
	}

	private PostingRule getPostingRule(AccountingEvent event) {
		final TemporalCollection rules = getRulesTemporalCollectionFor(event
				.getType());
		if (rules == null)
			throw new MissingPostingRuleException(this, event);
		try {
			return (PostingRule) rules.get(event.getWhenOccurred());
		} catch (IllegalArgumentException e) {
			throw new MissingPostingRuleException(this, event);
		}
	}

}
