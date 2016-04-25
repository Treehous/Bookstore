package controller;

import database.DatabaseProvider;
import database.IDatabase;

public class LoginController {
	private IDatabase database;
	
	public LoginController(){
		this.database = DatabaseProvider.getDatabase();
	}
	
	//returns new login id
	public int validateLogin(String username, int loginId){
		return -1;
	}
	
	//returns new login id
	public int loginUser(String username, String password){
		return -1;
	}
}
		