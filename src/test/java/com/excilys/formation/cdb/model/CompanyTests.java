package com.excilys.formation.cdb.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CompanyTests {
	@Test
	void constructorFull() {
		Company company = new Company(1, "name");
		assertEquals(1, company.getId());
		assertEquals("name", company.getName());
	}
	
	@Test
	void setters() {
		Company company = new Company();
		company.setId(1);
		company.setName("name");
		assertEquals(1, company.getId());
		assertEquals("name", company.getName());
	}
}
