package com.excilys.formation.cdb.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.CompanyList;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.persistence.DAOCompany;

class CompanyServiceTests {
	
	@Test
	void FindAll() {
		List<Company> companies = CompanyService.findAll();
		assertEquals(false, companies.isEmpty());
		assertTrue(companies.containsAll(CompanyList.companies));
	}
	
	@Test
	void getNbCompanies() {
		int nbCompanies = DAOCompany.getNbCompanies();
		assertEquals(CompanyList.companies.size(), nbCompanies);
	}
	
	@Test
	void findAllPages() {
		Page<Company> page = new Page<Company>(1, 1);
		CompanyService.findCompanyPages(page);
		assertEquals(CompanyList.companies.size()-1, page.getEntities().size());
		List<Company> companiesTest = new ArrayList<Company>();
		for(int i = 1; i<CompanyList.companies.size();i++) {
			companiesTest.add(CompanyList.companies.get(i));
		}
		assertTrue(page.getEntities().containsAll(companiesTest));
	}
}
