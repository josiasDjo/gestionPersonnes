package gestionPersonnes.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection con;
    private ConnectionDb connect;
    
    public DatabaseConnection(ConnectionDb connect) {
        this.connect = connect;
        this.con = null;
    }
    
    public DatabaseConnection() {
        this.connect = new ConnectionDb("localhost", "persondb", "root", "", 3306, 1);
        this.con = null;
    }
    
    public Connection getConnection() throws SQLException {
        if(con == null || con.isClosed()) {
            con = DriverManager.getConnection(connect.toString());
        }
        return con;
    }
    
    public void closeConnection() throws SQLException {
        if(con != null && !con.isClosed()) {
            con.close();
            con = null;
        }
    }
    
    public boolean isConnected() {
        try {
            if(con != null && !con.isClosed()) {
                return true;
            } else {
                return false;
            }
        } catch(SQLException e) {
            return false;
        }
    }
}
