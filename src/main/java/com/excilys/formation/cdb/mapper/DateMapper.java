package com.excilys.formation.cdb.mapper;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateMapper {
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;

	public static Date sqlDateFromLocalDate(LocalDate localDate) {
		Date date = null;
		if (localDate != null) {
			date = Date.valueOf(localDate);
		}
		return date;
	}

	public static LocalDate localDateFromSqlDate(Date date) {
		LocalDate localDate = null;
		if (date != null) {
			localDate = date.toLocalDate();
		}
		return localDate;
	}

	public static LocalDate localDateFromString(String date) throws DateTimeParseException {
		LocalDate localDate = null;
		if (date != null) {
			localDate = LocalDate.parse(date, dateTimeFormatter);
		}
		return localDate;
	}
}