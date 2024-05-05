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
import lk.ijse.thedale.model.Rent;
import lk.ijse.thedale.repository.RentRepo;
import lk.ijse.thedale.tm.RentTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RentFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private Pane pageInPane;


    @FXML
    private TableView<RentTm> tblRent;


    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtRentId;

    @FXML
    private TextField txtType;

    RentRepo rentRepo = new RentRepo();

    private List<Rent> rentList = new ArrayList<>();

    @FXML
    void btnClearOnAction(ActionEvent event) { clearFields();}

    private void clearFields() {
        txtRentId.setText("");
        txtQty.setText("");
        txtDescription.setText("");
        txtType.setText("");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtRentId.setText(RentRepo.generateNextId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.rentList = getAllRental();
        setCellValueFactory();
        loadRentTable();


    }

    private void loadRentTable() {
        RentRepo rentRepo = new RentRepo();
        ObservableList<RentTm>tmList = FXCollections.observableArrayList();
        try {
            List<Rent> rentList = rentRepo.getRent();
            for (Rent rent : rentList) {
                RentTm rentTm = new RentTm(
                        rent.getRentID(),
                        rent.getQty(),
                        rent.getDescription(),
                        rent.getType()
                );
                tmList.add(rentTm);
            }
            tblRent.setItems(tmList);
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
        }
    }

    private void setCellValueFactory() {

        colId.setCellValueFactory(new PropertyValueFactory<>("RentID"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));

    }

    private List<Rent> getAllRental() {
        List<Rent>rentList = null;
        try {
            rentList = rentRepo.getRent();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
    }
        return rentList;
}

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
       String id = txtRentId.getText();

       try {
           boolean isDeleted = RentRepo.delete(id);
           if(isDeleted){
               new Alert(Alert.AlertType.CONFIRMATION,"Rent deleted successfully!").show();
           }
       }catch (SQLException e){
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
       }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtRentId.getText();
        int Qty = Integer.parseInt(txtQty.getText());
        String description = txtDescription.getText();
        String type = txtType.getText();

        Rent rent = new Rent(id, Qty, description, type);

        try {
            boolean isSaved = RentRepo.save(rent);
            new Alert(Alert.AlertType.CONFIRMATION,"Rent saved successfully!").show();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            loadRentTable();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String RentID = txtRentId.getText();
        int Qty = Integer.parseInt(txtQty.getText());
        String description = txtDescription.getText();
        String type = txtType.getText();

        Rent rent = new Rent(RentID, Qty, description, type);

        try {
            boolean isUpdate = RentRepo.update(rent);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Rent updated successfully!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            loadRentTable();
        }
    }

}
