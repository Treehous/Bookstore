package controller;

import database.DatabaseProvider;
import database.IDatabase;
import src.Account;

public class CreateLoginController {
	private IDatabase database;
	
	public CreateLoginController(){
		this.database = DatabaseProvider.getDatabase();
	}
	
	public boolean createAccount(Account account){
		return this.database.insertNewAccountIntoDatabase(account);
	}
}
