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
		
		SearchController search = new SearchController();
		List<Book> books = search.getAllBooks();
		
		req.setAttribute("books", books);
		req.getRequestDispatcher("/_view/searchResult.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		SearchController search = new SearchController();
		String bar = req.getParameter("search");
		String byButton = req.getParameter("bybutton");
		List<Book> books = null;
		
		if(bar != null && !bar.equals("")){
			if(byButton.equals("Search by Title")){
				books = search.getBooksByTitle(bar);
			}
			else if(byButton.equals("Search by Author Last Name")){
				books = search.getBooksByAuthorLastName(bar);
			}
			else if(byButton.equals("Search by ISBN")){
				books = search.getBooksByISBN(bar);
			}
			else {
				books = search.getBooksByTitle(bar);
			}
		}
		else{
			books = search.getAllBooks();
		}
		
		
		req.setAttribute("books", books);
		req.getRequestDispatcher("/_view/searchResult.jsp").forward(req, resp);
	}

}
