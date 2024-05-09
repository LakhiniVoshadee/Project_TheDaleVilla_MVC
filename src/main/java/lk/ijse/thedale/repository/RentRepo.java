package lk.ijse.thedale.repository;

import lk.ijse.thedale.db.Dbconnection;
import lk.ijse.thedale.model.Rent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentRepo {
    public static String generateNextId() throws SQLException {
        String sql = "SELECT RentID FROM rent order by RentID desc LIMIT 1";
        Connection connection = Dbconnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String id = null;
        if (resultSet.next()){
            id = resultSet.getString(1);
            return splitId(id);
        }
        return splitId(null);
    }

    private static String splitId(String id) {
        if (id != null){
           String[] split = id.split("Rent ");
           int RentID = Integer.parseInt(split[1]);
           RentID++;
           return "Rent " + RentID;
        }
        return "Rent 1";
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM rent WHERE RentID = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        return pstm.executeUpdate() >0;
    }

    public static boolean save(Rent rent) throws SQLException {
        String sql = "INSERT INTO rent VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,rent.getRentID());
        pstm.setObject(2,rent.getQty());
        pstm.setObject(3,rent.getDescription());
        pstm.setObject(4,rent.getType());
        pstm.setObject(5,rent.getQtyOnHand());
        pstm.setObject(6,rent.getUnitPrice());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Rent rent) throws SQLException {
        String sql = "Update rent set Type = ?, Qty = ?, Description = ? ,QtyOnHand = ?, UnitPrice = ? where RentID = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,rent.getType());
        pstm.setObject(2,rent.getQty());
        pstm.setObject(3,rent.getDescription());
        pstm.setObject(4,rent.getQtyOnHand());
        pstm.setObject(5,rent.getUnitPrice());
        pstm.setObject(6,rent.getRentID());

        return pstm.executeUpdate() > 0;
    }

    public static Rent searchRent(String rentId) throws SQLException {
        String sql = "select * from rent where RentID = ?";

        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);


        pstm.setObject(1,rentId);
        ResultSet resultSet = pstm.executeQuery();
        Rent rent = null;

        if (resultSet.next()){
            String id = resultSet.getString(1);
            int qty = Integer.parseInt(resultSet.getString(2));
            String description = resultSet.getString(3);
            String type = resultSet.getString(4);
            String qtyOnHand = resultSet.getString(5);
            double unitPrice = resultSet.getDouble(6);

            rent = new Rent(id,type,qty,description,qtyOnHand,unitPrice);

        }
        return rent;

    }

    public List<Rent> getRent() throws SQLException {
        String sql = "SELECT * FROM rent";
        ResultSet resultSet = Dbconnection.getInstance().getConnection().prepareStatement(sql).executeQuery();
        List<Rent> rentList = new ArrayList<>();
        while (resultSet.next()){
            String id = resultSet.getString(1);
            int qty = Integer.parseInt(resultSet.getString(2));
            String description = resultSet.getString(3);
            String type = resultSet.getString(4);
            String qtyOnHand = resultSet.getString(5);
            double unitPrice = resultSet.getDouble(6);

            Rent rent = new Rent(id,type,qty,description,qtyOnHand,unitPrice);
            rentList.add(rent);

        }
        return rentList;
    }
}
