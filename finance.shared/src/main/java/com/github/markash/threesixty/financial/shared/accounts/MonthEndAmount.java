package com.github.markash.threesixty.financial.shared.accounts;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class MonthEndAmount implements Serializable, Comparable<MonthEndAmount> {
	private static final long serialVersionUID = 1L;
	
	private LocalDate monthEnd;
	private BigDecimal amount;
	
	public MonthEndAmount(
			final LocalDate monthEnd,
			final BigDecimal amount) {
		
		this.monthEnd = monthEnd;
		this.amount = BigDecimal.valueOf(amount != null ? amount.doubleValue() : 0.00);
	}
	
	public MonthEndAmount(
			final LocalDate monthEnd,
			final Long amount) {
		
		this.monthEnd = monthEnd;
		this.amount = BigDecimal.valueOf(amount != null ? amount.doubleValue() : 0.00);
	}
	
	public MonthEndAmount(
			final java.sql.Date monthEnd,
			final BigDecimal amount) {
		
		this.monthEnd = monthEnd.toLocalDate();
		this.amount = BigDecimal.valueOf(amount != null ? amount.doubleValue() : 0.00);
	}
	
	/**
	 * Month-end for the summary
	 * @return Month-end
	 */
	public LocalDate getMonthEnd() {
		return monthEnd;
	}
	/**
	 * Summary amount
	 * @return Amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	public MonthEndAmount add(
			final BigDecimal amount) {
	
		return new MonthEndAmount(this.monthEnd, this.amount.add(amount));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(amount, monthEnd);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonthEndAmount other = (MonthEndAmount) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(monthEnd, other.monthEnd);
	}

	@Override
	public int compareTo(
			final MonthEndAmount o) {
		
		LocalDate other = o != null ? o.getMonthEnd() : null;
		
		if (this.monthEnd == null && other == null) {
			return 0;
		}
		
		return this.monthEnd != null ? this.monthEnd.compareTo(other) : -1; 
	}
	
	public static class MonthEndDescending implements Comparator<MonthEndAmount> {

		@Override
		public int compare(
				final MonthEndAmount o1, 
				final MonthEndAmount o2) {
			
			if (o1 == null && o2 == null) {
				return 0;
			}
			
			if (o1 != null) {
				return o1.compareTo(o2) * -1;
			}
			
			if (o2 != null) {
				return o2.compareTo(o1);
			}
			
			return 0;
		}
		
	}
}
