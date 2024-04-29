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
import lk.ijse.thedale.model.Customer;
import lk.ijse.thedale.repository.CustomerRepo;
import lk.ijse.thedale.tm.CustomerTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableColumn<?, ?> colSex;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtCusId;

    @FXML
    private TextField txtCusName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtSex;

    CustomerRepo customerRepo = new CustomerRepo();

    private List<Customer> customerList = new ArrayList<>();

    @FXML
    void btnClearOnAction(ActionEvent event) { clearFields();}

        private void clearFields(){
        txtCusId.setText("");
        txtCusName.setText("");
        txtEmail.setText("");
        txtNic.setText("");
        txtSex.setText("");

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtCusId.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer deleted successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try{
            txtCusId.setText(CustomerRepo.generateNextId());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        customerList=getAllCustomer();
        setCellValueFactory();
        loadCustomerTable();
    }

    private void loadCustomerTable() {
        CustomerRepo customerRepo = new CustomerRepo();
        ObservableList<CustomerTm>tmList = FXCollections.observableArrayList();
        try {
            List<Customer> customerList = customerRepo.getCustomer();
            for (Customer customer : customerList) {
                CustomerTm customerTm = new CustomerTm(
                        customer.getCusId(),
                        customer.getCusName(),
                        customer.getSex(),
                        customer.getNic(),
                        customer.getContact(),
                        customer.getEmail()

                );
                tmList.add(customerTm);
            }
            tblCustomer.setItems(tmList);
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }

    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSex.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    private List<Customer> getAllCustomer() {
        List<Customer>customerList = null;
        try {
            customerList = customerRepo.getCustomer();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        return customerList;
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtCusId.getText();
        String name = txtCusName.getText();
        String sex = txtSex.getText();
        String nic = txtNic.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String userId = LoginFormController.getInstance().userId;

        Customer customer = new Customer(id,name,sex,nic,contact,email,userId);

        try {
            boolean isSaved = CustomerRepo.save(customer);

            new Alert(Alert.AlertType.CONFIRMATION, "Customer saved successfully").show();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtCusId.getText();
        String name = txtCusName.getText();
        String sex = txtSex.getText();
        String nic = txtNic.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String userId = LoginFormController.getInstance().userId;

        Customer customer = new Customer(id, name, sex, nic, contact, email, userId);

        try {
            boolean isUpdated = CustomerRepo.update(customer);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer updated successfully").show();
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Customer updated successfully").show();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
