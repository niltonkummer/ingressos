package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String HOSTNAME = "localhost";
    private static final String DATABASE = "cinema";
    private static final String PORT = "3306";
    private static final String URL = "jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DATABASE;
    private static final String USER = "root";
    private static final String PASSWORD = "asenna";
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        return (DriverManager.getConnection(URL, USER, PASSWORD));
    }

}
