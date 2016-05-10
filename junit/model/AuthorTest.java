package model;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import src.Author;

public class AuthorTest {
	Author a1, a2, a3, a4, a5;
	@Before
	public void setUp(){
		this.a1 = new Author("Donald J. Trump");
		this.a2 = new Author("Dank Memes");
		this.a3 = new Author("M1lk Man");
		this.a4 = new Author("");
	}
	
	@Test
	public void getAuthorsFullNameTest(){
		assertEquals(a1.getAuthorsFullName(),"J. Trump, Donald");
		assertEquals(a2.getAuthorsFullName(),"Memes, Dank");
		assertEquals(a3.getAuthorsFullName(),"Man, M1lk");
		assertEquals(a4.getAuthorsFullName(), "");
	}
	
	@Test
	public void getAuthorsFirstNameTest(){
		assertEquals(a1.getAuthorsFirstName(),"Donald");
		assertEquals(a2.getAuthorsFirstName(),"Dank");
		assertEquals(a3.getAuthorsFirstName(),"M1lk");
		assertEquals(a4.getAuthorsFirstName(), "");
	}
	
	@Test
	public void getAuthorsLastNameTest(){
		assertEquals(a1.getAuthorsLastName(),"J. Trump");
		assertEquals(a2.getAuthorsLastName(),"Memes");
		assertEquals(a3.getAuthorsLastName(),"Man");
		assertEquals(a4.getAuthorsLastName(),"");
	}
}