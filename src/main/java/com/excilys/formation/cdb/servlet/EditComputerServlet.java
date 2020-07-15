package com.excilys.formation.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.validation.ComputerValidation;
import com.excilys.formation.cdb.validation.Validation;

@WebServlet(urlPatterns = "/editComputer")
public class EditComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ComputerService computerService;

	private CompanyService companyService;

	public EditComputerServlet() {
		computerService = (ComputerService) ContextFactory.getApplicationContext().getBean("computerService");
		companyService = (CompanyService) ContextFactory.getApplicationContext().getBean("companyService");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("computerId");
		if (id == null) {
			id = request.getParameter("id");
		}
		request.setAttribute("computerId", id);
		List<Company> companies = companyService.findAll();
		List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
		for (Company company : companies) {
			companiesDTO.add(CompanyMapper.mapCompanyDTO(company));
		}
		request.setAttribute("companies", companiesDTO);
		this.getServletContext().getRequestDispatcher("/views/editComputer.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("company");
		ComputerDTO computerDTO = new ComputerDTO(id, name, introduced, discontinued, companyId, null);
		Computer computer = new Computer();
		Validation validation = ComputerValidation.editValidation(computerDTO, computer);
		if (validation != Validation.NO_ERROR) {
			request.setAttribute("error", validation);
		} else {
			computerService.updateComputer(computer);
			response.sendRedirect("/computer-database/dashboard");
			return;
		}
		this.doGet(request, response);
	}

}
