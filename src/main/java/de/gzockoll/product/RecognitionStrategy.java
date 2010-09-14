package de.gzockoll.product;

import java.util.Date;
import java.util.List;

import org.joda.time.base.BaseDateTime;

import de.gzockoll.quantity.Quantity;

public interface RecognitionStrategy {
	List<RevenueRecognition>calculateRevenueRecognitions(Quantity a,BaseDateTime date);
}
