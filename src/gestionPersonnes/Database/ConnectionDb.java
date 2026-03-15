package gestionPersonnes.Database;

public class ConnectionDb {
    private String server;
    private String data;
    private String user;
    private String password;
    private int port;
    private int db;
    
    public ConnectionDb() {
    }

    public ConnectionDb(String server, String data, String user, String password, int port, int db) {
        this.server = server;
        this.data = data;
        this.user = user;
        this.password = password;
        this.port = port;
        this.db = db;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }

    @Override
    public String toString() {
        StringBuilder strConnect = new StringBuilder();
        switch(this.db) {
        case 1: 
            // MySQL ConnexionString
            strConnect.append("jdbc:mysql")
            .append("://")
            .append(this.server)
            .append(":")
            .append(this.port)
            .append("/")
            .append(this.data)
            .append("?user=")
            .append(this.user)
            .append("&password=")
            .append(this.password)
            .append("&serverTimezone=UTC");
            break;
        case 2:
            // SQLServer Connection
            strConnect.append("jdbc:sqlserver://")
            .append(this.server)
            .append(":")
            .append(this.port)
            .append(";databaseName=")
            .append(this.data)
            .append(";user=")
            .append(this.user)
            .append(";password=")
            .append(this.password)
            .append(";encrypt=true;trustServerCertificate=true;");
            break;
        case 3: 
            // PosGreSQL
            strConnect.append("jdbc:postgresql://")
            .append(this.server)
            .append(":")
            .append(this.port)
            .append("/")
            .append(this.data)
            .append("?user=")
            .append(this.user)
            .append("&password=")
            .append(this.password);
            break;
        default:
            throw new IllegalArgumentException("SGBD Inexistant");
        }
        return strConnect.toString();
    }
}