package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.LoginController;
import src.ObjectHandler;

public class FrontEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("In the FrontEnd Get servlet");

		//validate login
		Boolean loggedin = ObjectHandler.castObject(req.getAttribute("loggedin"));
		if(loggedin != null){
			if(loggedin){
				String user = ObjectHandler.castObject(req.getSession().getAttribute("username"));
				Integer loginId = ObjectHandler.castObject(req.getSession().getAttribute("login_id"));
				LoginController login = new LoginController();
				loginId = login.validateLogin(user, loginId);
				req.getSession().setAttribute("login_id", loginId);
				req.setAttribute("account", login.returnAccountForUsername(user));
			}
		}
		req.getRequestDispatcher("/_view/front-end.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("In the FrontEnd Post servlet");

		//validate login
		Boolean loggedin = ObjectHandler.castObject(req.getAttribute("loggedin"));
		if(loggedin != null){
			if(loggedin){
				String user = ObjectHandler.castObject(req.getSession().getAttribute("username"));
				Integer loginId = ObjectHandler.castObject(req.getSession().getAttribute("login_id"));
				LoginController login = new LoginController();
				loginId = login.validateLogin(user, loginId);
				req.getSession().setAttribute("login_id", loginId);
				req.setAttribute("account", login.returnAccountForUsername(user));
			}
		}
		req.getRequestDispatcher("/_view/front-end.jsp").forward(req, resp);
	}
}
