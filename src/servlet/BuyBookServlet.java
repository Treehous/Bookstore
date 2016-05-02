package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.LoginController;
import src.ObjectHandler;

public class BuyBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//validate login
		String user = ObjectHandler.castObject(req.getSession().getAttribute("username"));
		if(user != null){
			Integer loginId = ObjectHandler.castObject(req.getSession().getAttribute("login_id"));
			LoginController login = new LoginController();
			loginId = login.validateLogin(user, loginId);
			if(loginId >= 0){
				req.getSession().setAttribute("login_id", loginId);
				req.setAttribute("account", login.returnAccountForUsername(user));
				req.setAttribute("loggedin", true);
				req.getRequestDispatcher("/_view/buy-book.jsp").forward(req, resp);
			}
			else{
				req.setAttribute("loggedin", false);
				req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
			}

		}
		else{
			req.setAttribute("loggedin", false);
			req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {


	}
}
