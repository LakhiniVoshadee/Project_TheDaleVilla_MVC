package lk.ijse.thedale.model;

import lk.ijse.thedale.db.Dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public static boolean verifyCredentials(String UserName, String Password) {
        try {
          Dbconnection instance = Dbconnection.getInstance();
            Connection connection = instance.getConnection();

            String sql = "select Password from admin where UserName=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, UserName);
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
