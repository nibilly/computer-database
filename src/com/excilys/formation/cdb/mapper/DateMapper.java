package com.excilys.formation.cdb.mapper;

import java.sql.Date;
import java.time.LocalDate;

public class DateMapper {
	public static Date localDateToSqlDate(LocalDate localDate) {
		Date date = null;
		if(localDate != null) {
			date = Date.valueOf(localDate);
		}
		return date;
	}
	public static LocalDate sqlDateToLocalDate(Date date) {
		LocalDate localDate = null;
		if(date != null) {
			localDate = date.toLocalDate();
		}
		return localDate;
	}
}
