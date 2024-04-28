package lk.ijse.thedale.repository;

import lk.ijse.thedale.db.Dbconnection;
import lk.ijse.thedale.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        if (id != null) {
            String[] split = id.split("EMP ");
            int EmpId = Integer.parseInt(split[1]);
            EmpId++;
            return "EMP " + EmpId;
        }
        return "Emp 1";
    }

    public static boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employee VALUES (?,?,?,?,?)";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, employee.getEmpID());
        pstm.setObject(2, employee.getName());
        pstm.setObject(3, employee.getType());
        pstm.setObject(4, employee.getDOB());
        pstm.setObject(5, employee.getEmail());
       // pstm.setObject(6, employee.getUserID());

        return pstm.executeUpdate() > 0;

    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET Name = ?,Type = ?,DOB = ?,Email = ?,UserID = ? WHERE EmpId = ? ";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, employee.getName());
        pstm.setObject(2, employee.getType());
        pstm.setObject(3, employee.getDOB());
        pstm.setObject(4, employee.getEmail());
        pstm.setObject(5, employee.getEmpID());
       // pstm.setObject(6, employee.getUserID());

        return pstm.executeUpdate() > 0;
    }

    public static Employee searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE EmpId = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,id);
        ResultSet resultSet = pstm.executeQuery();

        Employee employee = null;

        if (resultSet.next()){
            String empId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String type = resultSet.getString(3);
            String dob = resultSet.getString(4);
            String email = resultSet.getString(5);
           // String userID = resultSet.getString(6);

            //employee = new Employee(empId,name,type,dob,email,userID);

        }
        return employee;
    }
}
