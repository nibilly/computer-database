package com.excilys.formation.cdb.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ComputerList {
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static List<Computer> computers = new ArrayList<Computer>() {
		{
			add(new Computer(1, "MacBook Pro 15.4 inch", null, null, 1, "Apple Inc."));
			add(new Computer(2, "CM-2a", null, null, 2, "Thinking Machines"));
			add(new Computer(3, "CM-200", null, null, 2, "Thinking Machines"));
			add(new Computer(4, "CM-5e", null, null, 2, "Thinking Machines"));
			add(new Computer(5, "CM-5", LocalDate.parse("1991-01-01", dateTimeFormatter) , LocalDate.parse("2000-01-01", dateTimeFormatter), 2, "Thinking Machines"));
		}
	};
}
