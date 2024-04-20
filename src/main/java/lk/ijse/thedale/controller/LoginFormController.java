package lk.ijse.thedale.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.thedale.db.Dbconnection;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
     public TextField txtUserId;
     public PasswordField txtPassword;
     public AnchorPane rootLogin;

public void btnLoginOnAction(ActionEvent actionEvent) throws IOException  {
    String userId = txtUserId.getText();
    String password = txtPassword.getText();

    try{
        checkCredential(userId,password);
    }catch (SQLException e){
        new Alert(Alert.AlertType.ERROR,"OOPS! something went wrong").show();
    }
}

    private void checkCredential(String userId, String password) throws SQLException {
     String sql = "select * from users where username = ?";

        Connection connection = Dbconnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,userId);
        
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            String dbPw = resultSet.getNString(2);
            
            if (dbPw.equals(password)){
                navigateToTheDashboard();
                
            }else {
                new Alert(Alert.AlertType.ERROR,"Password is incorrect").show();
            }
        }else {
            new Alert(Alert.AlertType.INFORMATION,"User id not found").show();
        }
    }

    private void navigateToTheDashboard() {

    }
    }


