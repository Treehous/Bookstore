package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.*;

public class IDatabase {
	static{
		try{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		}catch(Exception e){
			throw new IllegalStateException("Could not load the driver");
		}
	}
	
	private static final int TIMEOUT = 10;

	private interface Query<ReturnType>{
		public ReturnType query(Connection conn) throws SQLException;
	}

	/*
	 * ----------------MAIN QUERY METHODS------------------------------------------------------------
	 */
	public List<Book> queryForAllBooks(){
		try{
			return doQueryLoop(new Query<List<Book>>(){
				@Override
				public List<Book> query(Connection conn) throws SQLException{
					List<Book> books = null;
					PreparedStatement stmt = null;
					ResultSet set = null;
					try{
						stmt = conn.prepareStatement(
								"SELECT * FROM books" );
						set = stmt.executeQuery();
						books = getAllBooksFromResultSet(conn,set);
					}finally{
						DBUtil.closeQuietly(stmt);
						DBUtil.closeQuietly(set);
					}
					return books;
				}
			});
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public List<Book> queryForBooksByTitle(String title){
		try{
			return doQueryLoop(new Query<List<Book>>(){
				@Override
				public List<Book> query(Connection conn) throws SQLException{
					List<Book> books = null;
					PreparedStatement stmt = null;
					ResultSet set = null;
					try{
						stmt = conn.prepareStatement(
								"SELECT * FROM books "
								+ " WHERE title = ?" );
						stmt.setString(1, title);
						set = stmt.executeQuery();
						books = getAllBooksFromResultSet(conn,set);
					}finally{
						DBUtil.closeQuietly(stmt);
						DBUtil.closeQuietly(set);
					}
					return books;
				}
			});
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public List<Book> queryForBooksByAuthorLastName(String lastname){
		try{
			return doQueryLoop(new Query<List<Book>>(){
				@Override
				public List<Book> query(Connection conn) throws SQLException{
					List<Book> books = new ArrayList<Book>();
					PreparedStatement stmt = null;
					PreparedStatement stmt1 = null;
					PreparedStatement stmt2 = null;
					ResultSet set = null;
					ResultSet set1 = null;
					ResultSet set2 = null;
					try{
						stmt = conn.prepareStatement(
								"Select author_id FROM authors "
								+ " WHERE author_lastname=?" );
						stmt.setString(1, lastname);
						set = stmt.executeQuery();
						
						if(set.next()){
							int authorId = set.getInt(1);
							stmt1 = conn.prepareStatement(
									"SELECT book_id FROM authored "
									+" WHERE author_id=?");
							stmt1.setInt(1, authorId);
							set1 = stmt1.executeQuery();
							
							stmt2 = conn.prepareStatement(
									"SELECT * FROM books "
									+ " WHERE book_id = ?");
							
							while(set1.next()){
								stmt2.setInt(1, set1.getInt(1));
								set2 = stmt2.executeQuery();
								books.addAll(getAllBooksFromResultSet(conn,set2));
							}	
						}
					}finally{
						DBUtil.closeQuietly(stmt);
						DBUtil.closeQuietly(stmt1);
						DBUtil.closeQuietly(stmt2);
						DBUtil.closeQuietly(set);
						DBUtil.closeQuietly(set1);
						DBUtil.closeQuietly(set2);
					}
					if(books.isEmpty()){
						return null;
					}
					else{
						return books;
					}
				}
			});
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public List<Book> queryForBooksByISBN(String isbn){
		try{
			return doQueryLoop(new Query<List<Book>>(){
				@Override
				public List<Book> query(Connection conn) throws SQLException{
					List<Book> books = null;
					PreparedStatement stmt = null;
					ResultSet set = null;
					
					try{
						stmt = conn.prepareStatement(
								"SELECT * FROM books "
								+ "WHERE isbn=?" );
						stmt.setString(1, isbn);
						set = stmt.executeQuery();
						books = getAllBooksFromResultSet(conn,set);
					}finally{
						DBUtil.closeQuietly(stmt);
						DBUtil.closeQuietly(set);
					}
					return books;
				}
			});
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return null;
		}
	}

	public boolean insertBookIntoDatabase(Book book) {
		try{
			return doQueryLoop(new Query<Boolean>(){
				@Override
				public Boolean query(Connection conn) throws SQLException{
					boolean success = true;
					int[] authorIds = new int[book.getAuthors().size()];
					int index = 0;

					for(Author a: book.getAuthors()){
						authorIds[index++] = insertAuthor(conn, a);
					}

					int bookId = insertBook(conn, book);

					for(int authorId: authorIds){
						if(!insertAuthored(conn,authorId,bookId)){
							success = false;
						}
					}
					return success;
				}
			});
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		}
	}
	/*
	 * -----------------------HELPER METHODS FOR STREAMLINING SQL QUERIES----------------------------------------------------
	 */
	
	private List<Book> getAllBooksFromResultSet(Connection conn,ResultSet set) throws SQLException{
		//from a result set selected from the books table returning all rows compile a list of books
		ArrayList<Book> books = new ArrayList<Book>();
		if(set != null){
			while(set.next()){
				Book book = new Book();
				int index = 1;
				int bookId = set.getInt(index++);
				inflateBook(set,book,index);
				book.setAuthor(getAuthorsFromBookId(conn,bookId));
				books.add(book);
			}
		}
		return books;
	}
	
	private List<Author> getAuthorsFromBookId(Connection conn, int bookId) throws SQLException{
		List<Author> authors = new ArrayList<Author>();
		PreparedStatement stmt1 = null;
		ResultSet set1 = null;
		
		try{
			stmt1 = conn.prepareStatement(
					"SELECT author_id FROM authored "
					+ "WHERE book_id=?");
			stmt1.setInt(1, bookId);
			set1 = stmt1.executeQuery();
			
			while(set1.next()){
				Author author = getAuthorFromAuthorId(conn,set1.getInt(1));
				if(author != null){
					authors.add(author);
				}
			}
		}finally{
			DBUtil.closeQuietly(stmt1);
			DBUtil.closeQuietly(set1);
		}
		return authors;
	}
	
	private Author getAuthorFromAuthorId(Connection conn, int authorId) throws SQLException{
		Author author = null;
		PreparedStatement stmt1 = null;
		ResultSet set1 = null;
		
		try{
			stmt1 = conn.prepareStatement(
					"SELECT author_lastname, author_firstname FROM authors "
					+ "WHERE author_id=?");
			stmt1.setInt(1, authorId);
			set1 = stmt1.executeQuery();
			
			if(set1.next()){
				author = new Author();
				inflateAuthor(set1,author,1);
			}
		}finally{
			DBUtil.closeQuietly(stmt1);
			DBUtil.closeQuietly(set1);
		}
		return author;
	}
	
	private int insertAuthor(Connection conn, Author author) throws SQLException{
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;

		ResultSet set1 = null;
		ResultSet set3 = null;

		int authorId = -1;
		try{
			//TODO: change to allow for multiple authors
			stmt1 = conn.prepareStatement(
					"SELECT author_id FROM authors "
							+ "WHERE author_firstname = ? "
							+ "AND author_lastname = ? "
					);
			stmt1.setString(1, author.getAuthorsFirstName());
			stmt1.setString(2, author.getAuthorsLastName());
			set1 = stmt1.executeQuery();

			if(set1.next()){
				authorId = set1.getInt(1);
			}

			if(authorId <= 0){
				stmt2 = conn.prepareStatement(
						"INSERT INTO authors (author_lastname, author_firstname) "
								+ "VALUES (?,?)");
				stmt2.setString(1, author.getAuthorsLastName());
				stmt2.setString(2, author.getAuthorsFirstName());
				stmt2.executeUpdate();

				stmt3 = conn.prepareStatement(
						"SELECT author_id FROM authors "
								+ "WHERE author_firstname = ? "
								+ "AND author_lastname = ? "
						);
				stmt3.setString(1, author.getAuthorsFirstName());
				stmt3.setString(2, author.getAuthorsLastName());
				set3 = stmt3.executeQuery();

				//TODO: check  set3
				if(set3.next()){
					authorId = set3.getInt(1);
				}
			}
		}finally{
			DBUtil.closeQuietly(stmt1);
			DBUtil.closeQuietly(stmt2);
			DBUtil.closeQuietly(stmt3);
			DBUtil.closeQuietly(set1);
			DBUtil.closeQuietly(set3);
		}
		return authorId;
	}

	private int insertBook(Connection conn, Book book) throws SQLException{
		PreparedStatement stmt4 = null;
		PreparedStatement stmt5 = null;
		PreparedStatement stmt6 = null;

		ResultSet set4 = null;
		ResultSet set6 = null;

		int bookId = -1;
		try{
			stmt4 = conn.prepareStatement(
					"SELECT book_id FROM books "
							+ "WHERE title = ? "
							+ "AND isbn = ? "
					);
			stmt4.setString(1, book.getTitle());
			stmt4.setString(2, book.getIsbn());
			set4 = stmt4.executeQuery();

			if(set4.next()){
				bookId = set4.getInt(1);
			}

			if(bookId <= 0){
				stmt5 = conn.prepareStatement(
						"INSERT INTO books (title, isbn) "+
						"VALUES (?,?) ");

				stmt5.setString(1, book.getTitle());
				stmt5.setString(2, book.getIsbn());
				stmt5.executeUpdate();

				stmt6 = conn.prepareStatement(
						"SELECT book_id FROM books "
								+ "WHERE title = ? "
								+ "AND isbn = ? ");

				stmt6.setString(1, book.getTitle());
				stmt6.setString(2, book.getIsbn());
				set6 = stmt6.executeQuery();

				if(set6.next()){
					bookId = set6.getInt(1);
				}
			}
		}finally{
			DBUtil.closeQuietly(stmt4);
			DBUtil.closeQuietly(stmt5);
			DBUtil.closeQuietly(stmt6);

			DBUtil.closeQuietly(set4);
			DBUtil.closeQuietly(set6);
		}
		return bookId;
	}

	private boolean insertAuthored(Connection conn, int authorId, int bookId) throws SQLException{
		PreparedStatement stmt = null;
		PreparedStatement stmt7 = null;
		ResultSet set = null;
		boolean success = false;
		
		try{
			if(authorId > 0 && bookId > 0){
				stmt = conn.prepareStatement(
						"SELECT * FROM authored "
						+ "WHERE book_id = ? " 
						+ "AND author_id = ? ");
				stmt.setInt(1, bookId);
				stmt.setInt(2,authorId);
				set = stmt.executeQuery();
				
				if(!set.next()){
					stmt7 = conn.prepareStatement(
						"INSERT INTO authored(author_id, book_id) "
								+ "VALUES (?,?) ");

					stmt7.setInt(1, authorId);
					stmt7.setInt(2, bookId);
					stmt7.executeUpdate();
				}
				success = true;
			}
		}finally{
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(stmt7);
			DBUtil.closeQuietly(set);
		}
		return success;
	}
	
	private int inflateAuthor(ResultSet set, Author author, int index) throws SQLException{
		String last = set.getString(index++);
		String first = set.getString(index++);
		author.setAuthorName(first, last);
		return index;
	}

	private int inflateBook(ResultSet set, Book book, int index) throws SQLException{
		book.setTitle(set.getString(index++));
		book.setISBN(set.getString(index++));
		return index;
	}
	
	/*
	 * ------------------------------------CORE DATABASE FUNCTIONALITY METHODS------------------------------------------------------------
	 */
	
	private Connection connect() {
		Connection conn = null;
		try{
			conn =  DriverManager.getConnection("jdbc:derby:../database/bookstore.db;create=true");	
			conn.setAutoCommit(false);
		} catch(SQLException e){
			System.out.println(e.getSQLState());
		}
		return conn;
	}

	private<ReturnType> ReturnType doQueryLoop(Query<ReturnType> query) throws SQLException{
		Connection conn = connect();

		ReturnType ret = null;
		int times = 0;
		boolean done = false;
		try{
			while(!done && times < TIMEOUT){
				try{
					ret = query.query(conn);
					conn.commit();
					done = true;
				}catch(SQLException e){
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						times++;
					} else {
						throw e;
					}
				}
			}

			if (!done) {
				throw new SQLException("Query Failed, TIMEOUT. ");
			}
			return ret;
		}finally{
			DBUtil.closeQuietly(conn);
		}
	}
	
	/*
	 * --------------------------STATIC METHODS FOR MODIFING THE DATABASE OUTSIDE OF THE WEB APP------------------------------------
	 */
	private boolean createTables(Connection conn){
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		PreparedStatement stmt4 = null;
		PreparedStatement stmt5 = null;
		try {
			stmt1 = conn.prepareStatement(
					"CREATE TABLE authors (" +
							"	author_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"	author_lastname varchar(40)," +
							"	author_firstname varchar(40)" +
							")"
					);
			stmt1.execute();

			stmt2 = conn.prepareStatement(
					"CREATE TABLE books (" +
							"	book_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	title varchar(50)," +
							"	isbn varchar(20)" +
							")"
					);
			stmt2.execute();

			stmt3 = conn.prepareStatement(
					"CREATE TABLE authored( "+
							"  author_id integer,"+
							"  book_id integer"+
							")"
					);
			
			stmt3.execute();

			stmt4 = conn.prepareStatement(
					"CREATE TABLE accounts ("+
							" user_id integer primary key "+
							"     generated always as identity (start with 1, increment by 1), "
							+" username varchar(20), "
							+" password varchar(20), "
							+" login_id integer, "
							+" name varchar(30),"
							+" email varchar(30), "
							+" phone_number varchar(30)"
							+")"	
					);
			stmt4.execute();
			
			stmt5 = conn.prepareStatement(
					"CREATE TABLE books_for_sale_by_user ("
							+ " user_id integer, "
							+ " book_id integer, "
							+ " book_price varchar(6))");
			stmt5.execute();

			conn.commit();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}finally{
			DBUtil.closeQuietly(stmt1);
			DBUtil.closeQuietly(stmt2);
			DBUtil.closeQuietly(stmt3);
			DBUtil.closeQuietly(stmt4);
		}
		return true;
	}

	private boolean dropTable(Connection conn, String table){
		PreparedStatement stmt1 = null;

		try{
			stmt1 = conn.prepareStatement(
					"DROP TABLE "+ table + " ");

			stmt1.executeUpdate();
			conn.commit();
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		}finally{
			DBUtil.closeQuietly(stmt1);
		}
		return true;
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		System.out.println("----Loading Database Driver---- ");
		IDatabase db = DatabaseProvider.getDatabase();

		System.out.println("----Connecting to Database---- ");
		Connection conn = db.connect();

		System.out.println("(C)reate table or (D)rop tables: ");
		Scanner in = new Scanner(System.in);
		if(in.nextLine().toUpperCase().equals("C")){
			System.out.println("----Creating Tables---- ");
			if(db.createTables(conn)){
				System.out.println("----Successfully Created Tables---- ");
			}

			else{
				System.out.println("----Failed to Create Tables---- ");
			}
		}
		else{
			boolean done = false;
			while(!done){
				System.out.print("Enter name of table to be dropped: ");
				in = new Scanner(System.in);
				String table = in.nextLine();

				System.out.println("----Preparing to Drop :"+table.toUpperCase()+"---- ");
				if(db.dropTable(conn,table)){
					System.out.println("----Successfully Dropped :"+table.toUpperCase()+"---- ");
				}
				else{
					System.out.println("----Failed To Drop Table---- ");
				}

				in = new Scanner(System.in);
				System.out.println("Drop another table? (Y/n): ");
				if(in.nextLine().toUpperCase().equals("N"))
					done = true;
			}
		}
		in.close();
		DBUtil.closeQuietly(conn);
	}
}
