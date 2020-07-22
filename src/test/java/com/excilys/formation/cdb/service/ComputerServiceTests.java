package com.excilys.formation.cdb.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.persistence.CDBConnection;
import com.zaxxer.hikari.HikariDataSource;

public class ComputerServiceTests extends DBTestCase {
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;

	private ComputerService computerService;

	private CDBConnection cdbConnection;

	public ComputerServiceTests(String name) {
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
		cdbConnection = (CDBConnection) ContextFactory.getApplicationContext().getBean("cdbConnection");
		computerService = (ComputerService) ContextFactory.getApplicationContext().getBean("computerService");
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
	public void testGetNbComputers() {
		int nbComputers = computerService.getNbComputers();
		assertEquals(4, nbComputers);
	}

	@Test
	public void testFindComputersPages() {
		Page<Computer> page = new Page<Computer>(1, 1);
		computerService.findComputersPages(page);
		assertEquals(3, page.getEntities().size());
		Computer computer = page.getEntities().get(0);
		assertEquals(2, computer.getId());
		assertEquals("CM-2a", computer.getName());
		assertEquals("1980-02-01", computer.getIntroduced().toString());
		assertNull(computer.getDiscontinued());
		assertEquals(2, computer.getCompany().getId());
		assertEquals("Thinking Machines", computer.getCompany().getName());
	}

	@Test
	public void testFindById() {
		try {
			Computer computer = computerService.findById(2);
			assertEquals("CM-2a", computer.getName());
		} catch (NoResultException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateComputer() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;
		Computer computer = new Computer("name", LocalDate.parse("1980-05-04", dateTimeFormatter),
				LocalDate.parse("2000-12-31", dateTimeFormatter), new Company(2, "Thinking Machines"));
		computerService.createComputer(computer);
		String query = "SELECT computer.id, computer.name computer_name, computer.introduced, computer.discontinued,"
				+ " company.id company_id, company.name company_name from computer left join company on computer.company_id = company.id ORDER BY computer.id DESC LIMIT 1";
		try (Connection connection = cdbConnection.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {
			if (resultSet.next()) {
				Computer computer1 = ComputerMapper.mapSQLToComputer(resultSet);
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
			Computer computer = computerService.findById(3);
			computer.setIntroduced(LocalDate.parse("1997-01-01", dateTimeFormatter));
			computerService.updateComputer(computer);
			Computer computer1 = computerService.findById(3);
			assertEquals(computer, computer1);
		} catch (NoResultException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteComputerById() {
		boolean isSuppresed = false;
		computerService.deleteComputerById(4);
		try {
			computerService.findById(4);
		} catch (NoResultException e) {
			isSuppresed = true;
		}
		assertEquals(true, isSuppresed);
	}
}
