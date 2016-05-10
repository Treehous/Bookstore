package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.LoginController;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("In LoginServlet DOPOST");

		String username = null;
		String password = null;
		String buttonPress = null;
		boolean loggedin = false;
		String errorMessage = null;
		LoginController login = new LoginController();

		buttonPress = req.getParameter("buttonPress");

		if(buttonPress != null){
			if(buttonPress.toLowerCase().equals("logout")){
				req.getSession().setAttribute("loggedin",false);
				req.getSession().setAttribute("username", null);
				req.getSession().setAttribute("login_id", -1);
				req.getRequestDispatcher("/_view/front-end.jsp").forward(req, resp);
			}
			else if(buttonPress.toLowerCase().equals("login")){

				username = req.getParameter("username");
				password = req.getParameter("password");

				if("".equals(username) || username == null ||
						"".equals(password) || password == null){
					errorMessage = "Invalid username or password.";
				}
				else {
					int loginId = login.loginUser(username, password);
					if(loginId >= 0){
						req.getSession().setAttribute("username", username);
						req.getSession().setAttribute("login_id", loginId);
						loggedin = true;
						req.setAttribute("loggedin", loggedin);
					}
					else{
						errorMessage = "Invalid username or password.";
					}
				}

				if(loggedin){
					req.setAttribute("account", login.returnAccountForUsername(username));
					req.getRequestDispatcher("/_view/front-end.jsp").forward(req, resp);
				}
				else{
					req.setAttribute("username",username);
					req.setAttribute("errorMessage", errorMessage);
					req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
				}
			}
		}
		else{
			req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
		}
	}

}
