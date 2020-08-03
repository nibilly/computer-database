package com.excilys.formation.cdb.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.service.CompanyService;

@RestController
@RequestMapping(value = "/api/companies", produces = { MediaType.APPLICATION_JSON_VALUE })
public class CompanyController {
	private CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping
	public List<Company> getCompanies() {
		return companyService.findAll();
	}

	@GetMapping("/pages/{pageIndex}/{nbRowsReturned}")
	public ResponseEntity<Page<Company>> getCompanyPages(@PathVariable(name = "pageIndex") int pageIndex,
			@PathVariable(name = "nbRowsReturned") int nbRowsReturned) {
		if (nbRowsReturned < 1 || pageIndex < 0) {
			return new ResponseEntity<Page<Company>>(HttpStatus.BAD_REQUEST);
		}
		Page<Company> page = new Page<Company>(pageIndex, nbRowsReturned);
		companyService.findCompanyPages(page);
		return new ResponseEntity<Page<Company>>(page, HttpStatus.OK);
	}

	@GetMapping("/nb")
	public int getNbCompany() {
		return companyService.getNbCompanies();
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Company> getCompanyById(@PathVariable int id) {
		Company company = companyService.findById(id);
		return new ResponseEntity<Company>(company, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		companyService.delete(id);
		return new ResponseEntity<String>("request received", HttpStatus.OK);
	}
}
