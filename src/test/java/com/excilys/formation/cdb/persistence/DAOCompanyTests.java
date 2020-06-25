package com.excilys.formation.cdb.persistence;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.CompanyList;
import com.excilys.formation.cdb.model.NoResultException;
import com.excilys.formation.cdb.model.Page;

class DAOCompanyTests {
	@Test
	void FindAll() {
		List<Company> companies = DAOCompany.findAll();
		assertEquals(false, companies.isEmpty());
		assertTrue(companies.containsAll(CompanyList.companies));
	}
	
	@Test
	void findById() {
		try {
			Company company = DAOCompany.findById(CompanyList.companies.get(0).getId());
			assertEquals(CompanyList.companies.get(0), company);
		} catch (NoResultException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void getNbCompanies() {
		int nbCompanies = DAOCompany.getNbCompanies();
		assertEquals(CompanyList.companies.size(), nbCompanies);
	}
	
	@Test
	void findAllPages() {
		Page<Company> page = new Page<Company>(1, 1);
		DAOCompany.findAllPages(page);
		assertEquals(CompanyList.companies.size()-1, page.getEntities().size());
		List<Company> companiesTest = new ArrayList<Company>();
		for(int i = 1; i<CompanyList.companies.size();i++) {
			companiesTest.add(CompanyList.companies.get(i));
		}
		assertTrue(page.getEntities().containsAll(companiesTest));
	}
}
