package database;

public class DatabaseProvider {
	private static  IDatabase databaseInstance;
	
	public static void setDatabase(IDatabase database){
		DatabaseProvider.databaseInstance = database;
	}
	
	public static IDatabase getDatabase() throws Exception{
		if(DatabaseProvider.databaseInstance == null){
			//throw new Exception("---No database assigned---");
			DatabaseProvider.databaseInstance = new IDatabase();
			return DatabaseProvider.databaseInstance;
		}
		
		return DatabaseProvider.databaseInstance;
	}
	
}
