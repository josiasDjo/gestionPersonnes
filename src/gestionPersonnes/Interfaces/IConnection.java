package gestionPersonnes.Interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnection {
    Connection getConnection() throws SQLException;
    void closeConnection() throws SQLException;
    boolean isConnected();
}