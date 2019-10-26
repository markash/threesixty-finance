package com.github.markash.threesixty.financial.database.date;

import java.io.Serializable;
import java.time.LocalDate;

public class LogicalDate implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Current date
	 */
	private LocalDate current;
	/**
	 * Month end date
	 */
	private LocalDate monthEnd;
	
	/**
	 * The current date
	 * @return The current date
	 */
	public LocalDate getCurrent() {
		return current;
	}
	public void setCurrent(LocalDate current) {
		this.current = current;
	}
	
	/**
	 * The month-end for the current date
	 * @return Month end
	 */
	public LocalDate getMonthEnd() {
		return monthEnd;
	}
	public void setMonthEnd(LocalDate monthEnd) {
		this.monthEnd = monthEnd;
	}
	
	
}
