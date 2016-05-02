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
		int persistantLoginId = this.database.queryForLoginIdByUsername(username);
		Account account = this.database.queryForUserAccountByUsername(username);
		if(persistantLoginId == loginId && loginId != -1){
			account.createLoginId();
		}
		else{
			account.resetLoginId();
		}
		this.database.updateAccountByUsername(username, account);
		return account.getLoginId();
	}

	//return new login id
	public int loginUser(String username, String password){
		int loginId = -1;
		String pass = this.database.queryForPasswordByUsername(username);
		if(pass!= null && password!=null){
			if(pass.equals(password)){
				Account account = this.database.queryForUserAccountByUsername(username);
				loginId = account.createLoginId();
				this.database.updateAccountByUsername(username, account);
			}
		}
		return loginId;
	}
	
	public Account returnAccountForUsername(String username){
		return this.database.queryForUserAccountByUsername(username);
	}
}
