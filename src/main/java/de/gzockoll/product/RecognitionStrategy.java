package de.gzockoll.product;

import java.util.List;

import org.joda.money.Money;
import org.joda.time.base.BaseDateTime;

public interface RecognitionStrategy {
	List<RevenueRecognition>calculateRevenueRecognitions(Money m,BaseDateTime date);
}
