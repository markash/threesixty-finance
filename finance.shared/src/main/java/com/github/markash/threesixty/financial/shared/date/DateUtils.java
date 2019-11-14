package com.github.markash.threesixty.financial.shared.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

    private DateUtils() {}
    
	public static java.util.Date convertToDate(
	        final LocalDate dateToConvert) {
	
		if (dateToConvert == null) {
			return null;
		}
		
		return java.util.Date.from(dateToConvert.atStartOfDay()
			      .atZone(ZoneId.systemDefault())
			      .toInstant());
	}
	
	public static LocalDate convertToLocalDateViaMilisecond(
			final Date dateToConvert) {
	    
		if (dateToConvert == null) {
			return null;
		}
		
		return Instant.ofEpochMilli(dateToConvert.getTime())
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
}
