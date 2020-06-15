package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cdb.model.Computer;

public class DAOComputer {
	
	public static List<Computer> findAll() {
		Connection connection = CDBConnection.getConnection();
		List<Computer> companies = new ArrayList<Computer>();
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from computer;");
			while(resultSet.next()) {
				long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				Date introduced = resultSet.getDate("introduced");
				Date discontinued = resultSet.getDate("discontinued");
				long companyId = resultSet.getLong("company_id");
				Computer company = new Computer(id, name, introduced, discontinued, companyId);
				companies.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companies;
	}
}
