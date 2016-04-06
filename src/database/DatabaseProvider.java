package database;

public class DatabaseProvider {
	private static  IDatabase databaseInstance;
	
	public static void setDatabase(IDatabase database){
		databaseInstance = database;
	}
	
	public static IDatabase getDatabase() throws Exception{
		if(databaseInstance == null){
			databaseInstance = new IDatabase();
		}
		
		return databaseInstance;
	}
	
}
