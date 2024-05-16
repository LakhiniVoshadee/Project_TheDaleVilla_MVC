package lk.ijse.thedale.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;


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

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    RentRepo rentRepo = new RentRepo();

    private List<Rent> rentList = new ArrayList<>();

    @FXML
    void btnClearOnAction(ActionEvent event) { clearFields();}

    private void clearFields() {
        txtRentId.setText("");
        txtType.setText("");
        txtQty.setText("");
        txtDescription.setText("");
        //txtQtyOnHand.setText("");
        txtUnitPrice.setText("");

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

    @FXML
    void rentTableClick(MouseEvent event) {
        TablePosition pos = tblRent.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        ObservableList<TableColumn<RentTm,?>> columns = tblRent.getColumns();

        txtRentId.setText(columns.get(0).getCellData(row).toString());
        txtType.setText(columns.get(1).getCellData(row).toString());
        txtQty.setText(columns.get(2).getCellData(row).toString());
        txtDescription.setText(columns.get(3).getCellData(row).toString());
        txtUnitPrice.setText(columns.get(4).getCellData(row).toString());


    }

    private void loadRentTable() {
        RentRepo rentRepo = new RentRepo();
        ObservableList<RentTm>tmList = FXCollections.observableArrayList();
        try {
            List<Rent> rentList = rentRepo.getRent();
            for (Rent rent : rentList) {
                RentTm rentTm = new RentTm(
                        rent.getRentID(),
                        rent.getType(),
                        rent.getQty(),
                        rent.getDescription(),
                       // rent.getQtyOnHand(),
                        rent.getUnitPrice()
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
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
      //  colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));

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
       RentRepo rentRepo = new RentRepo();

       try {
           boolean isDeleted = rentRepo.delete(id);
           if(isDeleted){
               new Alert(Alert.AlertType.CONFIRMATION,"Rent deleted successfully!").show();
               loadRentTable();
           }
       }catch (SQLException e){
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
       }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String RentID = txtRentId.getText();
        String Type = txtType.getText();
        int Qty = Integer.parseInt(txtQty.getText());
        String Description = txtDescription.getText();
       // String QtyOnHand = txtQtyOnHand.getText();
        double UnitPrice = Double.parseDouble(txtUnitPrice.getText());

        Rent rent = new Rent(RentID,Type,Qty,Description, UnitPrice);

        try {
            boolean isSaved = RentRepo.save(rent);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Rent saved successfully!").show();
                loadRentTable();
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String RentID = txtRentId.getText();
        String Type = txtType.getText();
        int Qty = Integer.parseInt(txtQty.getText());
        String Description = txtDescription.getText();
       // String QtyOnHand = txtQtyOnHand.getText();
        double UnitPrice = Double.parseDouble(txtUnitPrice.getText());


        Rent rent = new Rent(RentID,Type,Qty,Description, UnitPrice);



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
