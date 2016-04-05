package src;

import java.util.ArrayList;
import java.util.List;

public final class Book {
	private final String title;
	private ArrayList<Author> authors = new ArrayList<Author>();
	private final String isbn;
	private final String description;

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

	public List<String> getAuthors(){
		List<String> list = new ArrayList<String>();

		for(Author a: authors){
			list.add(a.getAuthorsFullName());
		}

		return list;
	}

	public String getISBN(){
		return this.isbn;
	}

	public String getDescription(){
		return this.description;
	}
}
