import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class InventoryAction {
	
	public ResultSet getInventoryData() throws Exception{
		Connection connection = DBConnection.getConnection();
		Statement s = connection.createStatement();
		ResultSet rs = s.executeQuery("Select * from Inventory");
		return rs;
		
	}
	

}
