package de.gzockoll.accounting;

import java.util.HashMap;
import java.util.Map;

import de.gzockoll.quantity.Unit;

public class AccountingPlan {
	private static Map<String,Account> plan=new HashMap<String, Account>();

	public static Account getAccount(String string) {
		Account a=plan.get(string);
		if (a==null)
			a=new NullAccount();
		return a;
	}

	public static SummaryAccount createSummaryAccount(String key, Unit unit) {
		SummaryAccount a=new SummaryAccount(unit);
		return (SummaryAccount) addAccount(key, a);
	}

	private static Account addAccount(String key, Account a) {
		plan.put(key,a);
		return a;
	}

	public static DetailAccount createDetailAccount(String key, Unit unit) {
		DetailAccount a=new DetailAccount(unit);
		return (DetailAccount) addAccount(key, a);
	}
}
