package de.gzockoll.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.money.Money;
import org.joda.time.base.BaseDateTime;

public class CompleteRecognitionStrategy implements RecognitionStrategy {

	public List<RevenueRecognition> calculateRevenueRecognitions(Money m,BaseDateTime date) {
		List<RevenueRecognition>l=new ArrayList<RevenueRecognition>();
		l.add(new RevenueRecognition(m, date));
		return Collections.unmodifiableList(l);
	}

}
