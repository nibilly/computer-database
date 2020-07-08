package com.excilys.formation.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.validation.ComputerValidation;
import com.excilys.formation.cdb.validation.Validation;

@WebServlet(urlPatterns = "/add-computer")
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ComputerService computerService = (ComputerService) new ClassPathXmlApplicationContext("beans.xml")
			.getBean("computerService");
	private CompanyService companyService = (CompanyService) new ClassPathXmlApplicationContext("beans.xml")
			.getBean("companyService");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Company> companies = companyService.findAll();
		List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
		for (Company company : companies) {
			companiesDTO.add(CompanyMapper.mapCompanyDTO(company));
		}
		request.setAttribute("companies", companiesDTO);
		this.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("company");
		Computer computer = new Computer();
		Validation validation = ComputerValidation.validation(name, introduced, discontinued, companyId, computer);
		if (validation != Validation.NO_ERROR) {
			request.setAttribute("error", validation);
		} else {
			computerService.createComputer(computer);
		}
		doGet(request, response);
	}
}
