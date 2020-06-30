package com.excilys.formation.cdb.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.excilys.formation.cdb.exception.NoResultException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Page;

class DAOCompanyTests {
	
	@Test
	void findAll() {
		List<Company> companies = DAOCompany.findAll();
		assertEquals(false, companies.isEmpty());
	}
	
	@Test
	void findById() {
		List<Company> companies = DAOCompany.findAll();
		try {
			Company company = DAOCompany.findById(companies.get(0).getId());
			assertEquals(companies.get(0), company);
		} catch (NoResultException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void getNbCompanies() {
		List<Company> companies = DAOCompany.findAll();
		int nbCompanies = DAOCompany.getNbCompanies();
		assertEquals(companies.size(), nbCompanies);
	}
	
	@Test
	void findAllPages() {
		List<Company> companies = DAOCompany.findAll();
		Page<Company> page = new Page<Company>(1, 1);
		DAOCompany.findAllPages(page);
		assertEquals(companies.size()-1, page.getEntities().size());
		List<Company> companiesTest = new ArrayList<Company>();
		for(int i = 1; i<companies.size();i++) {
			companiesTest.add(companies.get(i));
		}
		assertTrue(page.getEntities().containsAll(companiesTest));
	}
}
