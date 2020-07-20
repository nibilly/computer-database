package com.excilys.formation.cdb.persistence;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.excilys.formation.cdb.exception.NoResultException;
import com.excilys.formation.cdb.mapper.ComputerRowMapper;
import com.excilys.formation.cdb.mapper.DateMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.OrderBy;
import com.excilys.formation.cdb.model.Page;

/**
 * SQL computer table access manager
 * 
 * @author nbilly
 *
 */
public class DAOComputer {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private static final String COMPUTERS_LIMIT_LIKE_ORDER = "select computer.id, computer.name computer_name, computer.introduced, computer.discontinued, "
			+ "company.id company_id, company.name company_name"
			+ " from computer left join company on computer.company_id = company.id "
			+ "where computer.name like :computer_name or company.name like :company_name order by :order_by limit :jump,:return;";

	private static final String COMPUTERS_LIMIT_LIKE = "select computer.id, computer.name computer_name, computer.introduced, computer.discontinued, "
			+ "company.id company_id, company.name company_name"
			+ " from computer left join company on computer.company_id = company.id "
			+ "where computer.name like :computer_name or company.name like :company_name limit :jump,:return;";

	private static final String COMPUTERS_LIMIT_ORDER = "select computer.id, computer.name computer_name, computer.introduced, computer.discontinued, "
			+ "company.id company_id, company.name company_name"
			+ " from computer left join company on computer.company_id = company.id "
			+ "order by :order_by limit :jump,:return;";

	private static final String COMPUTERS_LIMIT = "select computer.id, computer.name computer_name, computer.introduced, computer.discontinued, "
			+ "company.id company_id, company.name company_name"
			+ " from computer left join company on computer.company_id = company.id " + "limit :jump,:return;";

	private static final String COMPUTERS_LIKE = "select count(computer.id)"
			+ " from computer left join company on computer.company_id = company.id "
			+ "where computer.name like :computer_name or company.name like :company_name;";

	private static final String COMPUTERS = "select count(computer.id)"
			+ " from computer left join company on computer.company_id = company.id;";

	/**
	 * look for all computers
	 * 
	 * @return computers
	 */
	public List<Computer> findAll() {
		List<Computer> computers = null;
		String request = "select computer.id, computer.name computer_name, computer.introduced, computer.discontinued, "
				+ "company.id company_id, company.name company_name from computer join company on computer.company_id = company.id;";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		computers = jdbcTemplate.query(request, new ComputerRowMapper());
		return computers;
	}

	/**
	 * select count(id) from computer;
	 * 
	 * @return number of computers
	 */
	public int getNbComputers() {
		int nbComputers = 0;
		String request = "select count(id) from computer;";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		nbComputers = jdbcTemplate.queryForObject(request, Integer.class);
		return nbComputers;
	}

	/**
	 * look for computers with pagination
	 * 
	 * @param page the page have number of rows jumped and number of rows returned.
	 *             Also the list of computers is returned by page.entities.
	 */
	public void findComputersPages(Page<Computer> page) {
		List<Computer> computers = null;
		String request = "select computer.id, computer.name computer_name, computer.introduced, computer.discontinued, "
				+ "company.id company_id, company.name company_name from computer left join company on computer.company_id = company.id limit :jump,:return;";
		NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("jump", page.getNbRowsJumped());
		parameterSource.addValue("return", Page.getNbRowsReturned());
		computers = parameterJdbcTemplate.query(request, parameterSource, new ComputerRowMapper());
		page.setEntities(computers);
		page.setNbComputerFound(getNbComputers());
	}

	/**
	 * look for one computer
	 * 
	 * @param computerId
	 * @return a computer
	 * @throws NoResultException
	 */
	public Computer findById(long computerId) throws NoResultException {
		Computer computer = null;
		String request = "select computer.id, computer.name computer_name, computer.introduced, computer.discontinued, "
				+ "company.id company_id, company.name company_name from computer left join company on computer.company_id = company.id where computer.id = :id;";
		NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("id", computerId);
		try {
			computer = parameterJdbcTemplate.queryForObject(request, parameterSource, new ComputerRowMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new NoResultException();
		}
		return computer;
	}

	public void createComputer(Computer computer) {
		String request = "insert into computer(name, introduced, discontinued, company_id) values(:name, :introduced, :discontinued, :company_id);";
		NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("name", computer.getName());
		parameterSource.addValue("introduced", DateMapper.sqlDateFromLocalDate(computer.getIntroduced()));
		parameterSource.addValue("discontinued", DateMapper.sqlDateFromLocalDate(computer.getDiscontinued()));
		if (computer.getCompany() == null) {
			parameterSource.addValue("company_id", null);
		} else {
			parameterSource.addValue("company_id", computer.getCompany().getId());
		}
		parameterJdbcTemplate.update(request, parameterSource);
	}

	public void updateComputer(Computer computer) {
		String request = "update computer set name=:name, introduced=:introduced, discontinued=:discontinued, company_id=:company_id where id=:id;";
		NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("name", computer.getName());
		parameterSource.addValue("introduced", DateMapper.sqlDateFromLocalDate(computer.getIntroduced()));
		parameterSource.addValue("discontinued", DateMapper.sqlDateFromLocalDate(computer.getDiscontinued()));
		if (computer.getCompany() == null) {
			parameterSource.addValue("company_id", null);
		} else {
			parameterSource.addValue("company_id", computer.getCompany().getId());
		}
		parameterSource.addValue("id", computer.getId());
		parameterJdbcTemplate.update(request, parameterSource);
	}

	public void deleteComputerById(long computerId) {
		String request = "delete from computer where id=:id;";
		NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("id", computerId);
		parameterJdbcTemplate.update(request, parameterSource);
	}

	public void findComputersPageSearchOrderBy(Page<Computer> page) {
		String request = null;
		if (page.getSearch() != null && !"".equals(page.getSearch())) {
			if (page.getOrderBy() != null) {
				request = COMPUTERS_LIMIT_LIKE_ORDER;

			} else {
				request = COMPUTERS_LIMIT_LIKE;
			}
		} else {
			if (page.getOrderBy() != null) {
				request = COMPUTERS_LIMIT_ORDER;
			} else {
				request = COMPUTERS_LIMIT;
			}
		}
		List<Computer> computers = null;
		NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		fulfillPreparedStatement(parameterSource, page);
		computers = parameterJdbcTemplate.query(request, parameterSource, new ComputerRowMapper());
		page.setEntities(computers);
		page.setNbComputerFound(nbComputersLike(page));
	}

	private int nbComputersLike(Page<Computer> page) {
		String request = null;
		if (page.getSearch() != null && !"".equals(page.getSearch())) {
			request = COMPUTERS_LIKE;
		} else {
			request = COMPUTERS;
		}
		NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		if (page.getSearch() != null && !"".equals(page.getSearch())) {
			parameterSource.addValue("computer_name", '%' + page.getSearch() + '%');
			parameterSource.addValue("company_name", '%' + page.getSearch() + '%');
		}
		return parameterJdbcTemplate.queryForObject(request, parameterSource, Integer.class);
	}

	private void fulfillPreparedStatement(MapSqlParameterSource parameterSource, Page<Computer> page) {
		parameterSource.addValue("jump", page.getNbRowsJumped());
		parameterSource.addValue("return", Page.getNbRowsReturned());
		if (page.getSearch() != null && !"".equals(page.getSearch())) {
			parameterSource.addValue("computer_name", '%' + page.getSearch() + '%');
			parameterSource.addValue("company_name", '%' + page.getSearch() + '%');
		}
		if (page.getOrderBy() != null) {
			parameterSource.addValue("order_by", orderMatch(page.getOrderBy()));
		}
	}

	private String orderMatch(OrderBy orderBy) {
		switch (orderBy) {
		case COMPANY_NAME:
			return "company_name";
		case COMPUTER_NAME:
			return "computer_name";
		case DISCONTINUED:
			return "discontinued";
		case INTRODUCED:
			return "introduced";
		default:
			return null;
		}
	}
}
