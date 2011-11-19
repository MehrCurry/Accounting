package de.gzockoll.accounting;

import java.util.HashMap;
import java.util.Map;

import org.joda.money.CurrencyUnit;
import org.osgi.util.measurement.Unit;

public class AccountingPlan {
	private static Map<String,Account> plan=new HashMap<String, Account>();

	public static Account getAccount(String string) {
		Account a=plan.get(string);
		if (a==null)
			a=new NullAccount();
		return a;
	}

	public static SummaryAccount createSummaryAccount(String key, CurrencyUnit unit) {
		SummaryAccount a=new SummaryAccount(key,unit);
		return (SummaryAccount) addAccount(key, a);
	}

	private static Account addAccount(String key, Account a) {
		plan.put(key,a);
		return a;
	}

	public static DetailAccount createDetailAccount(String key, CurrencyUnit unit) {
		DetailAccount a=new DetailAccount(key,unit);
		return (DetailAccount) addAccount(key, a);
	}
}
