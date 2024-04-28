package lk.ijse.thedale.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lk.ijse.thedale.model.Employee;
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
    void btnClearOnAction(ActionEvent event) { clearFields();}

    private void clearFields(){
        txtEmpId.setText("");
        txtEmpName.setText("");
        txtType.setText("");
        txtDob.setText("");

    }



    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtEmpId.getText();



    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
       String id = txtEmpId.getText();
       String name = txtEmpName.getText();
       String type = txtType.getText();
       String email = txtEmail.getText();
       String dob = txtDob.getText();

       //Employee employee = new Employee(id, name, type, dob, email);
       // Employee employee = new Employee(id,name,type,email,dob);
        Employee employee = new Employee(id,name,type,email,dob);

       try {
           boolean isSaved = EmployeeRepo.save(employee);

               new Alert(Alert.AlertType.CONFIRMATION,"Employee has been saved successfully").show();


       }catch (SQLException e){
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
       }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String type = txtType.getText();
        String email = txtEmail.getText();
        String dob = txtDob.getText();

        Employee employee = new Employee(id,name,type,email,dob);

        try {
            boolean isUpdated = EmployeeRepo.update(employee);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee has been updated successfully").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtEmpId.getText();

        try {
            Employee employee = EmployeeRepo.searchById(id);

            if (employee != null) {
                txtEmpId.setText(employee.getEmpID());
                txtEmpName.setText(employee.getName());
                txtType.setText(employee.getType());
                txtEmail.setText(employee.getEmail());
                txtDob.setText(employee.getDOB());
            }
        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

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
