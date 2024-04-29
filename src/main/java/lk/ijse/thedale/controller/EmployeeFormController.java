package lk.ijse.thedale.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.thedale.model.Employee;
import lk.ijse.thedale.repository.EmployeeRepo;
import lk.ijse.thedale.tm.EmployeeTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class EmployeeFormController implements Initializable {

    @FXML
    private TableColumn<String, String> colDob;

    @FXML
    private TableColumn<String, String> colEmail;

    @FXML
    private TableColumn<String, String> colId;

    @FXML
    private TableColumn<String, String> colName;

    @FXML
    private TableColumn<String, String> colType;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

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

    EmployeeRepo employeeRepo = new EmployeeRepo();

    private List<Employee> employeeList = new ArrayList<>();


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields(){
        txtEmpId.setText("");
        txtEmpName.setText("");
        txtType.setText("");
        txtDob.setText("");

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtEmpId.getText();

        try {
            boolean isDeleted = EmployeeRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee deleted successfully").show();
            }
        } catch(SQLException e){
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
       employeeList =getAllEmployee();
        setCellValueFactory();
        loadEmployeeTable();

    }

    private List<Employee>  getAllEmployee() {
        List<Employee>employeeList = null;
        try {
            employeeList = employeeRepo.getEmployee();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        return employeeList;
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) {
       String id = txtEmpId.getText();
       String name = txtEmpName.getText();
       String type = txtType.getText();
       String email = txtEmail.getText();
       String dob = txtDob.getText();
       String userId = LoginFormController.getInstance().userId;

       Employee employee = new Employee(id,name,type,email,dob,userId);

       try {
           boolean isSaved = EmployeeRepo.save(employee);
           new Alert(Alert.AlertType.CONFIRMATION,"Employee has been saved successfully").show();
       }catch (SQLException e){
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
           loadEmployeeTable();
       }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
       String id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String type = txtType.getText();
        String email = txtEmail.getText();
        String dob = txtDob.getText();
        String userId = "U001";
        //String userId = LoginFormController.getInstance().userId;

        Employee employee = new Employee(id,name,type,email,dob,userId);

        try {
            boolean isUpdated = EmployeeRepo.update(employee);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee has been updated successfully").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            loadEmployeeTable();
        }

    }




   /* @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtEmpId.setText(EmployeeRepo.generateNextId());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        setCellValueFactory();
        loadEmployeeTable();
    }*/

    private void loadEmployeeTable() {
        EmployeeRepo employeeRepo = new EmployeeRepo();
        ObservableList<EmployeeTm>tmList = FXCollections.observableArrayList();
        try {
            List<Employee> employeeList = employeeRepo.getEmployee();
            for (Employee employee : employeeList) {
                EmployeeTm employeeTm = new EmployeeTm(
                        employee.getEmpID(),
                        employee.getName(),
                        employee.getType(),
                        employee.getEmail(),
                        employee.getDOB()
                );
                tmList.add(employeeTm);
            }
            tblEmployee.setItems(tmList);
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }

    }

    private void setCellValueFactory() {

            colId.setCellValueFactory(new PropertyValueFactory<>("EmpID"));
            colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
            colDob.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        }
    }

