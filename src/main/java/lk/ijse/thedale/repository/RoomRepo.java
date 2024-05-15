package lk.ijse.thedale.repository;

import lk.ijse.thedale.db.Dbconnection;
import lk.ijse.thedale.model.Room;
import lk.ijse.thedale.model.RoomDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomRepo {
    public static boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM room WHERE RoomID = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(Room room) throws SQLException {
        String sql = "Insert into room values(?,?,?,?,?,?)";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, room.getRoomID());
        pstm.setObject(2, room.getType());
        pstm.setObject(3, room.getDate());
        //pstm.setObject(4, room.getQtyOnHand());
        pstm.setObject(4, room.getUnitPrice());
        pstm.setObject(5, room.getQty());
        pstm.setObject(6,room.getCusID());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Room room) throws SQLException {

        String sql = "Update room Set Type = ?, Date = ?  , UnitPrice = ?, Qty = ? , CusID = ? where RoomID = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, room.getType());
        pstm.setObject(2, room.getDate());
       // pstm.setObject(3, room.getQtyOnHand());
        pstm.setObject(3, room.getUnitPrice());
        pstm.setObject(4, room.getQty());
        pstm.setObject(5, room.getCusID());
        pstm.setObject(6, room.getRoomID());


        return pstm.executeUpdate() > 0;
    }

    public static String generateNextId() throws SQLException {
        String sql = "SELECT RoomID from room order by RoomID desc limit 1";
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
            String[] split = id.split("Room ");
            int RoomID = Integer.parseInt(split[1]);
            RoomID++;
            return "Room " + RoomID;
        }
        return "Room 1";
    }

    public static Room searchRoom(String roomId) throws SQLException {
        String sql = "select * from room where RoomID = ?";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, roomId);
        ResultSet resultSet = pstm.executeQuery();
        Room room = null;

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String type = resultSet.getString(2);
            String date = resultSet.getString(3);
           // String qtyOnHand = resultSet.getString(4);
            double unitPrice = resultSet.getDouble(4);
            String qty = resultSet.getString(5);
            String cusID = resultSet.getString(6);

            room = new Room(id, type, date, unitPrice, qty, cusID);

        }
        return room;
    }

    public static boolean updateRoomQty(List<RoomDetails> roomDetails) throws SQLException {
        for (RoomDetails roomDetail : roomDetails) {
            if (!updateRoomQty(roomDetail)){
                return false;
            }
        }
        return true;
    }

    public static boolean updateRoomQty(RoomDetails roomDetails) throws SQLException {
        String sql = "update Room set Qty = Qty - ? where RoomID = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setInt(1, roomDetails.getQty());
        pstm.setString(2, roomDetails.getRoomID());

        return pstm.executeUpdate() > 0;

    }

    public List<Room> getRoom() throws SQLException {
        String sql = "SELECT * FROM room";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<Room> roomList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String type = resultSet.getString(2);
            String date = resultSet.getString(3);
           // String qtyOnHand = resultSet.getString(4);
            double unitPrice = resultSet.getDouble(4);
            String qty = resultSet.getString(5);
            String cusID = resultSet.getString(6);

            Room room = new Room(id,type,date, unitPrice, qty,cusID);
            roomList.add(room);

    }
        return roomList;
    }
}
