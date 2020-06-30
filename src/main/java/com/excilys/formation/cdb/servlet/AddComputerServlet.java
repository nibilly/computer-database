package com.excilys.formation.cdb.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.exception.Validation;
import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.mapper.DateMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;

@WebServlet(urlPatterns = "/add-computer")
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 5638126407477651967L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies = CompanyService.findAll();
		List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
		for (Company company : companies) {
			companiesDTO.add(CompanyMapper.mapCompanyDTO(company));
		}
		request.setAttribute("companies", companiesDTO);
		this.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("company");
		Computer computer = new Computer();
		Validation validation = validation(name, introduced, discontinued, companyId, computer);
		if(validation != Validation.NO_ERROR) {
			request.setAttribute("error", validation);
		}
		else
		{
			ComputerService.createComputer(computer);
		}
		doGet(request, response);
	}

	private Validation validation(String name, String introduced, String discontinued, String companyId, Computer computer) {
		Validation validationError = Validation.NO_ERROR;
		LocalDate introducedDate;
		LocalDate discontinuedDate;
		long companyIdLong;
		if(name == null || name.equals("")) {
			validationError = Validation.NAME_ERROR;
		}
		else {
			computer.setName(name);
			try {
				introducedDate = (introduced.equals(""))?null:DateMapper.localDateFromString(introduced);
				computer.setIntroduced(introducedDate);
				try {
					discontinuedDate = (discontinued.equals(""))?null:DateMapper.localDateFromString(discontinued);
					computer.setDiscontinued(discontinuedDate);
					if(introducedDate != null && discontinuedDate != null) {
						if(introducedDate.compareTo(discontinuedDate) >=0) {
							validationError = Validation.DATE_PRECEDENCE_ERROR;
							return validationError;
						}
					}
					try {
						companyIdLong = Long.parseLong(companyId);
						if(companyIdLong > 0 || companyIdLong < CompanyService.getNbCompanies()) {
							Company company = CompanyService.findById(companyIdLong);
							computer.setCompany(company);
						}
						else {
							throw new NumberFormatException();
						}
					}
					catch(NumberFormatException e) {
						validationError = Validation.COMPANY_ERROR;
					}
				}
				catch(DateTimeParseException e) {
					validationError = Validation.DISCONTINUED_DATE_ERROR;
				}
			}
			catch(DateTimeParseException e) {
				validationError = Validation.INTRODUCED_DATE_ERROR;
			}
		}

		return validationError;
	}
}
