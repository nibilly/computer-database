package com.excilys.formation.cdb.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PageTests {

	private final List<Computer> computers = new ArrayList<Computer>() {
		private static final long serialVersionUID = 2L;

		{
			add(new Computer(1, "name", LocalDate.now(), LocalDate.now().plusDays(1), new Company(1, "companyName")));
			add(new Computer(2, "name2", null, LocalDate.now().plusDays(1), new Company(2, "companyName2")));
		}
	};

	private final List<Company> companies = new ArrayList<Company>() {
		private static final long serialVersionUID = 2L;

		{
			add(new Company(1, "companyName"));
			add(new Company(2, "companyName1"));
		}
	};

	@Test
	public void testConstructorComputerFull() {
		Page<Computer> page = new Page<Computer>(1, 10);
		page.setEntities(computers);
		assertEquals(1, page.getPageIndex());
		assertEquals(10, page.getNbRowsJumped());
		assertEquals(computers.get(0), page.getEntities().get(0));
		assertEquals(computers.get(1), page.getEntities().get(1));
	}

	@Test
	public void testSettersComputer() {
		Page<Computer> page = new Page<Computer>();
		page.setNbRowsJumped(10);
		page.setPageIndex(1);
		page.setEntities(computers);
		assertEquals(1, page.getPageIndex());
		assertEquals(10, page.getNbRowsJumped());
		assertEquals(computers.get(0), page.getEntities().get(0));
		assertEquals(computers.get(1), page.getEntities().get(1));
	}

	@Test
	public void testConstructorCompanyFull() {
		Page<Company> page = new Page<Company>(1, 10);
		page.setEntities(companies);
		assertEquals(1, page.getPageIndex());
		assertEquals(10, page.getNbRowsJumped());
		assertEquals(companies.get(0), page.getEntities().get(0));
		assertEquals(companies.get(1), page.getEntities().get(1));
	}

	@Test
	public void testSettersCompany() {
		Page<Company> page = new Page<Company>();
		page.setNbRowsJumped(10);
		page.setPageIndex(1);
		page.setEntities(companies);
		assertEquals(1, page.getPageIndex());
		assertEquals(10, page.getNbRowsJumped());
		assertEquals(companies.get(0), page.getEntities().get(0));
		assertEquals(companies.get(1), page.getEntities().get(1));
	}

	@Test
	public void testSearch() {
		Page<Computer> page = new Page<Computer>();
		page.setSearch("computer");
		assertEquals("computer", page.getSearch());
	}

	@Test
	public void testOrderBy() {
		Page<Computer> page = new Page<Computer>();
		page.setOrderBy(OrderBy.COMPANY_NAME);
		assertEquals(OrderBy.COMPANY_NAME, page.getOrderBy());
		page = new Page<Computer>();
		page.setOrderBy(OrderBy.COMPUTER_NAME);
		assertEquals(OrderBy.COMPUTER_NAME, page.getOrderBy());
		page = new Page<Computer>();
		page.setOrderBy(OrderBy.DISCONTINUED);
		assertEquals(OrderBy.DISCONTINUED, page.getOrderBy());
		page = new Page<Computer>();
		page.setOrderBy(OrderBy.INTRODUCED);
		assertEquals(OrderBy.INTRODUCED, page.getOrderBy());
	}
}
