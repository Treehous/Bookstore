package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.LoginController;
import controller.UpdateAccountController;
import src.Account;
import src.ObjectHandler;

public class UpdateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LoginController login = new LoginController();
		if(login.handleLoginCheck(req)){
			Account account = login.returnAccountForUsername(ObjectHandler.castObject(req.getSession().getAttribute("username")));
			
			
			req.setAttribute("account",account);
			req.getRequestDispatcher("/_view/update-account.jsp").forward(req, resp);
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
			String pass1 = null;
			String pass2 = null;
			String name = null;
			String email = null;
			String phone = null;
			String errorMessage = null;

			pass1 = req.getParameter("pass1");
			pass2 = req.getParameter("pass2");
			name = req.getParameter("name");
			email = req.getParameter("email");
			phone = req.getParameter("phone");
			
			if("".equals(pass1) || pass1 == null){
				errorMessage = "Invalid Password, please re-enter";
				pass1 = null;
			}

			else if("".equals(pass2) || pass2 == null){
				errorMessage = "Passwords don't match, please re-enter";
				pass2 = null;
			}

			else if(!pass2.equals(pass1)){
				errorMessage = "Passwords don't match, please re-enter";
				pass2 = null;
			}

			else if("".equals(name) || name == null){
				errorMessage = "Please re-enter Name";
				name = null;
			}

			else if("".equals(email) || email == null){
				errorMessage = "Please re-enter Email";
				email = null;
			}

			else if("".equals(phone) || phone == null){
				errorMessage = "Please re-enter Phone Number";
				phone = null;
			}
			else{
				Account oldAccount = login.returnAccountForUsername(ObjectHandler.castObject(req.getSession().getAttribute("username")));
				Account newAccount = new Account(oldAccount.getUsername(),pass1,oldAccount.getLoginId(),name,email,phone,false,oldAccount.getBooksForSale());
				UpdateAccountController update = new UpdateAccountController();
				if(!update.updateUserAccount(oldAccount.getUsername(), newAccount)){
					errorMessage = "Something horrible happened while updating your account.  I don't know what to do. AHhhhhh!!!!!";
				}
			}
			
			if(errorMessage==null){
				req.getRequestDispatcher("/_view/front-end.jsp").forward(req, resp);
			}
			else{
				req.setAttribute("errorMessage",errorMessage);
				Account account = login.returnAccountForUsername(ObjectHandler.castObject(req.getSession().getAttribute("username")));
				req.setAttribute("account", account);
				req.getRequestDispatcher("/_view/update-account.jsp").forward(req, resp);
			}
		}
		else{
			req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
		}
	}
}
