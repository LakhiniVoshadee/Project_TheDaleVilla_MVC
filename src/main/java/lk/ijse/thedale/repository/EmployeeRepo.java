package lk.ijse.thedale.repository;

import lk.ijse.thedale.db.Dbconnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRepo {
    public static String generateNextId() throws SQLException {
        String sql = "Select EmpId from Employee order by EmpId desc limit 1 ";
        Connection connection = Dbconnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String id = null;
        if (resultSet.next()) {
            id = resultSet.getString(1);
            return splitId(null);
        }
        return splitId(null);
    }

    private static String splitId(String id) {
        if (id != null){
            String []  split = id.split("EMP ");
            int EmpId = Integer.parseInt(split[1]);
            EmpId++;
            return "EMP " + EmpId;
        }
        return "Emp 1";
    }
}
