package lk.ijse.thedale.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.thedale.db.Dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupFormController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserNme;

    @FXML
    void btnSignUpOnAction(ActionEvent event) throws SQLException {
        String userNme = txtUserNme.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();

        saveUser(userNme,password,email);
    }

    private void saveUser(String userNme, String password, String email) throws SQLException {
        try {
            String sql = "INSERT INTO users VALUES(?,?,?)";

            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setObject(1,userNme);
            pstm.setObject(2,password);
            pstm.setObject(3,email);

            if (pstm.executeUpdate()>0){
                new Alert(Alert.AlertType.CONFIRMATION,"user saved!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"something went wrong!").show();
        }
    }

}
