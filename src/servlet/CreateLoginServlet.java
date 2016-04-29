package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.CreateLoginController;
import src.Account;

public class CreateLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("In the Login Get servlet");
		String button = req.getParameter("buttonPress");
		if(button != null){
			if(button.toUpperCase().equals("CREATE")){
				req.setAttribute("username", req.getParameter("Username"));
				req.setAttribute("password", req.getParameter("Password"));
				req.getRequestDispatcher("/_view/create-login.jsp").forward(req, resp);
			}
			else if(req.getParameter("buttonPress").toUpperCase().equals("LOGIN")){
				//TODO: login
				req.getRequestDispatcher("/_view/front-end.jsp").forward(req, resp);
			}
			else{
				req.getRequestDispatcher("/_view/front-end.jsp").forward(req, resp);
			}
		}	
		else{
			req.getRequestDispatcher("/_view/front-end.jsp").forward(req, resp);;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("In the Login Post servlet");

		String user = null;
		String pass1 = null;
		String pass2 = null;
		String name = null;
		String email = null;
		String phone = null;
		String errorMessage = null;
		boolean loggedin = false;

		user = req.getParameter("username");
		pass1 = req.getParameter("pass1");
		pass2 = req.getParameter("pass2");
		name = req.getParameter("name");
		email = req.getParameter("email");
		phone = req.getParameter("phone");

		if(user.equals("") || user == null){
			errorMessage = "Invalid Username, please re-enter";
		}
		else if(pass1.equals("") || pass1 == null){
			errorMessage = "Invalid Password, please re-enter";
		}

		else if(pass2.equals("") || pass2 == null){
			errorMessage = "Passwords don't match, please re-enter";
		}

		else if(!pass2.equals(pass1)){
			errorMessage = "Passwords don't match, please re-enter";
		}

		else if(name.equals("") || name == null){
			errorMessage = "Please re-enter Name";
		}

		else if(email.equals("") || email == null){
			errorMessage = "Please re-enter Email";
		}

		else if(phone.equals("") || phone == null){
			errorMessage = "Please re-enter Phone Number";
		}
		else{
			Account account = new Account(user,pass1,-1,name, email, phone, false,null);
			int loginId = account.createLoginId();
			CreateLoginController controller = new CreateLoginController();

			if(controller.createAccount(account)){
				req.getSession(true).setAttribute("username", user);
				req.getSession().setAttribute("loginId", loginId);
				loggedin = true;
				req.setAttribute("loggedin", loggedin);
			}
			else{
				errorMessage = "Failed to Create Account, username probably already in use.";
			}
		}

		if(loggedin){
			req.getRequestDispatcher("/_view/front-end.jsp").forward(req, resp);
		}
		else{
			req.setAttribute("username", user);
			req.setAttribute("password", pass1);
			req.setAttribute("pass2", pass2);
			req.setAttribute("name", name);
			req.setAttribute("email", email);
			req.setAttribute("phone", phone);
			req.setAttribute("errorMessage",errorMessage);

			req.getRequestDispatcher("/_view/create-login.jsp").forward(req, resp);
		}
	}
}