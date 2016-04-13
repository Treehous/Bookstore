package controller;

import java.util.List;

import database.DatabaseProvider;
import database.IDatabase;
import src.Author;
import src.Book;

public class SearchController {
	private IDatabase database;
	
	public List<Book> getBooksByAuthor(List<Author> authors){
		this.database = DatabaseProvider.getDatabase();
		
		return null;
	} 
	
	public List<Book> getBooksByISBN(String isbn){
		this.database = DatabaseProvider.getDatabase();
		return database.queryForBooksByISBN(isbn);
	}

	public List<Book> getAllBooks() {
		this.database = DatabaseProvider.getDatabase();
		return database.queryForAllBooks();
	}
}
