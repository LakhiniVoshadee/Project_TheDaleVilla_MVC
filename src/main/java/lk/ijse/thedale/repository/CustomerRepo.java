package lk.ijse.thedale.repository;

import lk.ijse.thedale.db.Dbconnection;
import lk.ijse.thedale.model.Customer;
import lk.ijse.thedale.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {


    public static boolean delete(String id) throws SQLException {
        String sql = "delete from customer where CusId=?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        return pstm.executeUpdate()>0;
    }

    public static boolean save(Customer customer) throws SQLException {
       String sql = "insert into customer values(?,?,?,?,?,?,?)";
       PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

       pstm.setObject(1,customer.getCusId());
       pstm.setObject(2,customer.getCusName());
       pstm.setObject(3,customer.getSex());
       pstm.setObject(4,customer.getNic());
       pstm.setObject(5,customer.getContact());
       pstm.setObject(6,customer.getEmail());
       pstm.setObject(7,customer.getUserID());

       return pstm.executeUpdate() > 0;
    }

    public static boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET Name = ?, Sex = ?, Nic =?, Contact =?, Email =? WHERE CusId=? ";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,customer.getCusName());
        pstm.setObject(2,customer.getSex());
        pstm.setObject(3,customer.getNic());
        pstm.setObject(4,customer.getContact());
        pstm.setObject(5,customer.getEmail());
        pstm.setObject(6,customer.getUserID());

        return pstm.executeUpdate()>0;
    }

    public static String generateNextId() throws SQLException {
        String sql = "SELECT CusId FROM customer ORDER BY CusId DESC LIMIT 1";
        Connection connection = Dbconnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String id = null;
        if (resultSet.next()){
            id = resultSet.getString(1);
            return splitId(null);
        }
        return splitId(null);

    }

    private static String splitId(String id) {
        if (id != null){
            String[] ids = id.split("CUS");
            int CusId = Integer.parseInt(ids[1]);
            CusId++;
            return "CUS" + CusId;
        }
        return "Cus 1";

    }

    public List<Customer> getCustomer() throws SQLException {
        String sql = "select * from customer";
        ResultSet resultSet = Dbconnection.getInstance().getConnection().prepareStatement(sql).executeQuery();
        List<Customer> customerList = new ArrayList<>();
        while (resultSet.next()){
            customerList.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)

            ));
        }
        return customerList;
    }
}
