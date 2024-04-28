package lk.ijse.thedale.model;

import lk.ijse.thedale.db.Dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserModel {
    public static boolean verifyCredentials(String UserId,String UserName, String Password,String Mobile) {
        try {
          Dbconnection instance = Dbconnection.getInstance();
            Connection connection = instance.getConnection();

            String sql = "select UserName,Password,Mob_num from admin where UserId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, UserId);
            preparedStatement.setString(2, UserName);


            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (Password.equals(resultSet.getString(1))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
