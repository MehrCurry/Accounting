package de.gzockoll.accounting;


import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.gzockoll.common.types.TimeFactory;
import de.gzockoll.quantity.Quantity;
import de.gzockoll.quantity.SimpleQuantity;
import de.gzockoll.quantity.Unit;
import de.gzockoll.quantity.Units;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class EveningDaySplitPRTest {
	private DetailAccount main;
	private DetailAccount day;
	private DetailAccount night;
	private Quantity tenKwh = new SimpleQuantity(10, Units.KWH);

	@Before
	public void setUp() throws Exception {
		Unit unit = Units.KWH;
		main=AccountingPlan.createDetailAccount("Main",unit);
		day=AccountingPlan.createDetailAccount("#day", unit);
		night=AccountingPlan.createDetailAccount("#night", unit);
		
		Entry e=new Entry(main, Units.KWH.amount(10) , "Test").post();
		e.setWhenCharged(TimeFactory.parseDateTime("21.4.2009 10:00"));
		e=new Entry(main, Units.KWH.amount(20) , "Test").post();
		e.setWhenCharged(TimeFactory.parseDateTime("21.4.2009 17:00"));
		e=new Entry(main, Units.KWH.amount(15) , "Test").post();
		e.setWhenCharged(TimeFactory.parseDateTime("21.4.2009 18:00"));
		e=new Entry(main, Units.KWH.amount(18) , "Test").post();
		e.setWhenCharged(TimeFactory.parseDateTime("21.4.2009 21:00"));
	}

	@Test
	public void testPostingRule() {
		PostingRule pr=new EveningDaySplitPR();
		pr.processAccount(main);
		System.out.println(main);
		System.out.println(day);
		System.out.println(night);
		
		
		assertThat(day.entryCount(),is(3));
		assertThat(day.balance(),is((Quantity)new SimpleQuantity(45, Units.KWH)));
		assertThat(night.entryCount(),is(1));
		assertThat(night.balance(),is((Quantity)new SimpleQuantity(18, Units.KWH)));
		assertThat(main.entryCount(),is(8));
		assertThat(main.balance(),is((Quantity)new SimpleQuantity(0, Units.KWH)));
	}
}
