package model;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import src.Book;

public class BookTest {
	Book b1, b2, b3;
	@Before
	public void setUp(){
		List<String> l1 = new ArrayList<String>();
		l1.add("Dr. Suess");
		
		List<String> l2 = new ArrayList<String>();
		l2.add("Socrates");
		l2.add("Gerald H. J. von Miller");
		
		
		this.b1 = new Book("Green Eggs and Ham", l1, "9780007180561","The best book!" );
		this.b2 = new Book("Some dumb book", l2,"1","Not a book");
		this.b3 = new Book(null,null,null,null);
	}
	
	@Test
	public void getTitleTest(){
		assertEquals(b1.getTitle(),"Green Eggs and Ham");
		assertEquals(b2.getTitle(),"Some dumb book");
		assertEquals(b3.getTitle(),null);
	}
	
	@Test
	public void getAuthorTest(){
		assertEquals(b1.getAuthors().get(0),"Suess, Dr.");
		assertEquals(b2.getAuthors().get(0),"Socrates");
		assertEquals(b2.getAuthors().get(1),"H. J. von Miller, Gerald");
		assertEquals(b3.getAuthors().isEmpty(),true);
	}
	
	@Test
	public void getISBNTest(){
		assertEquals(b1.getIsbn(),"9780007180561");
		assertEquals(b2.getIsbn(),"1");
		assertEquals(b3.getIsbn(),null);
	}
	 
	@Test
	public void getDescriptionTest(){
		assertEquals(b1.getDescription(),"The best book!");
		assertEquals(b2.getDescription(),"Not a book");
		assertEquals(b3.getDescription(),null);
	}
}