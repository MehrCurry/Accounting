package de.gzockoll.accounting;

import java.util.HashMap;
import java.util.Map;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class Customer implements Subject {
	private ServiceAgreement serviceAgreement;

	private Map<AccountType, Account> accounts, savedRealAccounts;

	private String name;
    public Customer(String name) {
        this.name = name;
        setUpAccounts();
    }
    void setUpAccounts() {
        accounts = new HashMap<AccountType, Account>();
        for (AccountType type : AccountType.values())
            accounts.put(type, new DetailAccount(CurrencyUnit.EUR, type));
    }
    public Account accountFor(AccountType type) {
        assert accounts.containsKey(type);
        return accounts.get(type);
    }
    public void addEntry(Entry arg, AccountType type) {
        accountFor(type).post(arg);
    }
    public Money balanceFor(AccountType key) {
        return accountFor(key).balance();
    }
	public ServiceAgreement getServiceAgreement() {
		return serviceAgreement;
	}
	public void setServiceAgreement(ServiceAgreement serviceAgreement) {
		this.serviceAgreement = serviceAgreement;
	}

    public void process(AccountingEvent e) {
        serviceAgreement.process(e);
    }
}
