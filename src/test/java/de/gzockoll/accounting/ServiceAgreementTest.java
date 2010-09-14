package de.gzockoll.accounting;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import de.gzockoll.quantity.Units;
import de.gzockoll.types.money.Money;

public class ServiceAgreementTest {
    private ServiceAgreement simpleAgreement() {
        ServiceAgreement result = new ServiceAgreement();
        result.setRate(10, new Date(0));
        result.addPostingRule(EventType.USAGE,
                new MultiplyByRatePR(AccountType.BASE_USAGE, false),
                new Date(1999, 10, 1));
        result.addPostingRule(EventType.SERVICE_CALL,
                new AmountFormulaPR(1.1, Money.euros(10), AccountType.SERVICE, false),
                new Date(1999, 10, 1));
        result.addPostingRule(EventType.SERVICE_CALL,
                new AmountFormulaPR(1.1, Money.euros(15), AccountType.SERVICE, false),
                new Date(1999, 12, 1));
        return result;
    }

	@Test
    public void testSimpleRule() {
        Customer mycroft = new Customer ("Mycroft Homes");
        mycroft.setServiceAgreement(simpleAgreement());
        AccountingEvent usageEvent = new Usage(Units.KWH.amount(50),
                new Date(1999, 10, 1),
                new Date(1999, 10, 15),
                mycroft);
        EventList eventList = new EventList();
        eventList.add(usageEvent);
        eventList.process();
        assertEquals(Money.euros(500), mycroft.balanceFor(AccountType.BASE_USAGE));
        assertEquals(Money.euros(0), mycroft.balanceFor(AccountType.SERVICE));
        assertEquals(Money.euros(0), mycroft.balanceFor(AccountType.TAX));
    }

	@Test
    public void testSimpleRule3() {
        Customer mycroft = new Customer("Mycroft Homes");
        mycroft.setServiceAgreement(simpleAgreement());
        EventList eventList = new EventList();

        //service call before rule change
        AccountingEvent call1 = new MonetaryEvent(Money.euros(100),
                EventType.SERVICE_CALL,
                new Date(1999, 10, 1),
                new Date(1999, 10, 15),
                mycroft);
        eventList.add(call1);
        eventList.process();
        assertEquals(Money.dollars(0), mycroft.balanceFor(AccountType.BASE_USAGE));
        assertEquals(Money.dollars(120), mycroft.balanceFor(AccountType.SERVICE));
        assertEquals(Money.dollars(0), mycroft.balanceFor(AccountType.TAX));

        //service call after rule change
        AccountingEvent call2 = new MonetaryEvent(Money.euros(100),
                EventType.SERVICE_CALL,
                new Date(1999, 12, 1),
                new Date(1999, 12, 15),
                mycroft);
        eventList.add(call2);
        eventList.process();
        assertEquals(Money.dollars(0), mycroft.balanceFor(AccountType.BASE_USAGE));
        assertEquals(Money.dollars(245), mycroft.balanceFor(AccountType.SERVICE));
        assertEquals(Money.dollars(0), mycroft.balanceFor(AccountType.TAX));
    }

}
