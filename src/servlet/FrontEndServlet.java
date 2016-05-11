package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.LoginController;

public class FrontEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Front End Get");
		LoginController login = new LoginController();
		if(login.handleLoginCheck(req)){
			req.getRequestDispatcher("/_view/front-end.jsp").forward(req, resp);
		}
		else{
			req.getRequestDispatcher("/_view/front-end.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LoginController login = new LoginController();
		if(login.handleLoginCheck(req)){
			req.getRequestDispatcher("/_view/front-end.jsp").forward(req, resp);
		}
		else{
			req.getRequestDispatcher("/_view/front-end.jsp").forward(req, resp);
		}
	}
}
