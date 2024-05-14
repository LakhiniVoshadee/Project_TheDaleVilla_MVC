package lk.ijse.thedale.repository;

import lk.ijse.thedale.db.Dbconnection;
import lk.ijse.thedale.model.PlacedRoomBooking;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceRoomRepo {

    public static boolean orderPlaced(PlacedRoomBooking placedRoomBooking) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = RoomBookingRepo.saveOrder(placedRoomBooking.getRoomBooking());
            if (isOrderSaved) {

                boolean isOrderDetailSaved = RoomDetailsRepo.saveOrderDetails(placedRoomBooking.getRoomDetails());
                if(isOrderDetailSaved) {

                    boolean isRoomUpdated = RoomRepo.updateRoomQty(placedRoomBooking.getRoomDetails());
                    if(isRoomUpdated) {

                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;

        }catch (SQLException e){
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }
}
