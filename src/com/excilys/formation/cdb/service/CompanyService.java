package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CDBConnection;
import com.excilys.formation.cdb.persistence.DAOCompany;

public class CompanyService {

	public CompanyService() {
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		CDBConnection.CloseConnection();
	}

	public List<Company> findAll() {
		return DAOCompany.findAll();
	}
}
