package lk.ijse.thedale.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbconnection {
    private static Dbconnection dbconnection;
    private Connection connection;

    private Dbconnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jbbc:mysql://localhost:3306/theDaleVilla",
                "root",
                "IJSE@123"
        );
    }



    public static Dbconnection  getInstance() throws SQLException {
        return (dbconnection==null)?dbconnection=new Dbconnection():dbconnection;
    }
    public Connection getConnection() {return connection;
    }
}
