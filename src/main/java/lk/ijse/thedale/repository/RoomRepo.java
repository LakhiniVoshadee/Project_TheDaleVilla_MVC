package lk.ijse.thedale.repository;

import lk.ijse.thedale.db.Dbconnection;
import lk.ijse.thedale.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomRepo {
    public static boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM rooms WHERE RoomID = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(Room room) throws SQLException {
        String sql = "Insert into room values(?,?,?)";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, room.getRoomID());
        pstm.setObject(2, room.getType());
        pstm.setObject(3, room.getDate());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Room room) throws SQLException {

        String sql = "Update room Set Type = ?, Date = ? , CusID = ? where RoomID = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, room.getType());
        pstm.setObject(2, room.getDate());
        pstm.setObject(3, room.getRoomID());


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
            int RoomID = Integer.parseInt(split[0]);
            RoomID++;
            return "Room " + RoomID;
        }
        return "Room 1";
    }

    public List<Room> getRoom() throws SQLException {
        String sql = "SELECT * FROM rooms";
        ResultSet resultSet = Dbconnection.getInstance().getConnection().prepareStatement(sql).executeQuery();
        List<Room> roomList = new ArrayList<>();
        while (resultSet.next()) {
            roomList.add(new Room(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)


            ));

    }
        return roomList;
}
}
