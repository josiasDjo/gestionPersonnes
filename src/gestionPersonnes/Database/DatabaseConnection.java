package gestionPersonnes.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
	private static Connection con = null;
	private static ConnectionDb connect;
	
	private DatabaseConnection() {
	}

	public static Connection getConnection() throws SQLException {
		connect = new ConnectionDb("localhost", "persondb", "root", "", 3306, 1);
		if(con == null) {
			con = DriverManager.getConnection(connect.toString());
		}
		return con;
	}
}
