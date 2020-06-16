package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.cdb.model.Company;

public class CompanyMapper {
	public static Company mapSQLToCompany(ResultSet resultSet) throws SQLException {
		long id = resultSet.getLong("id");
		String name = resultSet.getString("name");
		return new Company(id, name);
	}
}
