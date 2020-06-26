package com.excilys.formation.cdb.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.model.Computer;

class ComputerMapperTests {
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void mapSQLTocomputer() {
		ResultSet resultSet = Mockito.mock(ResultSet.class);
		Computer computerTest = new Computer(2, "name", LocalDate.parse("1998-01-01", dateTimeFormatter),
				LocalDate.parse("2003-01-01", dateTimeFormatter), 2, "CompanyName");

		Computer computer = null;
		try {
			Mockito.when(resultSet.getLong("id")).thenReturn(computerTest.getId());
			Mockito.when(resultSet.getString("computer_name")).thenReturn(computerTest.getName());
			Mockito.when(resultSet.getDate("introduced"))
					.thenReturn(DateMapper.sqlDateFromLocalDate(computerTest.getIntroduced()));
			Mockito.when(resultSet.getDate("discontinued"))
					.thenReturn(DateMapper.sqlDateFromLocalDate(computerTest.getDiscontinued()));
			Mockito.when(resultSet.getLong("company_id")).thenReturn(computerTest.getCompany().getId());
			Mockito.when(resultSet.getString("company_name")).thenReturn(computerTest.getCompany().getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			computer = ComputerMapper.mapSQLToComputer(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(computerTest, computer);
	}

	@Test
	void mapComputerDTO() {
		long id = 1L;
		String name = "name";
		LocalDate introduced = LocalDate.parse("1995-01-01", dateTimeFormatter);
		LocalDate discontinued = LocalDate.parse("1995-01-01", dateTimeFormatter);
		long companyId = 2L;
		String companyName = "companyName";
		Computer computer = new Computer(id, name, introduced, discontinued, companyId, companyName);
		ComputerDTO computerDTO = ComputerMapper.mapComputerDTO(computer);
		assertEquals(id + "", computerDTO.getId());
		assertEquals(name, computerDTO.getName());
		assertEquals(introduced.toString(), computerDTO.getIntroduced());
		assertEquals(discontinued.toString(), computerDTO.getDiscontinued());
		assertEquals(companyId + "", computerDTO.getCompanyId());
		assertEquals(companyName, computerDTO.getCompanyName());
	}
}
