package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import src.Book;

public class IDatabase {
	static{
		try{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		}catch(Exception e){
			throw new IllegalStateException("Could not load to driver");
		}
	}
	private static final int TIMEOUT = 10;
	
	private interface Query<ReturnType>{
		public ReturnType query(Connection conn) throws SQLException;
	}
	
	private interface Insert<Type>{
		public Type insert(Connection conn) throws SQLException;
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

	public void insertBookIntoDatabase(Book book) throws SQLException{
		Connection con = this.connect();

		DBUtil.closeQuietly(con);
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
			stmt1.executeUpdate();

			stmt2 = conn.prepareStatement(
					"CREATE TABLE books (" +
							"	book_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	title varchar(50)," +
							"	isbn varchar(20)" +
							")"
					);
			stmt2.executeUpdate();
			
			stmt3 = conn.prepareStatement(
					"CREATE TABLE authored( "+
							"  author_id integer,"+
							"  book_id integer"+
							")"
					);
			
			stmt3.executeUpdate();
			
			stmt4 = conn.prepareStatement(
					"CREATE TABLE accounts ("+
							" user_id integer primary key "+
							"     generated always as identity (start with 1, increment by 1), "+
							" username varchar(20), "+
							" password varchar(20)"+
							")"	
					);
			stmt4.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			DBUtil.closeQuietly(stmt1);
			DBUtil.closeQuietly(stmt2);
			DBUtil.closeQuietly(stmt3);
			DBUtil.closeQuietly(stmt4);
		}
		return true;
	}

	private boolean dropTable(Connection conn){
		System.out.print("Enter name of table to be dropped: ");
		Scanner in = new Scanner(System.in);
		String table = in.nextLine();
		in.close();
		
		PreparedStatement stmt1 = null;
		
		try{
			stmt1 = conn.prepareStatement(
					"DROP TABLE "+ table + " ");
			
			stmt1.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}finally{
			DBUtil.closeQuietly(stmt1);
		}
		return true;
	}
	public static void main(String[] args) {
		System.out.println("----Loading Database Driver----");
		IDatabase db = DatabaseProvider.getDatabase();

		System.out.println("----Connecting to Database----");
		Connection conn = db.connect();

		System.out.println("(C)reate table or (D)rop tables: ");
		Scanner in = new Scanner(System.in);
		if(in.nextLine().toUpperCase().equals("C")){
			System.out.println("----Creating Tables----");
			if(db.createTables(conn)){
				System.out.println("----Successfully Created Tables----");
			}

			else{
				System.out.println("----Failed to Create Tables----");
			}
		}
		else{
			boolean done = false;
			while(!done){
				System.out.println("----Preparing to Drop Tables----");
				if(db.dropTable(conn)){
					System.out.println("----Successfully Dropped Table----");
				}
				else{
					System.out.println("----Failed To Drop Table----");
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
