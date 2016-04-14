package src;

import java.util.ArrayList;
import java.util.List;

public final class Book {
	private String title;
	private List<Author> authors = new ArrayList<Author>();
	private String isbn;
	private String description;
	
	public Book(){}

	public Book(String title, List<String> authors, String isbn, String description){
		this.title = title;

		if(authors != null)
			for(String s: authors){
				this.authors.add(new Author(s));
			}

		this.isbn = isbn;

		this.description = description;
	}

	public String getTitle(){
		return this.title;
	}

	public final List<Author> getAuthors(){
		return this.authors;
	}
	
	//temp function to adapt for .jsp
	public String getAuthorslastname(){
		return this.authors.get(0).getAuthorsLastName();
	}
	//temp function to adapt for .jsp
	public String getAuthorsfirstname(){
		return this.authors.get(0).getAuthorsFirstName();
	}

	public String getIsbn(){
		return this.isbn;
	}

	public String getDescription(){
		return this.description;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setAuthor(List<Author> authors){
		this.authors = authors;
	}
	
	public void setAuthor(String[] authors){
		if(authors != null)
			for(String s: authors){
				this.authors.add(new Author(s));
			}
	}
	
	public void setISBN(String isbn){
		this.isbn = isbn;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
}
