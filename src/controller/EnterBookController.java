package controller;

import database.DatabaseProvider;
import database.IDatabase;
import src.Book;

public class EnterBookController {

	public boolean insertBook(Book book) throws Exception{
		IDatabase database = DatabaseProvider.getDatabase();
		database.insertBookIntoDatabase(book);
		return true;
	}
}
