package com.excilys.formation.cdb.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ComputerDTOTest {

	@Test
	public void testComputerDTOComstructorFull() {
		String id = "1";
		String name = "name";
		String introduced = "1991-01-01";
		String discontinued = "2003-12-31";
		String companyId = "2";
		String companyName = "company name";
		ComputerDTO computerDTO = new ComputerDTO(id, name, introduced, discontinued, companyId, companyName);
		assertEquals(id, computerDTO.getId());
		assertEquals(name, computerDTO.getName());
		assertEquals(introduced, computerDTO.getIntroduced());
		assertEquals(discontinued, computerDTO.getDiscontinued());
		assertEquals(companyId, computerDTO.getCompanyId());
		assertEquals(companyName, computerDTO.getCompanyName());
	}

	@Test
	public void testComputerDTOComstructorEmpty() {
		String id = "1";
		String name = "name";
		String introduced = "1991-01-01";
		String discontinued = "2003-12-31";
		String companyId = "2";
		String companyName = "company name";
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(id);
		computerDTO.setName(name);
		computerDTO.setIntroduced(introduced);
		computerDTO.setDiscontinued(discontinued);
		computerDTO.setCompanyId(companyId);
		computerDTO.setCompanyName(companyName);
		assertEquals(id, computerDTO.getId());
		assertEquals(name, computerDTO.getName());
		assertEquals(introduced, computerDTO.getIntroduced());
		assertEquals(discontinued, computerDTO.getDiscontinued());
		assertEquals(companyId, computerDTO.getCompanyId());
		assertEquals(companyName, computerDTO.getCompanyName());
	}
}
