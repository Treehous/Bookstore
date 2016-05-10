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
	Book book1, book2, book3;
	Account a1, a2, a3;
	
	@Before
	public void SetUp(){
		List<String> l1 = new ArrayList<String>();
		l1.add("Dr. Suess");
		List<String> l2 = new ArrayList<String>();
		l2.add("Socrates");
		l2.add("Gerald H. J. von Miller");
		
		this.book1 = new Book("Green Eggs and Ham", l1, "9780007180561","The best book!" );
		this.book2 = new Book("Some dumb book", l2,"1","Not a book");
		this.book3 = new Book(null,null,null,null);
		
		List<BookForSale> x1 = new ArrayList<BookForSale>();
	    x1.add(b1);
	    List<BookForSale> x2 = new ArrayList<BookForSale>();
	    x2.add(b2);
	    
	    this.a1 = new Account("LoganH24", "abc123", 1, "LogeyBear", "Logan.Harris1995@gmail.com", "(570)-259-5555", false, x1);
	    this.a2 = new Account("PLynn17", "def456", 2, "PattyBoy", "Patrick.Lynn1994@gmail.com", "(570)-259-4444", false, x2);
	    this.a3 = new Account("KTurowsk420", "ghi789", 3, "KazBlaster", "Kazimir.Turowski1994@gmail.com", "(570)-259-3333", false, null);
	    
		this.b1 = new BookForSale(book1, "$4.20", a1);
		this.b2 = new BookForSale(book2, "1", a2);
		this.b3 = new BookForSale(null, null, null);
		}
		
		@Test
		public void getBookTest()
		{
			assertEquals(b1.getBook(), book1);	
			assertEquals(b2.getBook(), book2);	
			assertEquals(b3.getBook(), null);	
		}
		
		@Test
		public void getPriceTest()
		{	
			assertEquals(b1.getPrice(), "$4.20");	
			assertEquals(b2.getPrice(), "1");
			assertEquals(b3.getPrice(), null);
		}
		
		@Test
		public void getOwnerTest()
		{
			assertEquals(b1.getOwner(), a1);
			assertEquals(b2.getOwner(), a2);
			assertEquals(b3.getOwner(), null);
		}
}

