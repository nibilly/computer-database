package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cdb.model.Company;

public class DAOCompany {
	private static List<Company> companies = null;
	
	public static List<Company> findAll() {
		Connection connection = CDBConnection.getConnection();
		companies = new ArrayList<Company>();
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from company;");
			while(resultSet.next()) {
				long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				Company company = new Company(id, name);
				companies.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companies;
	}

	public static Company findById(long companyId) {
		Company company = null;
		if(companies!=null)
		{
			company = companies.stream().filter(c->c.getId() == companyId).findAny().orElse(null);
		}
		if (company==null) {
			Connection connection = CDBConnection.getConnection();
			companies = new ArrayList<Company>();
			Statement statement;
			try {
				statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("select * from company where id = " + companyId + ";");
				while(resultSet.next()) {
					long id = resultSet.getLong("id");
					String name = resultSet.getString("name");
					company = new Company(id, name);
					companies.add(company);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return company;
	}
}
