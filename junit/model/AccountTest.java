package model;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import src.Account;
import src.Book;


public class AccountTest {
	Account a1, a2, a3;
	
	Book b1;
	
    @Before
	public void setUp(){
    List<String> l1 = new ArrayList<String>();
	l1.add("Dr. Suess");
	
    this.b1 = new Book("Green Eggs and Ham", l1, "9780007180561","The best book!" );
    
    List<Book> l2 = new ArrayList<Book>();
	l2.add(0, b1);
	
	this.a1 = new Account("user", "pass", 1 ,"joe", "guy@mail.com", "1234567890", false, l2);
		
		
	}
	
	@Test
	public void getUsernameTest(){
		assertEquals(a1.getUsername(),"user");

	}
	
	@Test
	public void getPasswordTest(){
		assertEquals(a1.getPassword(),"pass");

	}
	@Test
	public void getLoginIdTest(){
		assertEquals(a1.getLoginId(), 1);

	}
	
	@Test
	public void getNameTest(){
		assertEquals(a1.getName(),"joe");

	}
	@Test
	public void getEmailTest(){
		assertEquals(a1.getEmail(),"guy@mail.com");

	}
	@Test
	public void getPhoneNumberTest(){
		assertEquals(a1.getPhoneNumber(),"1234567890");

	}
	@Test
	public void isLockedTest(){
		assertEquals(a1.isLocked(),false);

	}
	@Test
	public void getBooksForSaleTest(){
		
		List<String> l1 = new ArrayList<String>();
		l1.add("Dr. Suess");
		
	    this.b1 = new Book("Green Eggs and Ham", l1, "9780007180561","The best book!" );
	    
	    List<Book> l2 = new ArrayList<Book>();
		l2.add(0, b1);
		
		
		
		assertEquals(a1.getBooksForSale(), l2);

	}
	

}
