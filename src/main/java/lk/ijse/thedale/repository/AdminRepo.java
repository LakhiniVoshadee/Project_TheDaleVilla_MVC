package lk.ijse.thedale.repository;

import lk.ijse.thedale.db.Dbconnection;
import lk.ijse.thedale.model.Admin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRepo {

    public static String generateNextId() throws SQLException {
        String sql = "Select UserId from Admin order by UserId desc limit 1 ";

        ResultSet resultSet = Dbconnection.getInstance().getConnection().prepareStatement(sql).executeQuery();
        String currentId = null;
        if (resultSet.next()) {
            currentId = resultSet.getString(1);
            return splitId(currentId);

        }
        return splitId(null);
    }

    private static String splitId(String currentId) {
        if (currentId == null) {
            String[] split = currentId.split("U");
            int id = Integer.parseInt(split[1]);
            id++;
            return "U" + id;
        }
        return "U1";
    }

  /*  public boolean addUser(Admin admin) throws SQLException {
        String sql = "Insert into admin values(?,?,?)";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, admin.getUserId());
        pstm.setString(2, admin.getUserName());
        pstm.setString(3, admin.getPassword());

        return pstm.executeUpdate() > 0;

    }

   */
}
