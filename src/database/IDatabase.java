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
								"SELECT * FROM books " );
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
			System.out.println("queryForAllBooks: "+e.getMessage());
			return null;
		}
	}
	
	public List<Book> queryForAllBooksForSale(){
		try{
			return doQueryLoop(new Query<List<Book>>(){
				@Override
				public List<Book> query(Connection conn) throws SQLException{
					List<Book> books = null;
					PreparedStatement stmt = null;
					ResultSet set = null;
					try{
						stmt = conn.prepareStatement(
								"SELECT books.* FROM books, books_for_sale_by_user"
								+ " WHERE books.book_id = books_for_sale_by_user.book_id " );
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
			System.out.println("queryForAllBooksForSale: "+e.getMessage());
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
										+ " WHERE title LIKE ?" );
						stmt.setString(1, "%"+title+"%");
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
			System.out.println("queryForBooksByTitle: "+e.getMessage());
			return null;
		}
	}

	public List<Book> queryForBooksByAuthor(Author author){
		try{
			return doQueryLoop(new Query<List<Book>>(){
				@Override
				public List<Book> query(Connection conn) throws SQLException{
					List<Book> books = new ArrayList<Book>();
					PreparedStatement stmt = null;
					PreparedStatement stmt1 = null;
					PreparedStatement stmt2 = null;
					PreparedStatement stmt3 = null;
					ResultSet set = null;
					ResultSet set1 = null;
					ResultSet set2 = null;
					ResultSet set3 = null;
					ResultSet set4 = null;
					ResultSet set5 = null;
					try{
						stmt1 = conn.prepareStatement(
								"SELECT book_id FROM authored "
										+" WHERE author_id=?");

						stmt2 = conn.prepareStatement(
								"SELECT * FROM books "
										+ " WHERE book_id = ?");

						if(author.getAuthorsLastName() != null && !author.getAuthorsLastName().equals("")){
							stmt = conn.prepareStatement(
									"Select author_id FROM authors "
											+ " WHERE author_lastname LIKE ?" );
							
							stmt.setString(1, "%"+author.getAuthorsLastName()+"%");
							System.out.println(author.getAuthorsLastName());
							set = stmt.executeQuery();
							if(set.next()){
								int authorId = set.getInt(1);

								stmt1.setInt(1, authorId);
								set1 = stmt1.executeQuery();

								while(set1.next()){
									stmt2.setInt(1, set1.getInt(1));
									set2 = stmt2.executeQuery();
									books.addAll(getAllBooksFromResultSet(conn,set2));
								}	
							}
						}
						if(author.getAuthorsFirstName() != null && !author.getAuthorsFirstName().equals("")){
							stmt3 = conn.prepareStatement(
									"SELECT author_id FROM authors "
											+ " WHERE author_firstname LIKE ?" );
							
							stmt3.setString(1, "%"+author.getAuthorsFirstName()+"%");
							set3 = stmt3.executeQuery();

							if(set3.next()){
								int authorId = set3.getInt(1);

								stmt1.setInt(1, authorId);
								set4 = stmt1.executeQuery();

								while(set4.next()){
									stmt2.setInt(1, set1.getInt(1));
									set5 = stmt2.executeQuery();
									books.addAll(getAllBooksFromResultSet(conn,set5));
								}	
							}
						}
					}finally{
						DBUtil.closeQuietly(stmt);
						DBUtil.closeQuietly(stmt1);
						DBUtil.closeQuietly(stmt2);
						DBUtil.closeQuietly(stmt3);
						DBUtil.closeQuietly(set);
						DBUtil.closeQuietly(set1);
						DBUtil.closeQuietly(set2);
						DBUtil.closeQuietly(set3);
						DBUtil.closeQuietly(set4);
						DBUtil.closeQuietly(set5);
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
			System.out.println("queryForBooksByAuthor: "+e.getMessage());
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
			System.out.println("queryForBooksByISBN: "+e.getMessage());
			return null;
		}
	}

	public String queryForPasswordByUsername(String username){
		try{
			return doQueryLoop(new Query<String>(){
				@Override 
				public String query(Connection conn) throws SQLException{
					String password = null;
					password = getPasswordByUsername(conn,username);
					return password;
				}
			});
		} catch(SQLException e){
			System.out.println("queryForPasswordByUsername: "+e.getMessage());
			return null;
		}
	}
	
	public int queryForLoginIdByUsername(String username){
		try{
			return doQueryLoop(new Query<Integer>(){
				@Override
				public Integer query(Connection conn) throws SQLException{
					PreparedStatement stmt = null;
					ResultSet set = null;
					int loginId = -1;
					try{
						stmt = conn.prepareStatement(
								" SELECT login_id FROM accounts "
								+ " WHERE username = ?");
						stmt.setString(1, username);
						set = stmt.executeQuery();
						
						if(set.next()){
							loginId = set.getInt(1);
						}
					}finally{
						DBUtil.closeQuietly(stmt);
						DBUtil.closeQuietly(set);
					}
					return loginId;
				}
			});
		}catch(SQLException e){
			System.out.println("queryForLoginIdByUsername: "+e.getMessage());
			return -1;
		}
	}
	
	public boolean updateAccountByUsername(String username, Account account){
		try{
			return doQueryLoop(new Query<Boolean>(){
				@Override 
				public Boolean query(Connection conn)throws SQLException{
					if(verifyAccountExistsByUsername(conn,username))
						return updateAccountByUsername(conn, username,account);
					
					else 
						return false;
				}
			});
		}catch(SQLException e){
			System.out.println("updateAccountByUsername: "+e.getMessage());
			return false;
		}
	}
	
	public Account queryForUserAccountByUsername(String username){
		try{
			return doQueryLoop(new Query<Account>(){
				@Override
				public Account query(Connection conn) throws SQLException{
					Account account = null;
					if(verifyAccountExistsByUsername(conn, username)){
						account = getAccountByUsername(conn,username);
					}
					return account;
				}
			});
		}catch(SQLException e){
			System.out.println("queryForUserAccountByUsername: "+e.getMessage());
			return null;
		}
	}
	
	public boolean insertNewAccountIntoDatabase(Account account){
		try{
			return doQueryLoop(new Query<Boolean>(){
				@Override
				public Boolean query(Connection conn) throws SQLException{
					boolean success = false;
					if(!verifyAccountExistsByUsername(conn, account.getUsername())){
						if(insertAccountIntoAccounts(conn,account));
							success = true;
					}
					return success;
				}
			});
		}catch(SQLException e){
			System.out.println("insertNewAccountIntoDatabase: "+e.getMessage());
			return false;
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
			System.out.println("insertBookIntoDatabase: "+e.getMessage());
			return false;
		}
	}
	/*
	 * -----------------------HELPER METHODS FOR STREAMLINING SQL QUERIES----------------------------------------------------
	 */
	
	private boolean updateAccountByUsername(Connection conn, String username, Account account) throws SQLException{
		boolean success = false;
		PreparedStatement stmt = null;
		try{
			stmt = conn.prepareStatement(
					"UPDATE accounts "
					+ " SET username = ?, password = ?, login_id = ?, name = ?, email = ?, phone_number = ?, locked = ? "
					+ " WHERE username = ? ");
			stmt.setString(1, account.getUsername());
			stmt.setString(2, account.getPassword());
			stmt.setInt(3, account.getLoginId());
			stmt.setString(4, account.getName());
			stmt.setString(5, account.getEmail());
			stmt.setString(6, account.getPhoneNumber());
			
			if(account.isLocked())
				stmt.setInt(7, 1);
			else
				stmt.setInt(7, 0);
			
			stmt.setString(8, username);
			stmt.executeUpdate();
			success = true;
		}finally{
			DBUtil.closeQuietly(stmt);
		}
		return success;
	}
	
	private Account getAccountByUsername(Connection conn, String username) throws SQLException{
		Account account = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet set = null;
		ResultSet set2 = null;
		try{
			stmt = conn.prepareStatement(
					" SELECT * FROM accounts "
					+" WHERE username=?");
			stmt.setString(1, username);
			
			set = stmt.executeQuery();
			
			if(set.next()){
				int userId = set.getInt(1);
				account = inflateAccount(set,2);
				
				stmt2 = conn.prepareStatement(
						" SELECT book_id FROM books_for_sale_by_user "
						+ " WHERE user_id = ?");
				stmt2.setInt(1, userId);
			
				set2 = stmt2.executeQuery();
				
				while(set2.next()){
					account.addBookForSale(getBookFromBookId(conn,set2.getInt(1)));
				}
			}
		}finally{
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(stmt2);
			DBUtil.closeQuietly(set);
			DBUtil.closeQuietly(set2);
		}
		return account;
	}
	
	private String getPasswordByUsername(Connection conn,String username) throws SQLException{
		String password = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		try{
			stmt = conn.prepareStatement(
					" SELECT password FROM accounts WHERE username=? ");
			stmt.setString(1,username);
			set = stmt.executeQuery();
			
			if(set.next()){
				password = set.getString(1);
			}
		}finally{
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(set);
		}
		return password;
	}
	
	private boolean insertAccountIntoAccounts(Connection conn, Account account) throws SQLException{
		boolean success = false;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		ResultSet set = null;
		
		try{
			stmt1 = conn.prepareStatement(
					"INSERT INTO accounts (username, password, login_id, name, email, phone_number, locked) "
					+ " VALUES(?,?,?,?,?,?,?)");
			stmt1.setString(1, account.getUsername());
			stmt1.setString(2, account.getPassword());
			stmt1.setInt(3, account.getLoginId());
			stmt1.setString(4, account.getName());
			stmt1.setString(5, account.getEmail());
			stmt1.setString(6, account.getPhoneNumber());
			
			if(account.isLocked())
				stmt1.setInt(7, 1);
			else 
				stmt1.setInt(7, 0);
			
			stmt1.executeUpdate();
			
			stmt2 = conn.prepareStatement(
					"SELECT user_id FROM accounts "
					+ " WHERE username = ?");
			stmt2.setString(1, account.getUsername());
			set = stmt2.executeQuery();
			
			if(set.next()){
				int userId = set.getInt(1);
				stmt3 = conn.prepareStatement(
						" INSERT INTO books_for_sale_by_user (user_id, book_id) "
						+ " VALUES(?,?) ");
				for(Book book: account.getBooksForSale()){
					int bookId = insertBook(conn, book); // watch this statement when running??
					stmt3.setInt(1, userId);
					stmt3.setInt(2, bookId);
					stmt3.executeUpdate();
				}
				success = true;
			}
		}finally{
			DBUtil.closeQuietly(stmt1);
			DBUtil.closeQuietly(stmt2);
			DBUtil.closeQuietly(stmt3);
			DBUtil.closeQuietly(set);
		}
		return success;
	}
	
	private boolean verifyAccountExistsByUsername(Connection conn, String username) throws SQLException{
		boolean registered = false;
		PreparedStatement stmt = null;
		ResultSet set = null;
		
		try{
			stmt = conn.prepareStatement(
					"SELECT * from accounts WHERE username=? ");
			stmt.setString(1, username);
			set = stmt.executeQuery();
			if(set.next()){
				registered = true;
			}
		}finally{
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(set);
		}
		return registered;
	}

	private List<Book> getAllBooksFromResultSet(Connection conn,ResultSet set) throws SQLException{
		//from a result set selected from the books table returning all rows compile a list of books
		ArrayList<Book> books = new ArrayList<Book>();
		if(set != null){
			while(set.next()){
				Book book = null;
				int index = 1;
				int bookId = set.getInt(index++);
				book = inflateBook(set,index);
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

	private Book getBookFromBookId(Connection conn, int bookId) throws SQLException{
		Book book = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		try{
			stmt = conn.prepareStatement(
					"SELECT title, isbn FROM books "
					+ "WHERE book_id=?");
			stmt.setInt(1, bookId);
			set = stmt.executeQuery();
			
			if(set.next()){
				book = inflateBook(set, 1);
				book.setAuthor(getAuthorsFromBookId(conn, bookId));
			}
		}finally{
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(set);
		}
		return book;
	}
	
	private Account inflateAccount(ResultSet set, int index) throws SQLException{
		Account account = new Account();
		account.setUsername(set.getString(index++));
		account.setPassword(set.getString(index++));
		account.setLoginId(set.getInt(index++));
		account.setName(set.getString(index++));
		account.setEmail(set.getString(index++));
		account.setPhoneNumber(set.getString(index++));
		
		if(set.getInt(index)==1)
			account.lock();
		else
			account.unlock();
		
		return account;
	}
	
	private int inflateAuthor(ResultSet set, Author author, int index) throws SQLException{
		String last = set.getString(index++);
		String first = set.getString(index++);
		author.setAuthorName(first, last);
		return index;
	}

	private Book inflateBook(ResultSet set, int index) throws SQLException{
		Book book = new Book();
		book.setTitle(set.getString(index++));
		book.setISBN(set.getString(index++));
		return book;
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
		//Table Names: authors, books, authored, books_for_sale_by_user, accounts
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
							+" phone_number varchar(30), "
							+" locked integer"
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

	private boolean dropTables(Connection conn){
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		PreparedStatement stmt4 = null;
		PreparedStatement stmt5 = null;
		

		try{
			stmt1 = conn.prepareStatement("DROP TABLE books");
			stmt2 = conn.prepareStatement("DROP TABLE authors");
			stmt3 = conn.prepareStatement("DROP TABLE accounts");
			stmt4 = conn.prepareStatement("DROP TABLE authored");
			stmt5 = conn.prepareStatement("DROP TABLE books_for_sale_by_user");
			
			stmt1.executeUpdate();
			stmt2.executeUpdate();
			stmt3.executeUpdate();
			stmt4.executeUpdate();
			stmt5.executeUpdate();
						
			conn.commit();
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		}finally{
			DBUtil.closeQuietly(stmt1);
		}
		return true;
	}

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
			System.out.println("----Preparing to Drop Tables---- ");
			if(db.dropTables(conn)){
				System.out.println("----Successfully Dropped Tables---- ");
			}
			else{
				System.out.println("----Failed To Drop Table---- ");
			}
		}
		in.close();
		DBUtil.closeQuietly(conn);
	}
}
