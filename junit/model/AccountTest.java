package model;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import src.Account;
import src.Book;
import src.BookForSale;


public class AccountTest {
	BookForSale b1, b2, b3;
	Book book1, book2, book3;
	Account a1, a2, a3;
	
    @Before
	public void setUp(){
    	//---------Same setup as BooksForSaleTest (all components needed)---------------------------------------------------------------
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
	    this.a2 = new Account("PLynn17", "def456", 2, "PattyBoy", "Patrick.Lynn1994@gmail.com", "(570)-259-4444", true, x2);
	    this.a3 = new Account("KTurowsk420", "ghi789", 3, "KazBlaster", "Kazimir.Turowski1994@gmail.com", "(570)-259-3333", false, null);
	    
		this.b1 = new BookForSale(book1, "$4.20", a1);
		this.b2 = new BookForSale(book2, "1", a2);
		this.b3 = new BookForSale(null, null, null);
		//---------Same setup as BooksForSaleTest (all components needed)----------------------------------------------------------------
	}
	
	@Test
	public void getUsernameTest(){
		assertEquals(a1.getUsername(),"LoganH24");
		assertEquals(a2.getUsername(),"PLynn17");
		assertEquals(a3.getUsername(),"KTurowsk420");
	}
	
	@Test
	public void getPasswordTest(){
		assertEquals(a1.getPassword(),"abc123");
		assertEquals(a2.getPassword(),"def456");
		assertEquals(a3.getPassword(),"ghi789");

	}
	
	@Test
	public void getLoginIdTest(){
		assertEquals(a1.getLoginId(), 1);
		assertEquals(a2.getLoginId(), 2);
		assertEquals(a3.getLoginId(), 3);
	}
	
	@Test
	public void getNameTest(){
		assertEquals(a1.getName(),"LogeyBear");
		assertEquals(a2.getName(),"PattyBoy");
		assertEquals(a3.getName(),"KazBlaster");
	}
	
	@Test
	public void getEmailTest(){
		assertEquals(a1.getEmail(),"Logan.Harris1995@gmail.com");
		assertEquals(a2.getEmail(),"Patrick.Lynn1994@gmail.com");
		assertEquals(a3.getEmail(),"Kazimir.Turowski1994@gmail.com");
	}
	
	@Test
	public void getPhoneNumberTest(){
		assertEquals(a1.getPhoneNumber(),"(570)-259-5555");
		assertEquals(a2.getPhoneNumber(),"(570)-259-4444");
		assertEquals(a3.getPhoneNumber(),"(570)-259-3333");
	}
	
	@Test
	public void isLockedTest(){
		assertEquals(a1.isLocked(),false);
		assertEquals(a2.isLocked(),true);
		assertEquals(a3.isLocked(),false);
	}
	
	//No way to test on the getBooksForSaleTest() backend. Account requires BookForSale, BookForSale requires Account
	/*@Test
	public void getBooksForSaleTest(){
		assertEquals(a1.getBooksForSale(), null);
		assertEquals(a2.getBooksForSale(), null);
		assertEquals(a3.getBooksForSale(), null);
	}*/
}