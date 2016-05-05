package model;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import src.Account;
import src.Book;
import src.BookForSale;

public class BooksForSaleTest {
	BookForSale b1, b2, b3;
	
	Book book1;
	Book book2;
	Book book3;
	
	
	Account a1;
	@Before
	public void SetUp(){
		//Author lists
		List<String> l1 = new ArrayList<String>();
		l1.add("Dr. Suess");
		List<String> l2 = new ArrayList<String>();
		l2.add("Socrates");
		l2.add("Gerald H. J. von Miller");
		//Books
		book1 = new Book("Green Eggs and Ham", l1, "9780007180561","The best book!" );
		book2 = new Book("Some dumb book", l2,"1","Not a book");
		book3 = new Book(null,null,null,null);
		b1 = new BookForSale(book1, "numbers", a1);
		List<BookForSale> x1 = new ArrayList<BookForSale>();
	    x1.add(0, b1);
		
		//Accounts
	    a1 = new Account("LoganH24", "abc123", 1, "LogeyBear", "Logan.Harris1995@gmail.com", "(570)-259-9465", false, x1);
		//this.b1 = new BookForSale(book1, "$15", );
	
		}
		@Test
		public void setBookTest(){
			b1.setBook(book1);
			
			assertEquals(b1.getBook(), book1);	
		}
		
		@Test
		public void getBookTest(){
			assertEquals(b1.getBook(), book1);
			
		}
		
		@Test
		public void getPriceTest(){
			
			assertEquals(b1.getPrice(), "numbers");
			
		}
}

