package com.github.markash.threesixty.financial.database.accounts;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import junit.framework.TestCase;
import threesixty.financial.base.shared.account.MonthEndAmount;
import threesixty.financial.base.shared.account.MonthEndAmounts;

import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple2;
import org.junit.Assert;

public class MonthEndAmountsTest extends TestCase {

	public void testMonthEndAmountsAdd() {
		
		List<MonthEndAmount> amounts = Arrays.asList(
				new MonthEndAmount(LocalDate.parse("2019-05-31"), 100L),
				new MonthEndAmount(LocalDate.parse("2019-05-31"), 100L),
				new MonthEndAmount(LocalDate.parse("2019-05-31"), 100L),
				new MonthEndAmount(LocalDate.parse("2019-06-30"), 100L),
				new MonthEndAmount(LocalDate.parse("2019-07-31"), 100L)
		);
		
		List<MonthEndAmount> expected = Arrays.asList(
				new MonthEndAmount(LocalDate.parse("2019-05-31"), 300L),
				new MonthEndAmount(LocalDate.parse("2019-06-30"), 100L),
				new MonthEndAmount(LocalDate.parse("2019-07-31"), 100L)
		);
		
		MonthEndAmounts actual = new MonthEndAmounts();
		actual.add(amounts);

		Assert.assertEquals(expected.size(), actual.getAmounts().size());

		List<Tuple2<MonthEndAmount, MonthEndAmount>> differences = Seq.ofType(expected.stream(), MonthEndAmount.class)
				.leftOuterJoin(actual.getAmounts().stream(), (a, b) -> a.getMonthEnd().isEqual(b.getMonthEnd()))
				.filter(tuple -> !tuple.v1.getAmount().equals(tuple.v2.getAmount()))
				.collect(Collectors.toList());

		if (differences.size() > 0) {
			System.out.println("testMonthEndAmountsAdd Differences");
			differences.forEach(d -> System.out.println(d.v1.getMonthEnd().toString() + " expected " + d.v1.getAmount() + " actual " + d.v2.getAmount()));
		}

		Assert.assertEquals("Expected 0 differences", 0, differences.size());
	}
}
