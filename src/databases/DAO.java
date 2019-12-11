package databases;

public abstract class DAO{
	
	java.sql.Connection conn; 
	
    public DAO() {
		try  {
			this.conn = DatabaseUtil.connect();
		} catch (Exception e) { 
			//this is called thus it isn't conencting
			this.conn = null;
		}
    }
    
    public DAO(java.sql.Connection conn) {
    	this.conn = conn;
    }
}
