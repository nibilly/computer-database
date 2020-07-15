package com.excilys.formation.cdb.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CompanyTests {
	@Test
	public void constructorFull() {
		Company company = new Company(1, "name");
		assertEquals(1, company.getId());
		assertEquals("name", company.getName());
	}

	@Test
	public void setters() {
		Company company = new Company();
		company.setId(1);
		company.setName("name");
		assertEquals(1, company.getId());
		assertEquals("name", company.getName());
	}
}
