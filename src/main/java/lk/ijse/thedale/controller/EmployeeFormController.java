package lk.ijse.thedale.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lk.ijse.thedale.repository.EmployeeRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class EmployeeFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<?> tblEmployee;

    @FXML
    private TextField txtDob;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TextField txtType;


    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtEmpId.setText(EmployeeRepo.generateNextId());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
