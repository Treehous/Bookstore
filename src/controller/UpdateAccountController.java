package controller;

import database.DatabaseProvider;
import database.IDatabase;
import src.Account;

public class UpdateAccountController {
	private IDatabase database;
	
	public UpdateAccountController(){
		this.database = DatabaseProvider.getDatabase();
	}
	
	public boolean updateUserAccount(String oldUser, Account newAccount){
		return this.database.updateAccountByUsername(oldUser, newAccount);
	}
}
