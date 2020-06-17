package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.cdb.model.Company;
/**
 * Map company from different types
 * @author nbilly
 *
 */
public class CompanyMapper {
	/**
	 * take a SQL result and return the company correspondent
	 * @param resultSet SQL request result
	 * @return a company
	 * @throws SQLException
	 */
	public static Company mapSQLToCompany(ResultSet resultSet) throws SQLException {
		long id = resultSet.getLong("id");
		String name = resultSet.getString("name");
		return new Company(id, name);
	}
}
