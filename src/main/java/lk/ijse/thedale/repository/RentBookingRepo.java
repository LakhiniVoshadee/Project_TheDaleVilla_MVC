package lk.ijse.thedale.repository;

import lk.ijse.thedale.db.Dbconnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
