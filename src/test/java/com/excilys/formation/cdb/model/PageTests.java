package com.excilys.formation.cdb.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class PageTests<T> {
	
	private final List<Computer> computers = new ArrayList<Computer>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add(new Computer(1, "name", LocalDate.now(), LocalDate.now().plusDays(1), 1, "companyName"));
			add(new Computer(2, "name2", null, LocalDate.now().plusDays(1), 2, "companyName2"));
		}
	};
	
	private final List<Company> companies = new ArrayList<Company>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add(new Company(1, "companyName"));
			add(new Company(2, "companyName1"));
		}
	};
	
	@Test
	void constructorComputerFull() {
		Page<Computer> page = new Page<Computer>(1, 10);
		page.setEntities(computers);
		assertEquals(1, page.getPageNumber());
		assertEquals(10, page.getNbRowsJumped());
		assertEquals(computers.get(0), page.getEntities().get(0));
		assertEquals(computers.get(1), page.getEntities().get(1));
	}
	
	@Test
	void settersComputer() {
		Page<Computer> page = new Page<Computer>();
		page.setNbRowsJumped(10);
		page.setPageNumber(1);
		page.setEntities(computers);
		assertEquals(1, page.getPageNumber());
		assertEquals(10, page.getNbRowsJumped());
		assertEquals(computers.get(0), page.getEntities().get(0));
		assertEquals(computers.get(1), page.getEntities().get(1));
	}
	
	@Test
	void constructorCompanyFull() {
		Page<Company> page = new Page<Company>(1, 10);
		page.setEntities(companies);
		assertEquals(1, page.getPageNumber());
		assertEquals(10, page.getNbRowsJumped());
		assertEquals(companies.get(0), page.getEntities().get(0));
		assertEquals(companies.get(1), page.getEntities().get(1));
	}
	
	@Test
	void settersCompany() {
		Page<Company> page = new Page<Company>();
		page.setNbRowsJumped(10);
		page.setPageNumber(1);
		page.setEntities(companies);
		assertEquals(1, page.getPageNumber());
		assertEquals(10, page.getNbRowsJumped());
		assertEquals(companies.get(0), page.getEntities().get(0));
		assertEquals(companies.get(1), page.getEntities().get(1));
	}
}
