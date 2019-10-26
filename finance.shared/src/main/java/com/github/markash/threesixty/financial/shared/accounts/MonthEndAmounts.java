package com.github.markash.threesixty.financial.shared.accounts;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.jooq.lambda.Seq;

public class MonthEndAmounts {

	private Set<MonthEndAmount> monthEnds = new HashSet<>();
	
	public MonthEndAmounts() { }
	
	public void add(
			final MonthEndAmount...amounts) {
		
		add(Arrays.asList(amounts));
	}
	
	public void add(
			final Collection<MonthEndAmount> amounts) {
		
		this.monthEnds = 
				Seq.ofType(this.monthEnds.stream(), MonthEndAmount.class)
				.append(amounts)
				.groupBy(monthEnd -> monthEnd.getMonthEnd())
				.entrySet()
				.stream()
				.map(entry -> {
					return new MonthEndAmount(
							entry.getKey(),
							entry.getValue().stream().map(m -> m.getAmount()).reduce(new BigDecimal("0"), (u, t) -> u.add(t))
							);
				})
				.collect(Collectors.toSet());
	}
	
	public Set<MonthEndAmount> getAmounts() {
		
		return Collections.unmodifiableSet(this.monthEnds);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MonthEndAmounts that = (MonthEndAmounts) o;
		return Objects.equals(monthEnds, that.monthEnds);
	}

	@Override
	public int hashCode() {
		return Objects.hash(monthEnds);
	}
}
