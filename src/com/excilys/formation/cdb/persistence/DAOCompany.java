package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.NoResultException;

/**
 * mySQL company table access manager
 * @author nbilly
 *
 */
public class DAOCompany {
	
	/**
	 * Select * from company;
	 * @return all companies
	 */
	public static List<Company> findAll() {
		List<Company> companies;
		Connection connection = CDBConnection.getConnection();
		companies = new ArrayList<Company>();
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from company;");
			while(resultSet.next()) {
				Company company = CompanyMapper.mapSQLToCompany(resultSet);
				companies.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companies;
	}

	/**
	 * Select * from company where id = X;
	 * @param companyId
	 * @return a company
	 * @throws NoResultException if request returns nothing
	 */
	public static Company findById(long companyId) throws NoResultException {
		Company company = null;
		Connection connection = CDBConnection.getConnection();
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from company where id = " + companyId + ";");
			if(resultSet.next()) {
				company = CompanyMapper.mapSQLToCompany(resultSet);
			}
			else {
				throw new NoResultException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}
}
