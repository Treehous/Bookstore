package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.EnterBookController;
import database.DatabaseProvider;
import database.IDatabase;
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
		System.out.println("In the EnterBook Post servlet");

		String error = null;
		String success = null;

		String title = req.getParameter("title");
		String author = req.getParameter("author");
		String isbn = req.getParameter("isbn");

		if(title == null || title.equals("")||
			author == null||author.equals("")||
			isbn == null  || isbn.equals("")){
			error = "Invalid entry";
		}
		else{
			ArrayList<String> authors = new ArrayList<String>();
			authors.add(author);
			EnterBookController enter = new EnterBookController();
				
			if(!enter.insertBook(new Book(title,authors,isbn,""))){
				error = "Failed to insert book into database.";
			}
		}
			
		if(error == null){
			success = "Successly added \"" + title + "\" into database.";
		}
		
		req.setAttribute("errorMessage",   error);
		req.setAttribute("successMessage", success);
		req.getRequestDispatcher("/_view/enterBook.jsp").forward(req, resp);
	}
}
