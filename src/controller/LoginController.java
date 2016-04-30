package controller;

import database.DatabaseProvider;
import database.IDatabase;
import src.Account;

public class LoginController {
	private IDatabase database;

	public LoginController(){
		this.database = DatabaseProvider.getDatabase();
	}

	//returns new login id
	public int validateLogin(String username, int loginId){
		return -1;
	}

	//return new login id
	public int loginUser(String username, String password){
		int loginId = -1;
		String pass = this.database.queryForPasswordByUsername(username);
		if(pass!= null && password!=null){
			if(pass.equals(password)){
				Account account = this.database.queryForUserAccountByUsername(username);
				loginId = account.createLoginId();
				this.database.updateLoginIdByUsername(username, account.getLoginId());
			}
		}
		return loginId;
	}
}
