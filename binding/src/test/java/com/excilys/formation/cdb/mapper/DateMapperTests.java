package com.excilys.formation.cdb.mapper;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.Test;

public class DateMapperTests {
	@Test
	@SuppressWarnings("deprecation")
	public void testEquality() {
		long now = System.currentTimeMillis();
		Date date = new Date(now);
		LocalDate localDate = Instant.ofEpochMilli(now).atZone(ZoneId.systemDefault()).toLocalDate();
		Date date2 = DateMapper.sqlDateFromLocalDate(localDate);
		assertEquals(date.getDay(), date2.getDay());
		assertEquals(date.getMonth(), DateMapper.sqlDateFromLocalDate(localDate).getMonth());
		assertEquals(date.getYear(), DateMapper.sqlDateFromLocalDate(localDate).getYear());
		LocalDate localDate2 = DateMapper.localDateFromSqlDate(date);
		assertEquals(localDate.getDayOfMonth(), localDate2.getDayOfMonth());
		assertEquals(localDate.getMonth(), localDate2.getMonth());
		assertEquals(localDate.getYear(), localDate2.getYear());
	}
}
