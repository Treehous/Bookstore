package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.EnterBookController;
import src.Book;

public class EnterBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("In the EnterBook Get servlet");

		req.getRequestDispatcher("/_view/enterBook.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String error = null;
		String success = null;



		String title = req.getParameter("title");
		String author = req.getParameter("author");
		String isbn = req.getParameter("isbn");

		ArrayList<String> authors = new ArrayList<String>();

		if(title == null || title.equals("")||
				author == null||author.equals("")||
				isbn == null  || isbn.equals("")){
			error = "Invalid entry";
		}
		else{
			authors.add(author);
			Book book = new Book(title,authors,isbn,"");
			EnterBookController enter = new EnterBookController();
			try {
				enter.insertBook(book);
				success = "entered book";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				error = "failed to insert book";
				e.printStackTrace();
			}
		}
		
		req.setAttribute("errorMessage",   error);
		req.setAttribute("successMessage", success);
		req.getRequestDispatcher("/_view/enterBook.jsp").forward(req, resp);
	}
}
