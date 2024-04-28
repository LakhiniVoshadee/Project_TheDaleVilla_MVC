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

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String type = txtType.getText();
        String dob = txtDob.getText();
        String email = txtEmail.getText();
        String userid = LoginFormController.getInstance().userid;



        Employee employee = new Employee(id, name, type, dob, email,userid);

      /*  try {
            boolean isSaved = EmployeeRepo.save(employee);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee has been saved successfully").show();

            } catch (Exception e){
                new Alert(Alert.AlertType.ERROR, e.getmessage()).show();
            }
        }*/

          try {

              boolean isSaved = EmployeeRepo.save(employee);
              if(isSaved) {
                  new Alert(Alert.AlertType.CONFIRMATION, "Employee has been saved successfully").show();
              }
          }catch (Exception e){
              new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

          }


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
