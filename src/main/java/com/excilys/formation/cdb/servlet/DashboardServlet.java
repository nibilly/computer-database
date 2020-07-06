package com.excilys.formation.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.OrderBy;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.service.ComputerService;

@WebServlet(urlPatterns = "/dashboard")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 6587399260408067529L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		try {
			int nbRowsReturned = Integer.parseInt(request.getParameter("nbRowsReturned"));
			Page.setNbRowsReturned(nbRowsReturned);
		}
		catch(NumberFormatException e) {
		}
		Page<ComputerDTO> page = new Page<ComputerDTO>(1, 0);
		String search = request.getParameter("search");
		request.setAttribute("search", search);
		String orderByString = request.getParameter("orderBy");
		request.setAttribute("orderBy", orderByString);
		OrderBy orderBy = null;
		if(orderByString!= null) {
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

		listComputers(request, response, page, search, orderBy);
		request.setAttribute("page", page);
		int nbComputers = page.getNbComputerFound();
		request.setAttribute("nbComputers", nbComputers+"");		
		int nbPages = (nbComputers==0)?1:nbComputers/Page.getNbRowsReturned();
		if((nbComputers%Page.getNbRowsReturned())>0) {
			nbPages++;
		}
		request.setAttribute("nbPages", nbPages);

		this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}

	private void listComputers(HttpServletRequest request, HttpServletResponse response, Page<ComputerDTO> pageDTO, String search, OrderBy orderBy) {
		int pageRequested;
		try {
			pageRequested = Integer.parseInt(request.getParameter("page"));
		}
		catch (NumberFormatException e) {
			pageRequested = 1;
		}
		int nbRowsJumped = Page.getNbRowsReturned() * (pageRequested-1);
		Page<Computer> page = new Page<Computer>(pageRequested, nbRowsJumped);
		ComputerService.findComputersPageSearchOrderBy(page, search, orderBy);
		List<ComputerDTO> computersDTO = new ArrayList<ComputerDTO>();
		for (Computer computer : page.getEntities()) {
			computersDTO.add(ComputerMapper.mapComputerDTO(computer));
		}
		pageDTO.setPageNumber(pageRequested);
		pageDTO.setNbRowsJumped(nbRowsJumped);
		pageDTO.setEntities(computersDTO);
		pageDTO.setNbComputerFound(page.getNbComputerFound());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String selection = request.getParameter("selection");
		List<String> selections = Arrays.asList(selection.split(","));
		for (String string : selections) {
			try {
				long id = Long.parseLong(string);
				ComputerService.deleteComputerById(id);
			}
			catch(NumberFormatException e) {
				e.printStackTrace();
			}
		}
		this.doGet(request, response);
	}


}
