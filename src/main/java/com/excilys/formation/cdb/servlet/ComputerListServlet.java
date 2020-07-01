package com.excilys.formation.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.service.ComputerService;

@WebServlet(urlPatterns = "/dashboard")
public class ComputerListServlet extends HttpServlet {

	private static final long serialVersionUID = 6587399260408067529L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		int nbComputers = ComputerService.getNbComputers();
		request.setAttribute("nbComputers", nbComputers+"");

		try {
			int nbRowsReturned = Integer.parseInt(request.getParameter("nbRowsReturned"));
			Page.setNbRowsReturned(nbRowsReturned);
		}
		catch(NumberFormatException e) {
		}
		
		int pageRequested;
		try {
			pageRequested = Integer.parseInt(request.getParameter("page"));
		}
		catch (NumberFormatException e) {
			pageRequested = 1;
		}
		int nbRowsJumped = Page.getNbRowsReturned() * (pageRequested-1);
		Page<Computer> page = new Page<Computer>(pageRequested, nbRowsJumped);
		ComputerService.findComputersPages(page);
		List<ComputerDTO> computersDTO = new ArrayList<ComputerDTO>();
		for (Computer computer : page.getEntities()) {
			computersDTO.add(ComputerMapper.mapComputerDTO(computer));
		}
		Page<ComputerDTO> pageDTO = new Page<ComputerDTO>(pageRequested, nbRowsJumped);
		pageDTO.setEntities(computersDTO);
		request.setAttribute("page", pageDTO);
		
		int nbPages = nbComputers/Page.getNbRowsReturned();
		if((nbComputers%Page.getNbRowsReturned())>0) {
			nbPages++;
		}
		request.setAttribute("nbPages", nbPages);
		
		this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}

}
