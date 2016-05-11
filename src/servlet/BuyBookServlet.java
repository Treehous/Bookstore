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
			ArrayList<Book> titles = ObjectHandler.castObject(req.getSession().getAttribute("booklist"));
			if(titles != null){
				BuyBookController buy = new BuyBookController();
				List<BookForSale> books= new ArrayList<BookForSale>();
				for(Book book: titles){
					String title = book.getTitle();
					String radio = req.getParameter("r"+title);
					if(title.equals(radio)){
						books.addAll(buy.getBooksForSaleByTitle(title));
					}
				}
				req.setAttribute("books", books);
				req.getSession().setAttribute("booklist",books);
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
			
			
			req.getRequestDispatcher("/_view/buy-book.jsp").forward(req, resp);
		}
		else{
			req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
		}

	}
}
