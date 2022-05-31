package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	static Connection conn;
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	static Connection makeConnection() throws SQLException {
		String connectionURL = "jdbc:postgresql://my-teamproject1-db.crv9kmc3fltq.us-east-1.rds.amazonaws.com:5432/teamp1";
		String username = "user1";
		String password = "rootroot";
		if(conn == null) {
			conn = DriverManager.getConnection(connectionURL, username, password);
		}
		return conn;
	}
}
