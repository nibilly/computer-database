package com.excilys.formation.cdb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.validation.ComputerValidation;
import com.excilys.formation.cdb.validation.Validation;

@RequestMapping("/add-computer")
public class AddComputerController {

	private ComputerService computerService;

	private CompanyService companyService;

	public AddComputerController(ComputerService computerService, CompanyService companyService) {
		this.companyService = companyService;
		this.computerService = computerService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String displayAdd(ModelMap model) {
		List<Company> companies = companyService.findAll();
		List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
		for (Company company : companies) {
			companiesDTO.add(CompanyMapper.mapCompanyDTO(company));
		}
		model.addAttribute("companies", companiesDTO);
		return "addComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	public RedirectView createComputer(ComputerDTO computerDTO, ModelMap model) {
		Computer computer = new Computer();
		Validation validation = ComputerValidation.validation(computerDTO, computer);
		if (validation != Validation.NO_ERROR) {
			model.addAttribute("error", validation);
			return new RedirectView("add-computer");
		} else {
			computerService.createComputer(computer);
		}
		return new RedirectView("dashboard");
	}
}
