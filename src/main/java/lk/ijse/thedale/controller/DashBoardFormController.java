package lk.ijse.thedale.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import lk.ijse.thedale.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardFormController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pagingPane.setVisible(true);
        try {
            Navigation.switchPaging(pagingPane,"home_form.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnFood;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnServices;

    @FXML
    private JFXButton btnRoom;

    @FXML
    private Pane pagingPane;

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"customer_form.fxml");

    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"employee_form.fxml");

    }

    @FXML
    void btnFoodOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"food_form.fxml");

    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"home_form.fxml");

    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {
        btnLogOut.getScene().getWindow().hide();
        Navigation.changeState("/view/login_form.fxml","Login Form");

    }

    @FXML
    void btnServicesOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"rent_form.fxml");

    }

    @FXML
    void btnRoomOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"room_form.fxml");

    }

}
