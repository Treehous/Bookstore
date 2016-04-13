package controller;

import database.DatabaseProvider;
import database.IDatabase;
import src.Book;

public class EnterBookController {
	private IDatabase database;

	public boolean insertBook(Book book) {
		this.database = DatabaseProvider.getDatabase();
		return this.database.insertBookIntoDatabase(book);
	}
}
