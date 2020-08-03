package com.excilys.formation.cdb.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.exception.NoResultException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.validation.ComputerValidation;
import com.excilys.formation.cdb.validation.Validation;

@RestController
@RequestMapping(value = "/api/computers", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ComputerController {
	private ComputerService computerService;
	private ComputerValidation computerValidation;

	public void setComputerValidation(ComputerValidation computerValidation) {
		this.computerValidation = computerValidation;
	}

	public ComputerController(ComputerService computerService) {
		this.computerService = computerService;
	}

	@GetMapping
	public List<Computer> getAllComputers() {
		return computerService.findAll();
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Computer> getComputerById(@PathVariable int id) {
		Computer computer = null;
		try {
			computer = computerService.findById(id);
		} catch (NoResultException e) {
			return new ResponseEntity<Computer>(computer, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Computer>(computer, HttpStatus.OK);
	}

	@GetMapping(value = "/pages/{pageIndex}/{nbRowsReturned}")
	public ResponseEntity<Page<Computer>> getComputersPage(@PathVariable(name = "pageIndex") int pageIndex,
			@PathVariable(name = "nbRowsReturned") int nbRowsReturned) {
		if (nbRowsReturned < 1 || pageIndex < 0) {
			return new ResponseEntity<Page<Computer>>(HttpStatus.BAD_REQUEST);
		}
		Page<Computer> page = new Page<Computer>(pageIndex, nbRowsReturned);
		computerService.findComputersPages(page);
		return new ResponseEntity<Page<Computer>>(page, HttpStatus.OK);
	}

	@GetMapping("/nb")
	public int getNbComputer() {
		return computerService.getNbComputers();
	}

	@PutMapping
	public ResponseEntity<String> update(@RequestBody ComputerDTO computerDTO) {
		Computer computer = new Computer();
		Validation validation = computerValidation.editValidation(computerDTO, computer);
		if (validation == Validation.NO_ERROR) {
			computerService.updateComputer(computer);
			return new ResponseEntity<String>("updated", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(validation.toString(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		computerService.deleteComputerById(id);
		return new ResponseEntity<String>("request received", HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> create(@RequestBody ComputerDTO computerDTO) {
		Computer computer = new Computer();
		Validation validation = computerValidation.validation(computerDTO, computer);
		computerService.createComputer(computer);
		if (validation == Validation.NO_ERROR) {
			computerService.updateComputer(computer);
			return new ResponseEntity<String>("created", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>(validation.toString(), HttpStatus.BAD_REQUEST);
		}
	}

}
