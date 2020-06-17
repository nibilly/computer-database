package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.persistence.DAOCompany;

/**
 * DAO access
 * @author nbilly
 *
 */
public class CompanyService {

	public static List<Company> findAll() {
		return DAOCompany.findAll();
	}
	
	public static int getNbCompanies() {
		return DAOCompany.getNbCompanies();
	}

	public static void findCompanyPages(Page<Company> page) {
		DAOCompany.findAllPages(page);
	}
}
