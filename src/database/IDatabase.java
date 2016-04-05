package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class IDatabase {
 static{
	 try{
		 Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
	 }catch(Exception e){
		 throw new IllegalStateException("Could not connect to database");
	 }
 }
 
 	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:database/bookstore.db;create=true");		
		
		conn.setAutoCommit(false);
		return conn;
 	}
 	
}
