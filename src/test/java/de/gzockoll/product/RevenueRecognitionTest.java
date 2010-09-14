package de.gzockoll.product;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;
import org.joda.time.DateMidnight;
import org.junit.Test;

import de.gzockoll.types.money.Money;


public class RevenueRecognitionTest {
	@Test
	public void testIsRecognizableBy() {
		RevenueRecognition rev=new RevenueRecognition(Money.euros(10),new DateMidnight(2000,1,1));
		assertThat(rev.isRecognizableByDate(new DateMidnight(2000,1,2)),is(true));
		assertThat(rev.isRecognizableByDate(new DateMidnight(1999,12,31)),is(false));
		assertThat(rev.isRecognizableByDate(new DateMidnight(2000,1,1)),is(true));
	}
}
