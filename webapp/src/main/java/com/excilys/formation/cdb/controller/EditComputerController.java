package com.excilys.formation.cdb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

@RequestMapping("/editComputer")
public class EditComputerController {
	private ComputerService computerService;

	private CompanyService companyService;

	private static boolean postFulFill = false;

	public EditComputerController(ComputerService computerService, CompanyService companyService) {
		this.computerService = computerService;
		this.companyService = companyService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String displayEdit(@RequestParam("computerId") String computerId, ModelMap model) {
		if (!postFulFill) {
			fulfillModel(computerId, model);
		}
		postFulFill = false;
		return "editComputer";
	}

	private void fulfillModel(String computerId, ModelMap model) {
		List<Company> companies = companyService.findAll();
		List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
		for (Company company : companies) {
			companiesDTO.add(CompanyMapper.mapCompanyDTO(company));
		}
		model.addAttribute("computerId", computerId);
		model.addAttribute("companies", companiesDTO);
	}

	@RequestMapping(method = RequestMethod.POST)
	public RedirectView update(ComputerDTO computerDTO, ModelMap model) {
		Computer computer = new Computer();
		Validation validation = ComputerValidation.editValidation(computerDTO, computer);
		if (validation == Validation.NO_ERROR) {
			computerService.updateComputer(computer);
			return new RedirectView("dashboard");
		} else {
			fulfillModel(computerDTO.getId(), model);
			model.addAttribute("error", validation);
			postFulFill = true;
		}
		return new RedirectView("editComputer");
	}
}
