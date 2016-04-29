package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.SearchController;
import src.Author;
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
		String searchBar = req.getParameter("search");
		String byButton = req.getParameter("bybutton");
		List<Book> books = null;
		
		if(searchBar != null && !searchBar.equals("")){
			if(byButton.equals("Search by Title")){
				books = search.getBooksByTitle(searchBar);
			}
			else if(byButton.equals("Search by Author Last Name")){
				Author author = new Author(searchBar);
				books = search.getBooksByAuthor(author);
			}
			else if(byButton.equals("Search by ISBN")){
				books = search.getBooksByISBN(searchBar);
			}
			else {
				books = search.getBooksByTitle(searchBar);
			}
		}
		else{
			books = search.getAllBooks();
		}
		
		
		req.setAttribute("books", books);
		req.getRequestDispatcher("/_view/searchResult.jsp").forward(req, resp);
	}

}
