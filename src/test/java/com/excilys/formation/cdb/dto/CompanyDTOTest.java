package com.excilys.formation.cdb.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CompanyDTOTest {
	@Test
	public void companyDTOContructorFull() {
		String id = "1";
		String name = "name";
		CompanyDTO companyDTO = new CompanyDTO(id, name);
		assertEquals(id, companyDTO.getId());
		assertEquals(name, companyDTO.getName());
	}

	@Test
	public void companyDTOContructorEmpty() {
		String id = "1";
		String name = "name";
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(id);
		companyDTO.setName(name);
		assertEquals(id, companyDTO.getId());
		assertEquals(name, companyDTO.getName());
	}
}
