package model;

import java.util.ArrayList;
import java.util.List;

public final class Book {
	private final String title;
	private final ArrayList<AuthorName> authors = new ArrayList<AuthorName>();
	private final String isbn;
	private final String description;
	
	public Book(String title, List<String> authors, String isbn, String description){
		this.title = title;
		
		for(String s: authors){
			this.authors.add(this.parseAuthorName(s));
		}
		
		this.isbn = isbn;
		this.description = description;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public List<String> getAuthors(){
		List<String> list = new ArrayList<String>();
		
		for(AuthorName a: authors){
			list.add(a.getFullName());
		}
		
		return list;
	}
	
	public String getISBN(){
		return this.isbn;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	private AuthorName parseAuthorName(String s){
		String first = null;
		String last = null;
		
		int index = s.indexOf(' ');
		
		if(index == -1){
			last = s;
		}
		
		else {
			first = s.substring(0, index);
			last = s.substring(index+1, s.length());
		}
		
		return new AuthorName(first,last);
	}
	
	//can be replaced with a full-blown author class
	private final class AuthorName{
		private final String firstName;
		private final String lastName;
		
		private AuthorName(String first, String last){
			if(first != null)
				this.firstName = first;
			else 
				this.firstName = "";
				
			if(last != null)
				this.lastName = last;
			else 
				this.lastName = "";
		}
		
		private String getFirstName(){
			return this.firstName;
		}
		
		private String getLastName(){
			return this.lastName;
		}
		
		private String getFullName(){
			return this.getLastName() + ", " + this.getFirstName();
		}
	}
}
