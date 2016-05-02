package controller;

import java.util.List;

import database.DatabaseProvider;
import database.IDatabase;
import src.Author;
import src.Book;

public class SearchController {
	private IDatabase database;
	
	public SearchController(){
		this.database = DatabaseProvider.getDatabase();
	}
	
	public List<Book> getBooksByAuthor(Author author){
		return this.database.queryForBooksByAuthor(author);
	} 
	
	public List<Book> getBooksByISBN(String isbn){
		return this.database.queryForBooksByISBN(isbn);
	}

	public List<Book> getAllBooks() {
		return this.database.queryForAllBooks();
	}

	public List<Book> getBooksByTitle(String title) {
		return this.database.queryForBooksByTitle(title);
	}
}
