package com.excilys.formation.cdb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.persistence.DAOCompany;

class CompanyServiceTests {

	@Test
	void FindAll() {
		List<Company> companies = CompanyService.findAll();
		assertEquals(false, companies.isEmpty());
	}

	@Test
	void getNbCompanies() {
		List<Company> companies = CompanyService.findAll();
		int nbCompanies = DAOCompany.getNbCompanies();
		assertEquals(companies.size(), nbCompanies);
	}

	@Test
	void findAllPages() {
		List<Company> companies = CompanyService.findAll();
		Page<Company> page = new Page<Company>(1, 1);
		CompanyService.findCompanyPages(page);
		assertEquals(companies.size() - 1, page.getEntities().size());
		List<Company> companiesTest = new ArrayList<Company>();
		for (int i = 1; i < companies.size(); i++) {
			companiesTest.add(companies.get(i));
		}
		assertTrue(page.getEntities().containsAll(companiesTest));
	}
}
