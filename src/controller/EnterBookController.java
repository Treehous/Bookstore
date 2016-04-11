package controller;

import database.DatabaseProvider;
import database.IDatabase;
import model.BookstoreModel;
import src.Book;

public class EnterBookController {
	private IDatabase database;
	private BookstoreModel model;

	public boolean insertBook(Book book) {
		this.database = DatabaseProvider.getDatabase();
		return this.database.insertBookIntoDatabase(book);
	}
}
