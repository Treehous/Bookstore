package src;

public class BookForSale {
	private Book book = null;
	private String price = "";
	private Account owner = null;
	
	public BookForSale(){
		
	}
	
	public BookForSale(Book book, String price, Account owner){
		this.book = book;
		this.price = price;
		this.owner = owner;
	}
	
	public void setBook(Book book){
		this.book = book;
	}
	
	public void setPrice(String price){
		this.price = price;
	}
	
	public void setOwner(Account owner){
		this.owner = owner;
	}
	
	public Book getBook(){
		return this.book;
	}
	
	public String getPrice(){
		return this.price;
	}
	
	public Account getOwner(){
		return this.owner;
	}
}
