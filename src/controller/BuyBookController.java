package controller;

import java.util.List;

import database.DatabaseProvider;
import database.IDatabase;
import src.BookForSale;

public class BuyBookController {
	private IDatabase database;
	
	public BuyBookController(){
		this.database = DatabaseProvider.getDatabase();
	}
	public List<BookForSale> getBooksForSaleByTitle(String title){
		return this.database.queryBooksForSaleByTitle(title);
	}
}
