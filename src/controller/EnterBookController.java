package controller;

import database.DatabaseProvider;
import database.IDatabase;
import src.Account;
import src.Book;

public class EnterBookController {
	private IDatabase database;
	
	public EnterBookController(){
		this.database = DatabaseProvider.getDatabase();
	}

	public boolean insertBook(Book book) {
		return this.database.insertBookIntoDatabase(book);
	}
	
	public boolean updateAccount(Account account){
		return this.database.updateAccountByUsername(account.getUsername(), account);
	}
}
