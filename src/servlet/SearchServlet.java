package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.LoginController;
import controller.SearchController;
import src.Author;
import src.Book;
import src.BookForSale;
import src.ObjectHandler;

public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//validate login
		boolean loggedin = false;
		String user = ObjectHandler.castObject(req.getSession().getAttribute("username"));
		if(user != null){
			Integer loginId = ObjectHandler.castObject(req.getSession().getAttribute("login_id"));
			LoginController login = new LoginController();
			loginId = login.validateLogin(user, loginId);
			if(loginId >= 0){
				req.getSession().setAttribute("login_id", loginId);
				loggedin = true;
				req.setAttribute("account", login.returnAccountForUsername(user));
			}
		}
		req.setAttribute("loggedin", loggedin);

		SearchController search = new SearchController();
		List<Book> books = search.getAllBooks();

		req.setAttribute("books", books);
		req.getRequestDispatcher("/_view/search-result.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//validate login
		boolean loggedin = false;
		String user = ObjectHandler.castObject(req.getSession().getAttribute("username"));
		if(user != null){
			Integer loginId = ObjectHandler.castObject(req.getSession().getAttribute("login_id"));
			LoginController login = new LoginController();
			loginId = login.validateLogin(user, loginId);
			if(loginId >= 0){
				req.getSession().setAttribute("login_id", loginId);
				loggedin = true;
				req.setAttribute("account", login.returnAccountForUsername(user));
			}
		}
		req.setAttribute("loggedin", loggedin);



		SearchController search = new SearchController();
		String searchBar = req.getParameter("search");
		String byButton = req.getParameter("bybutton");
		List<Book> books = null;
		if(searchBar != null && !searchBar.equals("")){
			if(byButton.equals("Search by Title")){
				books = search.getBooksByTitle(searchBar);
			}
			else if(byButton.equals("Search by Author")){
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
		req.getRequestDispatcher("/_view/search-result.jsp").forward(req, resp);
	}

}
