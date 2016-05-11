package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.BuyBookController;
import controller.LoginController;
import src.Book;
import src.BookForSale;
import src.ObjectHandler;

public class BuyBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LoginController login = new LoginController();
		if(login.handleLoginCheck(req)){
			Object booklistSession = req.getSession().getAttribute("booklist");
			ArrayList<Book> titles = ObjectHandler.castObject(booklistSession);
			
			//display booksforsale
			if(titles != null){
				BuyBookController buy = new BuyBookController();
				List<BookForSale> books= new ArrayList<BookForSale>();
				for(Book book: titles){
					String title = book.getTitle();
					String check = req.getParameter("c"+title);
					if(title.equals(check)){
						books.addAll(buy.getBooksForSaleByTitle(title));
					}
				}
				req.setAttribute("books", books);
				req.getSession().setAttribute("bfslist",books);
				req.getRequestDispatcher("/_view/buy-book.jsp").forward(req, resp);
			}
			else{
				
				req.getRequestDispatcher("/_view/search-result.jsp").forward(req, resp);
			}
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
			ArrayList<BookForSale> books = ObjectHandler.castObject(req.getSession().getAttribute("bfslist"));
			String errorMessage = "No book was selected to buy.";
			if(books != null){
				for(BookForSale book: books){
					String title = book.getBook().getTitle();
					String check = req.getParameter("c"+title);
					if(title.equals(check)){
						req.setAttribute("bfs",book);
						//req.getSession().setAttribute("booklist", null);
						//req.getSession().setAttribute("bfslist", null);
						errorMessage = null;
						break;
					}
				}
				req.setAttribute("errorMessage", errorMessage);
				req.getRequestDispatcher("/_view/display-info.jsp").forward(req, resp);
			}
			else{
				req.getRequestDispatcher("/_view/search-result.jsp").forward(req, resp);
			}
		}
		else{
			req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
		}

	}
}
