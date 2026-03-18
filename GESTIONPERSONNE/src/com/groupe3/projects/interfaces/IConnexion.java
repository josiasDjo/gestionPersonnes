package com.groupe3.projects.interfaces;
import java.sql.Connection;
import java.sql.SQLException;

public interface IConnexion {
    Connection getConnection() throws SQLException;
}
