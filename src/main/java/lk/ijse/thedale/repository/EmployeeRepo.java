package lk.ijse.thedale.repository;

import lk.ijse.thedale.db.Dbconnection;
import lk.ijse.thedale.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {
    public static String generateNextId() throws SQLException {
        String sql = "Select EmpId from employee order by EmpId desc limit 1 ";
        Connection connection = Dbconnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String id = null;
        if (resultSet.next()) {
            id = resultSet.getString(1);
            return splitId(id);
        }
        return splitId(null);
    }

    private static String splitId(String id) {
        if (id != null) {
            String[] split = id.split("Emp ");
            int EmpId = Integer.parseInt(split[1]);
            EmpId++;
            return "Emp " + EmpId;
        }
        return "Emp 1";
    }

    public static boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee VALUES (?,?,?,?,?,?)";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, employee.getEmpID());
        pstm.setObject(2, employee.getName());
        pstm.setObject(3, employee.getType());
        pstm.setObject(4, employee.getEmail());
        pstm.setObject(5, employee.getDOB());
        pstm.setObject(6, employee.getUserID());

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

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM employee WHERE EmpId = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        return pstm.executeUpdate() > 0;
    }


    public List<Employee> getEmployee() throws SQLException {
      String sql = "SELECT * FROM employee";
      ResultSet resultSet = Dbconnection.getInstance().getConnection().prepareStatement(sql).executeQuery();
      List<Employee> employeeList = new ArrayList<>();
      while (resultSet.next()) {
          employeeList.add(new Employee(
                  resultSet.getString(1),
                  resultSet.getString(2),
                  resultSet.getString(3),
                  resultSet.getString(4),
                  resultSet.getString(5),
                  resultSet.getString(6)

          ));
      }
      return employeeList;
    }
}
