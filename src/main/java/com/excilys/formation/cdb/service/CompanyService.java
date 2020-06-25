package com.excilys.formation.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.persistence.DAOCompany;

/**
 * DAO access
 * @author nbilly
 *
 */
public class CompanyService {
	private static Logger logger = LoggerFactory.getLogger(CompanyService.class);

	public static List<Company> findAll() {
		logger.info("findAll");
		return DAOCompany.findAll();
	}
	
	public static int getNbCompanies() {
		logger.info("getNbCompanies");
		return DAOCompany.getNbCompanies();
	}

	public static void findCompanyPages(Page<Company> page) {
		logger.info("findCompanyPages");
		DAOCompany.findAllPages(page);
	}
}
