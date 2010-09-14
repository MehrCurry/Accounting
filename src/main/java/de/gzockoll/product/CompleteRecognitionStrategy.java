package de.gzockoll.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.joda.time.base.BaseDateTime;

import de.gzockoll.quantity.Quantity;

public class CompleteRecognitionStrategy implements RecognitionStrategy {

	@Override
	public List<RevenueRecognition> calculateRevenueRecognitions(Quantity q,BaseDateTime date) {
		List<RevenueRecognition>l=new ArrayList<RevenueRecognition>();
		l.add(new RevenueRecognition(q, date));
		return Collections.unmodifiableList(l);
	}

}
