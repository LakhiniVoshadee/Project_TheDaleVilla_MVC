package lk.ijse.thedale.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lk.ijse.thedale.model.Employee;
import lk.ijse.thedale.repository.EmployeeRepo;
import lk.ijse.thedale.tm.EmployeeTm;
import lk.ijse.thedale.util.DataValidateController;
import lk.ijse.thedale.util.Validation;
//import lk.ijse.thedale.util.Validation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


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
    private DatePicker pickerDate;


    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TextField txtType;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblEmployeeName;

    @FXML
    private Label lblType;


   LinkedHashMap<TextField, Pattern> map =new LinkedHashMap();

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
        pickerDate.setValue(null);

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
       this.employeeList =getAllEmployee();
        setCellValueFactory();
        loadEmployeeTable();

       /* Pattern patternId = Pattern.compile("^([A-Z0-9])$");
        Pattern patternName = Pattern.compile("^[A-z|\\\\s]{3,}$");
        //Pattern patternType = Pattern.compile("^[A-z|\\\\s]{5,}$");
        Pattern patternEmail = Pattern.compile("^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$");

        map.put(txtEmpId, patternId);
        map.put(txtEmpName, patternName);
        map.put(txtEmail, patternEmail);

        */

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
       String dob = String.valueOf(pickerDate.getValue());
       String userId = LoginFormController.getInstance().userId;

       Employee employee = new Employee(id,name,type,email,dob,userId);
       if (DataValidateController.validateEmail(txtEmail.getText())) {
           lblEmail.setText("");

           if (DataValidateController.validateEmpType(txtType.getText())) {
               lblType.setText("");

               if (DataValidateController.validateEmpName(txtEmpName.getText())) {
                   lblEmployeeName.setText("");


                   try {
                       boolean isSaved = EmployeeRepo.save(employee);
                       new Alert(Alert.AlertType.CONFIRMATION, "Employee has been saved successfully").show();
                       loadEmployeeTable();
                   } catch (SQLException e) {
                       new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

                   }
               } else {
                   lblEmployeeName.setText("Invalid Name");
               }
           } else {
               lblType.setText("Invalid Type");
           }
       }else {
           lblEmail.setText("Invalid Email");
       }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
       String id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String type = txtType.getText();
        String email = txtEmail.getText();
        String dob = String.valueOf(pickerDate.getValue());
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
   @FXML
    void txtKeyOnRele(KeyEvent event) {
        Validation.validate(map);
    }

}

