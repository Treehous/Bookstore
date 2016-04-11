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
	public List<Book> getBookByISBN(String isbn){
		try{
			return doQueryLoop(new Query<List<Book>>(){
				@Override
				public List<Book> query(Connection conn) throws SQLException{
					ArrayList<Book> books = new ArrayList<Book>();
						PreparedStatement stmt1 = null;
						PreparedStatement stmt2 = null;
						PreparedStatement stmt3 = null;
						
						stmt1 = conn.prepareStatement(
								"SELECT * FROM books "+
									"WHERE isbn=?");
						
						stmt2 = conn.prepareStatement(
								"SELECT author_id FROM authored "
								+"WHERE book_id=?");
						
						stmt3 = conn.prepareStatement(
								"SELECT author_firstname, author_lastname "
								+ "FROM authors "
								+ "WHERE author_id=? ");
						
						stmt1.setString(1, isbn);
						ResultSet set1 = stmt1.executeQuery();
						
						int index1 = 1;
						while(set1.next()){
							int bookId = set1.getInt(index1++);
							Book book = new Book();
							index1 = inflateBook(set1, book, index1);
							
							List<Author> authors = new ArrayList<Author>();
							stmt2.setInt(1, bookId);
							ResultSet set2 = stmt2.executeQuery();
							
							int index2 = 1;
							while(set2.next()){
								int authorId = set2.getInt(index2++);
								
								stmt3.setInt(1, authorId);
								ResultSet set3 = stmt3.executeQuery();
								
								Author author = new Author();
								if(set3.next()){
									inflateAuthor(set3,author,1);
								}
								
								authors.add(author);
							}
							
							book.setAuthor(authors);
							books.add(book);
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
					PreparedStatement stmt1 = null;
					PreparedStatement stmt2 = null;
					PreparedStatement stmt3 = null;

					//TODO: Book check = bookByISBN(string isbn);
					stmt1 = conn.prepareStatement(
							"INSERT INTO books(title, isbn)" +
									"VALUES (?,?)"
							);
					stmt1.setString(1,book.getTitle());
					stmt1.setString(2, book.getISBN());
					stmt1.executeUpdate();

					//TODO: check for authors already added
					stmt2 = conn.prepareStatement(
							"INSERT INTO authors (authors_lastname, authors_firstname)" +
									"VALUES (?,?)"
							);
					for(Author a: book.getAuthors()){
						stmt2.setString(1, a.getAuthorsLastName());
						stmt2.setString(2, a.getAuthorsFirstName());
						stmt2.addBatch();
					}

					stmt2.executeBatch();

					//TODO: get author id and book id then fill authored table
					stmt3 = conn.prepareStatement(
							""
							);

					stmt3.executeUpdate();
					return true;
				}
			});
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		}
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
	
	private Connection connect() {
		Connection conn = null;
		try{
			conn =  DriverManager.getConnection("jdbc:derby:database/bookstore.db;create=true");	
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

	private boolean createTables(Connection conn){
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		PreparedStatement stmt4 = null;
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
							"     generated always as identity (start with 1, increment by 1), "+
							" username varchar(20), "+
							" password varchar(20)"+
							")"	
					);
			stmt4.execute();

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
