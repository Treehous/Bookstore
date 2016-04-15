package controller;

import java.util.List;

import database.DatabaseProvider;
import database.IDatabase;
import src.Author;
import src.Book;

public class SearchController {
	private IDatabase database;
	
	public List<Book> getBooksByAuthorLastName(String lastname){
		this.database = DatabaseProvider.getDatabase();
		return this.database.queryForBooksByAuthorLastName(lastname);
	} 
	
	public List<Book> getBooksByISBN(String isbn){
		this.database = DatabaseProvider.getDatabase();
		return this.database.queryForBooksByISBN(isbn);
	}

	public List<Book> getAllBooks() {
		this.database = DatabaseProvider.getDatabase();
		return this.database.queryForAllBooks();
	}

	public List<Book> getBooksByTitle(String title) {
		this.database = DatabaseProvider.getDatabase();
		return this.database.queryForBooksByTitle(title);
	}
}
