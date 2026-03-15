package gestionPersonnes.Class;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import gestionPersonnes.Database.DatabaseConnection;
import gestionPersonnes.Interfaces.IAdresse;

public class Adresse implements IAdresse {
    public int id;
    public String avenue;
    public String numero;
    public String commune;
    public String ville;
    public int idPersonne;
    public Adresse() {
    }
    public Adresse(int id, String avenue, String numero, String commune, String ville, int idPersonne) {
        this.id = id;
        this.avenue = avenue;
        this.numero = numero;
        this.commune = commune;
        this.ville = ville;
        this.idPersonne = idPersonne;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getAvenue() {
        return avenue;
    }
    public void setAvenue(String avenue) {
        this.avenue = avenue;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getCommune() {
        return commune;
    }
    public void setCommune(String commune) {
        this.commune = commune;
    }
    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }
    public int getIdPersonne() {
        return idPersonne;
    }
    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }
    @Override
    public int save() throws SQLException {
        String sql = "{call sp_insert_adresse(?, ?, ?, ?, ?, ?)}";
      
        DatabaseConnection dbConnection = new DatabaseConnection();

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {

            ps.setInt(1, this.getLastId());
            ps.setString(2, this.avenue);
            ps.setString(3, this.numero);
            ps.setString(4, this.commune);
            ps.setString(5, this.ville);
            ps.setInt(6, this.idPersonne);

            int result = ps.executeUpdate();

            dbConnection.closeConnection();

            return result;
        }
    }

    @Override
    public int update() throws SQLException {

        String sql = "{call sp_update_adresse(?, ?, ?, ?, ?, ?)}";

        DatabaseConnection dbConnection = new DatabaseConnection();

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {

            ps.setString(1, this.avenue);
            ps.setString(2, this.numero);
            ps.setString(3, this.commune);
            ps.setString(4, this.ville);
            ps.setInt(5, this.idPersonne);
            ps.setInt(6, this.id);

            int result = ps.executeUpdate();

            if (result < 1) {
                throw new IllegalArgumentException(String.format("Invalid ID provided: '%s'.", this.id));
            }

            dbConnection.closeConnection();

            return result;

        } catch (SQLException e) {

            System.out.println("Erreur lors de la modification : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public int delete() throws SQLException {

        String sql = "{call sp_delete_adresse(?)}";

        DatabaseConnection dbConnection = new DatabaseConnection();

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {

            ps.setInt(1, this.id);

            int result = ps.executeUpdate();

            if (result < 1) {
                throw new IllegalArgumentException(String.format("Invalid ID provided: '%s'.", this.id));
            }

            return result;
        }
    }

    @Override
    public List<IAdresse> listAdresse() throws SQLException {

        String sql = "{call sp_list_adresse()}";

        List<IAdresse> lstAdresse = new ArrayList<>();

        DatabaseConnection dbConnection = new DatabaseConnection();

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    IAdresse adr = new Adresse();

                    ((Adresse) adr).setId(rs.getInt("id"));
                    ((Adresse) adr).setAvenue(rs.getString("avenue"));
                    ((Adresse) adr).setNumero(rs.getString("numero"));
                    ((Adresse) adr).setCommune(rs.getString("commune"));
                    ((Adresse) adr).setVille(rs.getString("ville"));
                    ((Adresse) adr).setIdPersonne(rs.getInt("idPersonne"));

                    lstAdresse.add(adr);
                }
            }

            return lstAdresse;
        }
    }

    @Override
    public IAdresse adresseGet(int id) throws SQLException {

        String sql = "{call sp_get_adresse(?)}";

        IAdresse adr = new Adresse();

        ((Adresse) adr).setId(id);

        DatabaseConnection dbConnection = new DatabaseConnection();

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    ((Adresse) adr).setAvenue(rs.getString("avenue"));
                    ((Adresse) adr).setNumero(rs.getString("numero"));
                    ((Adresse) adr).setCommune(rs.getString("commune"));
                    ((Adresse) adr).setVille(rs.getString("ville"));
                    ((Adresse) adr).setIdPersonne(rs.getInt("idPersonne"));
                }
            }

            return adr;
        }
    }

    @Override
    public int getLastId() throws SQLException {

        int lastID = 0;

        String sql = "SELECT MAX(id) AS lastId FROM adresse";

        DatabaseConnection dbConnection = new DatabaseConnection();

        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    String result = rs.getString("lastId");

                    if (result == null) {
                        lastID = 1;
                    } else {
                        lastID = Integer.parseInt(result) + 1;
                    }
                }
            }
        }

        return lastID;
    }

    @Override
    public String toString() {

        return String.format("Adresse: %s N° %s, Commune %s, Ville %s",
                this.avenue,
                this.numero,
                this.commune,
                this.ville);
    }

}
