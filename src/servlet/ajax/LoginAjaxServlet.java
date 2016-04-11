package servlet.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.LoginController;
import src.Login;

public class LoginAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doRequest(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doRequest(req, resp);
	}

	private void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get parameters
		String user = getLogin(req, "Username");
		String pass = getLogin(req, "Password");
		
		// Check whether parameters are valid
		if (user == null || pass == null) {
			badRequest("Bad parameters", resp);
			return;
		}
		
		Login model = new Login();
		model.setUser(user);
		model.setPass(pass);

		
		// If an action was specified, use a GuessingGameController to carry it out
		String action = req.getParameter("action");
		if (action != null) {
			LoginController controller = new LoginController();
			controller.setModel(model);
			
			if (action.equals("submit")) {
				controller.setSuccessfulLogin();
			}
		
		}
			
		boolean guess = model.check;
		
		String log;
		
		if (guess == true)
		{
			log = "Welcome!";
		}
		else
		{
			log = "failure";
		}
		// Send back updated model to client
		resp.setContentType("application/json");
		resp.getWriter().println(
				"{ \"min\": " + model.getUser() +
				", \"max\": " + model.getPass() +
				", \"guess\": " + log + "}" );
		
		// Send back a response
		resp.setContentType("text/plain");
	}

	private Double getDouble(HttpServletRequest req, String name) {
		String val = req.getParameter(name);
		if (val == null) {
			return null;
		}
		try {
			return Double.parseDouble(val);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	private String getLogin(HttpServletRequest req, String name) {
		String val = req.getParameter(name);
		if (val == null) {
			return null;
		}
		return name;
	}

	private void badRequest(String message, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		resp.getWriter().println(message);
	}
}
