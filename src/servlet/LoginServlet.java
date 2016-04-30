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
		System.out.println("In LoginServlet DOGET");
		
		req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("In LoginServlet DOPOST");
		
		String username = null;
		String password = null;
		boolean loggedin = false;
		String errorMessage = null;
		
		username = req.getParameter("username");
		password = req.getParameter("password");
		
		if(username.equals("") || username == null ||
				password.equals("") || password == null){
			errorMessage = "Invalid username or password.";
		}
		else {
			LoginController controller = new LoginController();
			int loginId = controller.loginUser(username, password);
			if(loginId >= 0){
				req.getSession(true).setAttribute("username", username);
				req.getSession().setAttribute("login_id", loginId);
				loggedin = true;
				req.setAttribute("loggedin", loggedin);
			}
			else{
				errorMessage = "Invalid Username or password.";
			}
		}
		
		if(loggedin)
			req.getRequestDispatcher("/_view/front-end.jsp").forward(req, resp);
		else{
			req.setAttribute("username",username);
			req.setAttribute("errorMessage", errorMessage);
			req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
		}
	}

}
