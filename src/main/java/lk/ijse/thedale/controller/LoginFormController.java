package lk.ijse.thedale.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.thedale.model.UserModel;
import lk.ijse.thedale.util.Navigation;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private JFXButton btnLogin;

    @FXML
    private Hyperlink hyperForgotPassword;

    @FXML
    private Hyperlink hyperSignUp;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        if(UserModel.verifyCredentials(txtUserName.getText(), txtPassword.getText())) {
            try {
                Navigation.switchNavigation("dashboard_form.fxml",event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    void linkForgotPwOnAction(ActionEvent event) {

    }

    @FXML
    void linkSignUpOnAction(ActionEvent event) {

    }

}
