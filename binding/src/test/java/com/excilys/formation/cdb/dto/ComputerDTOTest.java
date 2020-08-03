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
		ComputerDTO computerDTO = new ComputerDTO(id, name, introduced, discontinued,
				new CompanyDTO(companyId, companyName));
		assertEquals(id, computerDTO.getId());
		assertEquals(name, computerDTO.getName());
		assertEquals(introduced, computerDTO.getIntroduced());
		assertEquals(discontinued, computerDTO.getDiscontinued());
		assertEquals(companyId, computerDTO.getCompanyDTO().getId());
		assertEquals(companyName, computerDTO.getCompanyDTO().getName());
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
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(companyId);
		companyDTO.setName(companyName);
		computerDTO.setCompanyDTO(companyDTO);
		assertEquals(id, computerDTO.getId());
		assertEquals(name, computerDTO.getName());
		assertEquals(introduced, computerDTO.getIntroduced());
		assertEquals(discontinued, computerDTO.getDiscontinued());
		assertEquals(companyId, computerDTO.getCompanyDTO().getId());
		assertEquals(companyName, computerDTO.getCompanyDTO().getName());
	}
}
