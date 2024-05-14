package lk.ijse.thedale.repository;


import lk.ijse.thedale.db.Dbconnection;
import lk.ijse.thedale.model.RoomDetails;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RoomDetailsRepo {

    public static boolean saveOrderDetails(List<RoomDetails> roomDetails) throws SQLException {
        for (RoomDetails roomDetail : roomDetails) {
            if (!orderSave(roomDetail)){
                return false;
            }
        }
        return true;
    }

    public static boolean orderSave(RoomDetails roomDetails) throws SQLException {
        String sql = "Insert into RoomDetails values(?,?,?,?,?)";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1,roomDetails.getRoomBookingID());
        pstm.setString(2,roomDetails.getRoomID());
        pstm.setInt(3,roomDetails.getQty());
        pstm.setDouble(4,roomDetails.getUnitPrice());
        pstm.setString(5,roomDetails.getType());

        return pstm.executeUpdate() > 0;
    }
}
