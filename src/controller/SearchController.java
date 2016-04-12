package controller;

import java.util.List;

import database.DatabaseProvider;
import database.IDatabase;
import model.BookstoreModel;
import src.Author;
import src.Book;

public class SearchController {
	private BookstoreModel model;
	private IDatabase database;
	
	public SearchController() {
		this.model = new BookstoreModel();
	}
	
	public List<Book> getBooksByAuthor(List<Author> authors){
		this.database = DatabaseProvider.getDatabase();
		
		return null;
	} 
	public List<Book> getBooksByISBN(String isbn){
		this.database = DatabaseProvider.getDatabase();
		return database.queryBookByISBN(isbn);
	}
}
