package com.excilys.formation.cdb.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.dto.DashboardDTO;
import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.OrderBy;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.service.ComputerService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	private ComputerService computerService;

	public DashboardController(ComputerService computerService) {
		this.computerService = computerService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getDashboard(DashboardDTO dashboardDTO, ModelMap model) {
		try {
			int nbRowsReturned1 = Integer.parseInt(dashboardDTO.getNbRowsReturned());
			Page.setNbRowsReturned(nbRowsReturned1);
		} catch (NumberFormatException e) {
		}
		Page<ComputerDTO> page = new Page<ComputerDTO>(1, 0);
		String search = dashboardDTO.getSearch();
		model.addAttribute("search", search);
		page.setSearch(search);
		String orderByString = dashboardDTO.getOrderBy();
		model.addAttribute("orderBy", orderByString);
		OrderBy orderBy = null;
		if (orderByString != null) {
			switch (orderByString) {
			case "computer":
				orderBy = OrderBy.COMPUTER_NAME;
				break;
			case "introduced":
				orderBy = OrderBy.INTRODUCED;
				break;
			case "discontinued":
				orderBy = OrderBy.DISCONTINUED;
				break;
			case "company":
				orderBy = OrderBy.COMPANY_NAME;
				break;
			default:
				orderBy = null;
				break;
			}
		}
		page.setOrderBy(orderBy);
		listComputers(model, page, dashboardDTO);
		model.addAttribute("page", page);
		int nbComputers = page.getNbComputerFound();
		model.addAttribute("nbComputers", nbComputers + "");
		int nbPages = (nbComputers == 0) ? 1 : nbComputers / Page.getNbRowsReturned();
		if ((nbComputers % Page.getNbRowsReturned()) > 0) {
			nbPages++;
		}
		model.addAttribute("nbPages", nbPages);

		return "dashboard";
	}

	private void listComputers(ModelMap model, Page<ComputerDTO> pageDTO, DashboardDTO dashboardDTO) {
		int pageRequested;
		try {
			pageRequested = Integer.parseInt(dashboardDTO.getPage());
		} catch (NumberFormatException e) {
			pageRequested = 1;
		}
		int nbRowsJumped = Page.getNbRowsReturned() * (pageRequested - 1);
		Page<Computer> page = new Page<Computer>(pageRequested, nbRowsJumped);
		page.setSearch(pageDTO.getSearch());
		page.setOrderBy(pageDTO.getOrderBy());
		computerService.findComputersPageSearchOrderBy(page);
		List<ComputerDTO> computersDTO = new ArrayList<ComputerDTO>();
		for (Computer computer : page.getEntities()) {
			computersDTO.add(ComputerMapper.mapComputerDTO(computer));
		}
		pageDTO.setPageNumber(pageRequested);
		pageDTO.setNbRowsJumped(nbRowsJumped);
		pageDTO.setEntities(computersDTO);
		pageDTO.setNbComputerFound(page.getNbComputerFound());
	}

	@RequestMapping(method = RequestMethod.POST)
	public void deleteDashboard(DashboardDTO dashboardDTO, @RequestParam("selection") String selection,
			ModelMap model) {
		List<String> selections = Arrays.asList(selection.split(","));
		for (String string : selections) {
			try {
				long id = Long.parseLong(string);
				computerService.deleteComputerById(id);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		getDashboard(dashboardDTO, model);
	}
}
