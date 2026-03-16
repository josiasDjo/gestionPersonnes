package com.groupe3.projects.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
public class Utilitaire {

    // MÉTHODE Gérer les IDs 
    public static int genererProchainId(String nomTable) {
        int prochainId = 1;
        try (Connection db = FactoryConnexion.getInstance().getConnection().getConnection()) {
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(id) FROM " + nomTable);
            if (rs.next()) {
                prochainId = rs.getInt(1) + 1;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return prochainId;
    }

    // MÉTHODE Ajouter
    public static int executer(String sql, Object... params) {
        try (Connection db = FactoryConnexion.getInstance().getConnection().getConnection()) {
            PreparedStatement pst = db.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }
            return pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // MÉTHODE  afficher
    public static DefaultTableModel chargerTableau(String sql) {
        DefaultTableModel model = new DefaultTableModel();
        try (Connection db = FactoryConnexion.getInstance().getConnection().getConnection()) {
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            for (int i = 1; i <= colCount; i++) model.addColumn(meta.getColumnName(i));

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= colCount; i++) row.add(rs.getObject(i));
                model.addRow(row);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return model;
    }
}
