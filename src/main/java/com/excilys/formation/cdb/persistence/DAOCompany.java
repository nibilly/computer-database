package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cdb.exception.NoResultException;
import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Page;

/**
 * mySQL company table access manager
 * 
 * @author nbilly
 *
 */
public class DAOCompany {

	private CDBConnection cdbConnection;

	public void setCdbConnection(CDBConnection cdbConnection) {
		this.cdbConnection = cdbConnection;
	}

	/**
	 * Select * from company;
	 * 
	 * @return all companies
	 */
	public List<Company> findAll() {
		List<Company> companies = new ArrayList<Company>();
		String query = "Select id, name from company;";
		try (Connection connection = cdbConnection.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {
			while (resultSet.next()) {
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
	 * 
	 * @param companyId
	 * @return a company
	 * @throws NoResultException if request returns nothing
	 */
	public Company findById(long companyId) throws NoResultException {
		Company company = null;
		String query = "select * from company where id = ?;";
		try (Connection connection = cdbConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setLong(1, companyId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					company = CompanyMapper.mapSQLToCompany(resultSet);
				} else {
					throw new NoResultException();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}

	public int getNbCompanies() {
		int nbCompanies = 0;
		String query = "select count(id) from company;";
		try (Connection connection = cdbConnection.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {
			if (resultSet.next()) {
				nbCompanies = resultSet.getInt("count(id)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbCompanies;
	}

	public void findAllPages(Page<Company> page) {
		List<Company> companies = new ArrayList<Company>();
		String query = "select * from company limit ?,?;";
		try (Connection connection = cdbConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, page.getNbRowsJumped());
			preparedStatement.setInt(2, Page.getNbRowsReturned());
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Company company = CompanyMapper.mapSQLToCompany(resultSet);
					companies.add(company);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		page.setEntities(companies);
	}

	public void delete(long companyId) {
		String computerRequest = "delete from computer where company_id=?;";
		String companyRequest = "delete from company where id=?;";
		try (Connection connection = cdbConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(computerRequest)) {
			preparedStatement.setLong(1, companyId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (Connection connection = cdbConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(companyRequest)) {
			preparedStatement.setLong(1, companyId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
