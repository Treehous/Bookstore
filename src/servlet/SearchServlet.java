package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.SearchController;
import src.Book;

public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("In the Search Get servlet");
		
		req.getRequestDispatcher("/_view/searchResult.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("In the Search Post servlet");
		
		
		SearchController con = new SearchController();
		List<Book> books = con.getBooksByISBN(req.getParameter("search"));
		req.setAttribute("books", books);
		System.out.println("END OF SERVLET");
		req.getRequestDispatcher("/_view/searchResult.jsp").forward(req, resp);
	}

}
