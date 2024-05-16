package lk.ijse.thedale.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.thedale.model.Customer;
import lk.ijse.thedale.model.Rent;
import lk.ijse.thedale.repository.CustomerRepo;
import lk.ijse.thedale.repository.RentBookingRepo;
import lk.ijse.thedale.repository.RentRepo;
import lk.ijse.thedale.tm.RentBookingTm;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class RentBookingFormController implements Initializable {

    @FXML
    private JFXButton btnAdToCart;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXComboBox<String> cmbCId;

    @FXML
    private JFXComboBox<String> cmbRentId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colRentId;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblBDate;

    @FXML
    private Label lblBId;

    @FXML
    private Label lblCName;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblQtyOHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Pane pagingPane;

    @FXML
    private TextField txtQty;


    @FXML
    private TableView<RentBookingTm> tblRentBookingCart;



    private ObservableList<RentBookingTm> cartList = FXCollections.observableArrayList();
    private double netTotal = 0;

    @FXML
    void addToCartOnAction(ActionEvent event) {
        String RentId = cmbRentId.getValue();
        String Description = lblDescription.getText();
        int Qty = Integer.parseInt(txtQty.getText());
        double UnitPrice = Double.parseDouble(lblUnitPrice.getText());
        String QtyOnHand = lblQtyOHand.getText();
        double Total = Qty * UnitPrice;
        JFXButton btnRemove = new JFXButton("Remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction(e   ->{
                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);


                    Optional<ButtonType> Desc  = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure want to remove?", yes, no).showAndWait();

                    if (Desc.orElse(no) == yes){
                        int selectedIndex = tblRentBookingCart.getSelectionModel().getSelectedIndex();
                        cartList.remove(selectedIndex);

                        tblRentBookingCart.refresh();
                        calculateNetTotal();
                    }
                }
                );

        for (int i = 0; i < tblRentBookingCart.getItems().size(); i++) {
            if (RentId.equals(colRentId.getCellData(i))){
                Qty += cartList.get(i).getQty();
                Total = UnitPrice * Qty;

                cartList.get(i).setQty(Qty);
                cartList.get(i).setTotal(Total);


                tblRentBookingCart.refresh();
                calculateNetTotal();
                txtQty.setText("");
                return;
            }
        }

        RentBookingTm rentBookingTm = new RentBookingTm(RentId, Qty, Description,  QtyOnHand, UnitPrice, Total, btnRemove);
        cartList.add(rentBookingTm);

        tblRentBookingCart.setItems(cartList);
        txtQty.setText("");
        calculateNetTotal();
    }

    @FXML
    void cmbCusOnAction(ActionEvent event) {
        String cId = cmbCId.getValue();
        try{
            Customer customer = CustomerRepo.searchCustomer(cId);
              lblCName.setText(customer.getCusName());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @FXML
    void cmbRentOnAction(ActionEvent event) {
        String rentId = cmbRentId.getValue();
        try {
            Rent rent = RentRepo.searchRent(rentId);
            if (rent != null){
                lblDescription.setText(rent.getDescription());
                lblUnitPrice.setText(String.valueOf(rent.getUnitPrice()));
              //  lblQtyOHand.setText(rent.getQtyOnHand());
                txtQty.setText(String.valueOf(rent.getQty()));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    private void calculateNetTotal() {
        netTotal = 0;
        for (int i = 0; i<tblRentBookingCart.getItems().size(); i++){
            netTotal += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
          lblBId.setText(RentBookingRepo.generateNextId());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        setDate();
        setCellValueFactory();
        getRentId();
        getCusID();


    }

    private void getCusID() {
        ObservableList<String> customerList = FXCollections.observableArrayList();

        try {
            List<String> customerIdList = RentBookingRepo.getCusId();
            for (String customerId : customerIdList) {
                customerList.add(customerId);
            }
            cmbCId.setItems(customerList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void getRentId() {
        ObservableList<String> rentList = FXCollections.observableArrayList();

        try {
            List<String> rentIdList = RentBookingRepo.getRentId();

            for (String rentId : rentIdList) {
                rentList.add(rentId);
            }
            cmbRentId.setItems(rentList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colRentId.setCellValueFactory(new PropertyValueFactory<>("RentId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));

    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblBDate.setText(String.valueOf(now));
    }
}
