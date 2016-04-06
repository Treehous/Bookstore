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
	
	public SearchController() throws Exception{
		this.model = new BookstoreModel();
		this.database = DatabaseProvider.getDatabase();
	}
	
	public List<Book> getBooksByAuthor(List<Author> authors){
		
		return null;
	}
}
