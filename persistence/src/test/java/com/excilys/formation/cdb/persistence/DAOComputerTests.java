package com.excilys.formation.cdb.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.cdb.config.ContextFactory;
import com.excilys.formation.cdb.exception.NoResultException;
import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.OrderBy;
import com.excilys.formation.cdb.model.Page;
import com.zaxxer.hikari.HikariDataSource;

public class DAOComputerTests extends DBTestCase {

	private DAOComputer daoComputer;

	private CDBConnection cdbConnection;

	public DAOComputerTests(String name) {
		super(name);

		try {
			InputStream inputStream = new FileInputStream("src/test/resources/local.properties");
			Properties properties = new Properties();
			properties.load(inputStream);

			String url = properties.getProperty("db.url");
			String username = properties.getProperty("db.username");
			String password = properties.getProperty("db.password");
			String driver = properties.getProperty("db.driver");

			System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, driver);
			System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, url);
			System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, username);
			System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, password);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		daoComputer = (DAOComputer) ContextFactory.getApplicationContext().getBean("daoComputer");
		cdbConnection = (CDBConnection) ContextFactory.getApplicationContext().getBean("cdbConnection");
	}

	@Before
	public void setUp() throws Exception {
		try (Connection connection = (Connection) ((HikariDataSource) ContextFactory.getApplicationContext()
				.getBean("dataSource")).getConnection()) {
			DatabaseConnection dbConnection = new DatabaseConnection(connection);
			getSetUpOperation().execute(dbConnection, getDataSet());
		}
	}

	@Override
	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.CLEAN_INSERT;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/dataset.xml"));
	}

	@Test
	public void testFindAll() {
		List<Computer> computers = daoComputer.findAll();
		assertFalse(computers.isEmpty());
	}

	@Test
	public void testGetNbComputers() {
		int nb = daoComputer.getNbComputers();
		assertEquals(4, nb);
	}

	@Test
	public void testFindComputersPages() {
		Page<Computer> page = new Page<Computer>(1, 1);
		daoComputer.findComputersPages(page);
		assertEquals(3, page.getEntities().size());
	}

	@Test
	public void testFindById() {
		try {
			Computer computer = daoComputer.findById(1);
			assertEquals(1, computer.getId());
			assertEquals("un", computer.getName());
			assertEquals("1999-01-01", computer.getIntroduced().toString());
			assertEquals("2000-12-31", computer.getDiscontinued().toString());
			assertEquals(1, computer.getCompany().getId());
			assertEquals("Apple Inc.", computer.getCompany().getName());
		} catch (NoResultException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFindByIdNoResult() {
		boolean noResult = false;
		try {
			daoComputer.findById(80);
		} catch (NoResultException e) {
			noResult = true;
		}
		assertTrue(noResult);
	}

	@Test
	public void testCreateComputer() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;
		Computer computer = new Computer("name", LocalDate.parse("1999-01-01", dateTimeFormatter),
				LocalDate.parse("1999-01-02", dateTimeFormatter), new Company(2, "Thinking Machines"));
		daoComputer.createComputer(computer);
		String query = "SELECT computer.id, computer.name computer_name, computer.introduced, computer.discontinued,"
				+ " company.id company_id, company.name company_name from computer left join company on computer.company_id = company.id ORDER BY computer.id DESC LIMIT 1";
		try (Connection connection = cdbConnection.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {
			if (resultSet.next()) {
				Computer computer1 = ComputerMapper.mapSQLToComputer(resultSet);
				assertTrue(computer1.getId() > 4);
				computer.setId(computer1.getId());
				assertEquals(computer, computer1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateComputer() {
		try {
			Computer computer = daoComputer.findById(1);
			computer.setDiscontinued(LocalDate.parse("2020-12-12"));
			daoComputer.updateComputer(computer);
			Computer computer1 = daoComputer.findById(1);
			assertEquals(computer, computer1);
		} catch (NoResultException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteComputerById() {
		boolean isSuppresed = false;
		daoComputer.deleteComputerById(1);
		try {
			daoComputer.findById(1);
		} catch (NoResultException e) {
			isSuppresed = true;
		}
		assertEquals(true, isSuppresed);
	}

	@Test
	public void testFindComputersPageSearchOrderBy() {
		Page<Computer> page = new Page<Computer>(1, 0);
		daoComputer.findComputersPageSearchOrderBy(page);
		assertEquals(4, page.getEntities().size());
	}

	@Test
	public void testFindComputersPageOrderBy() {
		Page<Computer> page = new Page<Computer>(1, 1);
		page.setOrderBy(OrderBy.COMPUTER_NAME);
		daoComputer.findComputersPageSearchOrderBy(page);
		assertEquals(3, page.getEntities().size());
	}

	@Test
	public void testFindComputersPageSearch() {
		Page<Computer> page = new Page<Computer>(1, 0);
		page.setSearch("un");
		daoComputer.findComputersPageSearchOrderBy(page);
		assertEquals(1, page.getEntities().size());
		return;
	}

	@Test
	public void testFindComputersPageFull() {
		Page<Computer> page = new Page<Computer>(1, 0);
		page.setOrderBy(OrderBy.COMPANY_NAME);
		page.setSearch("a");
		daoComputer.findComputersPageSearchOrderBy(page);
		assertEquals(3, page.getEntities().size());
	}
}
