package lk.ijse.thedale.repository;

import lk.ijse.thedale.db.Dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentBookingRepo {
    public static String generateNextId() throws SQLException {
        String sql = "Select RentBookingID from RentBooking order by RentBookingID desc limit 1";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        String currentId = null;
        if (resultSet.next()){
            currentId = resultSet.getString(1);
            return nextOrderId(currentId);
        }
        return nextOrderId(null);
    }

    private static String nextOrderId(String currentId) {
        if (currentId != null){
            String[] split = currentId.split("Rent ");
            int customerId = Integer.parseInt(split[1]);
            customerId++;
            return "Rent "+ customerId;
        }
        return "Rent 1";
    }

    public static List<String> getRentId() throws SQLException {
        String sql = "select RentID from Rent";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<String> rentIdList = new ArrayList<>();

        while (resultSet.next()){
            rentIdList.add(resultSet.getString(1));
        }
        return rentIdList;
    }

    public static List<String> getCusId() throws SQLException {
        String sql = "select CusID from customer";

        Connection connection = Dbconnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> customerList = new ArrayList<>();

        while (resultSet.next()){
            customerList.add(resultSet.getString(1));
        }
        return customerList;
    }
}
