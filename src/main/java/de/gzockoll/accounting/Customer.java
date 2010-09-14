package de.gzockoll.accounting;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import de.gzockoll.quantity.Quantity;
import de.gzockoll.types.money.CurrencyUnit;
import de.gzockoll.types.money.Money;

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
            accounts.put(type, new DetailAccount(CurrencyUnit.EURO, type));
    }
    public Account accountFor(AccountType type) {
        assert accounts.containsKey(type);
        return accounts.get(type);
    }
    public void addEntry(Entry arg, AccountType type) {
        accountFor(type).post(arg);
    }
    public Quantity balanceFor(AccountType key) {
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
