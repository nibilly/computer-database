package com.excilys.formation.cdb.persistence;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.excilys.formation.cdb.exception.NoResultException;
import com.excilys.formation.cdb.mapper.CompanyRowMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Page;

/**
 * mySQL company table access manager
 * 
 * @author nbilly
 *
 */
public class DAOCompany {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Select * from company;
	 * 
	 * @return all companies
	 */
	public List<Company> findAll() {
		List<Company> companies = null;
		String query = "Select id, name from company;";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		companies = jdbcTemplate.query(query, new CompanyRowMapper());
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
		String query = "select id, name from company where id = :id;";
		NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("id", companyId);
		try {
			company = parameterJdbcTemplate.queryForObject(query, parameterSource, new CompanyRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new NoResultException();
		}
		return company;
	}

	public int getNbCompanies() {
		int nbCompanies = 0;
		String query = "select count(id) from company;";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		nbCompanies = jdbcTemplate.queryForObject(query, Integer.class);
		return nbCompanies;
	}

	public void findAllPages(Page<Company> page) {
		List<Company> companies = null;
		String query = "select * from company limit :jump,:return;";
		NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("jump", page.getNbRowsJumped());
		parameterSource.addValue("return", Page.getNbRowsReturned());
		companies = parameterJdbcTemplate.query(query, parameterSource, new CompanyRowMapper());
		page.setEntities(companies);
	}

	public void delete(long companyId) {
		String computerRequest = "delete from computer where company_id=:company_id;";
		String companyRequest = "delete from company where id=:id;";
		NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("company_id", companyId);
		parameterJdbcTemplate.update(computerRequest, parameterSource);
		parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("id", companyId);
		parameterJdbcTemplate.update(companyRequest, parameterSource);
	}
}
