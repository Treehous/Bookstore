package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import src.Book;

public class IDatabase {
	static{
		try{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		}catch(Exception e){
			throw new IllegalStateException("Could not load to driver");
		}
	}

	private Connection connect() throws SQLException {
		return DriverManager.getConnection("jdbc:derby:database/bookstore.db;create=true");		
	}

	public void insertBookIntoDatabase(Book book) throws SQLException{
		Connection con = this.connect();

		DBUtil.closeQuietly(con);
	}

	private boolean createTables(Connection conn){
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		try {
			stmt1 = conn.prepareStatement(
					"create table authors (" +
							"	author_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"	author_lastname varchar(40)," +
							"	author_firstname varchar(40)" +
							")"
					);
			stmt1.executeUpdate();
	
			stmt2 = conn.prepareStatement(
					"create table books (" +
							"	book_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	author_id integer constraint author_id references authors, " +
							"	title varchar(50)," +
							"	isbn varchar(20)" +
							")"
					);
			stmt2.executeUpdate();

			stmt3 = conn.prepareStatement(
					"create table accounts ("+
							" user_id integer primary key "+
							"     generated always as identity (start with 1, increment by 1), "+
							" username varchar(20), "+
							" password varchar(20)"+
							")"	
					);
			stmt3.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			DBUtil.closeQuietly(stmt1);
			DBUtil.closeQuietly(stmt2);
			DBUtil.closeQuietly(stmt3);
		}
	}
	public static void main(String[] args) throws Exception{
		System.out.println("----Loading Database Driver----");
		IDatabase db = new IDatabase();
		
		System.out.println("----Connecting to Database----");
		Connection conn = db.connect();
		conn.setAutoCommit(false);
		
		System.out.println("----Creating Tables----");
		if(db.createTables(conn)){
			System.out.println("----Tables Created Successfully----");
		}
		
		else{
			System.out.println("----Failed to Create Tables----");
		}
		DBUtil.closeQuietly(conn);
	}
}
