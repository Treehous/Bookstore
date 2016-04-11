package controller;

import src.Login;


public class LoginController {
	private Login model;
	

	public void setModel(Login model) {
		this.model = model;
	}
		
	public void setSuccessfulLogin() {
		if (model.getUser() == "host" && model.getPass() == "password")
		{
			model.check = true;
		}
	}

	public void setUnsuccessfulLogin() {
		if (model.getUser() != "host" || model.getPass() != "password")
		{
			model.check = false;
		}
	}
}
		