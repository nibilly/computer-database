package com.excilys.formation.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.exception.NoResultException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.persistence.DAOCompany;

/**
 * DAO access
 * 
 * @author nbilly
 *
 */
public class CompanyService {
	private static Logger logger = LoggerFactory.getLogger(CompanyService.class);

	private DAOCompany daoCompany;

	public void setDaoCompany(DAOCompany daoCompany) {
		this.daoCompany = daoCompany;
	}

	public List<Company> findAll() {
		logger.info("findAll");
		return daoCompany.findAll();
	}

	public int getNbCompanies() {
		logger.info("getNbCompanies");
		return daoCompany.getNbCompanies();
	}

	public void findCompanyPages(Page<Company> page) {
		logger.info("findCompanyPages");
		daoCompany.findAllPages(page);
	}

	public Company findById(long companyId) {
		logger.info("findById");
		Company company;
		try {
			company = daoCompany.findById(companyId);
		} catch (NoResultException e) {
			company = null;
		}
		return company;
	}

	public void delete(long companyId) {
		logger.info("delete");
		daoCompany.delete(companyId);
	}
}
