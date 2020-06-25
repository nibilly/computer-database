package com.excilys.formation.cdb.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ComputerTests {

	@Test
	void constructorFull() {
		LocalDate localDate = LocalDate.now();
		LocalDate localDate1 = LocalDate.now().plusDays(1);
		Computer computer = new Computer(1, "name",localDate, localDate1, 1, "companyName");
		assertEquals(1, computer.getId());
		assertEquals("name", computer.getName());
		assertEquals(localDate, computer.getIntroduced());
		assertEquals(localDate1, computer.getDiscontinued());
		assertEquals(1, computer.getCompany().getId());
		assertEquals("companyName", computer.getCompany().getName());
	}
	
	@Test
	void setters() {
		LocalDate localDate = LocalDate.now();
		LocalDate localDate1 = LocalDate.now().plusDays(1);
		Computer computer = new Computer();
		computer.setId(1);
		computer.setName("name");
		computer.setIntroduced(localDate);
		computer.setDiscontinued(localDate1);
		computer.setCompany(new Company(1, "companyName"));
		assertEquals(1, computer.getId());
		assertEquals("name", computer.getName());
		assertEquals(localDate, computer.getIntroduced());
		assertEquals(localDate1, computer.getDiscontinued());
		assertEquals(1, computer.getCompany().getId());
		assertEquals("companyName", computer.getCompany().getName());
	}

}