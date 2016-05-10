package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.EnterBookController;
import controller.LoginController;
import src.Book;
import src.BookForSale;
import src.ObjectHandler;
import src.Account;

public class EnterBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LoginController login = new LoginController();
		
		//validate login
		if(login.handleLoginCheck(req)){
			req.getRequestDispatcher("/_view/enter-book.jsp").forward(req, resp);
		}
		else{
			req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LoginController login = new LoginController();
		
		if(login.handleLoginCheck(req)){
			String error = null;
			String success = null;

			EnterBookController enter = new EnterBookController();

			String title = req.getParameter("title");
			String author = req.getParameter("author");
			String isbn = req.getParameter("isbn");
			String price = req.getParameter("price");
			String user = ObjectHandler.castObject(req.getSession().getAttribute("username"));

			if(title == null  || title.equals("")||
					author == null||author.equals("")||
					isbn == null  || isbn.equals("") ||
					price == null || price.equals("")){
				error = "Invalid entry";
			}
			else{
				ArrayList<String> authors = new ArrayList<String>();
				authors.add(author);

				Account account = login.returnAccountForUsername(user);
				Book book = new Book(title,authors,isbn,"");
				BookForSale bfs = new BookForSale(book, price,account);
				account.addBookForSale(bfs);

				if(!enter.insertBook(book)){
					error = "Failed to insert book into database.";
				}
				else if(!enter.updateAccount(account)){
					error = "Failed to update your account, system will explode in 3....2....1...";
				}
			}

			if(error == null){
				success = "The book entitled: \"" + title + "\" is now for sale.";
			}

			req.setAttribute("errorMessage",   error);
			req.setAttribute("successMessage", success);
			req.getRequestDispatcher("/_view/enter-book.jsp").forward(req, resp);
		}
		else{
			req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
		}
	}
}
