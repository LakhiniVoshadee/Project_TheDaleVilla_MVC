package lk.ijse.thedale.repository;

import lk.ijse.thedale.db.Dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomBookingRepo {

    public static List<String> getCusId() throws SQLException {

        String sql = "select CusID from Customer";

        Connection connection = Dbconnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> customerList = new ArrayList<>();

        while (resultSet.next()){
            customerList.add(resultSet.getString(1));
        }
        return customerList;
    }

    public static List<String> getRoomId() throws SQLException {
        String sql = "select RoomID from Room";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<String> roomIdList = new ArrayList<>();

        while (resultSet.next()){
            roomIdList.add(resultSet.getString(1));
        }
        return roomIdList;
    }

    public static String generateNextId() throws SQLException {
        String sql =  "Select RoomBookingID from Roombooking order by RoomBookingID desc limit 1";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        String currentRmBookingID = null;
        if (resultSet.next()){
            currentRmBookingID = resultSet.getString(1);
            return nextBookingId(currentRmBookingID) ;
        }
        return nextBookingId(null);
    }

    private static String nextBookingId(String currentRmBookingID) {
        if (currentRmBookingID != null){
            String[] split = currentRmBookingID.split("0 ");
            int customerId = Integer.parseInt(split[1]);
            customerId++;
            return "0 " + customerId;
        }
        return "0 1";

    }

}
