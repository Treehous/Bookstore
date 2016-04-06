package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import src.Book;

public class IDatabase {
	static{
		try{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		}catch(Exception e){
			throw new IllegalStateException("Could not connect to database");
		}
	}
 
 	private Connection connect() throws SQLException {
		return DriverManager.getConnection("jdbc:derby:database/bookstore.db;create=true");		
 	}
 	
 	public boolean insertBookIntoDatabase(Book book) throws SQLException{
 		Connection con = this.connect();
 		DBUtil.closeQuietly(con);
 		return true;
 	}
 	
}
