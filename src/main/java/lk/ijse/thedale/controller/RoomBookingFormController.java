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
import lk.ijse.thedale.model.*;
import lk.ijse.thedale.repository.*;
import lk.ijse.thedale.tm.RoomBookingTm;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class RoomBookingFormController implements Initializable {

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
    private JFXComboBox<String> cmbRoomId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colQtOnHand;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colRmId;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colUnPrice;

    @FXML
    private Label lblBDate;

    @FXML
    private Label lblBId;

    @FXML
    private Label lblCName;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblQtyOHand;

    @FXML
    private Label lblType;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Pane pagingPane;

    @FXML
    private TextField txtQty;

    @FXML
    private TableView<RoomBookingTm> tblRmBookingCart;



    private ObservableList<RoomBookingTm> cartList = FXCollections.observableArrayList();
    private double netTotal = 0;

    @FXML
    void addToCartOnAction(ActionEvent event) {
        String RoomId = cmbRoomId.getValue();
        String Type = lblType.getText();
        int Qty = Integer.parseInt(txtQty.getText());
        double UnitPrice = Double.parseDouble(lblUnitPrice.getText());
        String QtyOnHand = lblQtyOHand.getText();
        double Total = Qty * UnitPrice;
        JFXButton btnRemove = new JFXButton("Remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction(e  ->{
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> Desc = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure want to remove?", yes, no).showAndWait();

            if (Desc.orElse(no) == yes){
                int selectedIndex = tblRmBookingCart.getSelectionModel().getSelectedIndex();
                cartList.remove(selectedIndex);

                tblRmBookingCart.refresh();
                calculateNetTotal();
            }
        });

        for (int i=0; i<tblRmBookingCart.getItems().size(); i++ ){
            if (RoomId.equals(colRmId.getCellData(i))){
                Qty += cartList.get(i).getQty();
                Total = UnitPrice * Qty;

                cartList.get(i).setQty(Qty);
                cartList.get(i).setTotal(Total);

                tblRmBookingCart.refresh();
                calculateNetTotal();
                txtQty.setText("");
                return;
            }

        }

        RoomBookingTm roomBookingTm = new RoomBookingTm(RoomId, Type, UnitPrice, QtyOnHand, Qty, Total, btnRemove);
        cartList.add(roomBookingTm);

        tblRmBookingCart.setItems(cartList);
        txtQty.setText("");
        calculateNetTotal();

    }


    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String RoomBookingId = lblBId.getText();
        Date date = Date.valueOf(LocalDate.now());
        String CustomerId = cmbCId.getValue();

        double bookingAmount = 0;
        RoomBooking roomBooking = new RoomBooking(RoomBookingId,CustomerId,date);
        List<RoomDetails>roombookingList = new ArrayList<>();

        for (int i=0; i<tblRmBookingCart.getItems().size(); i++){
            RoomBookingTm roomBookingTm = cartList.get(i);

            RoomDetails roomDetails = new RoomDetails(
                    RoomBookingId,
                    roomBookingTm.getRoomID(),
                    roomBookingTm.getQty(),
                    roomBookingTm.getUnitPrice(),
                    roomBookingTm.getType()
            );
            roombookingList.add(roomDetails);
        }
        PlacedRoomBooking placedRoomBooking = new PlacedRoomBooking(roomBooking,roombookingList);

        try {
            boolean isOrderPlaced = PlaceRoomRepo.orderPlaced(placedRoomBooking);
            if (isOrderPlaced){
                new Alert(Alert.AlertType.CONFIRMATION,"Order Placed Successfully").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Order Placed Failed").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }



    }
    @FXML
    void cmbCusOnAction(ActionEvent event) {
        String CId = cmbCId.getValue();
        try {
            Customer customer = CustomerRepo.searchCustomer(CId);
                lblCName.setText(customer.getCusName());

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @FXML
    void cmbRoomOnAction(ActionEvent event) {
        String roomId = cmbRoomId.getValue();
        try {
            Room room = RoomRepo.searchRoom(roomId);
            if (room != null){
                lblType.setText(room.getType());
                lblUnitPrice.setText(String.valueOf(room.getUnitPrice()));
                lblQtyOHand.setText(room.getQty());
               // lblQtyOHand.setText(String.valueOf(room.getQtyOnHand()));

            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    private void calculateNetTotal() {
        netTotal = 0;
        for (int i = 0; i <tblRmBookingCart.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);

        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblBId.setText(RoomBookingRepo.generateNextId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setDate();
        setCellValueFactory();
        getRoomId();
        getCusId();

    }

    private void getCusId() {
        ObservableList<String> customerList = FXCollections.observableArrayList();

        try {
            List<String> customerIdList = RoomBookingRepo.getCusId();
            for (String customerId : customerIdList) {
                customerList.add(customerId);
            }
            cmbCId.setItems(customerList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void getRoomId() {
        ObservableList<String> roomList = FXCollections.observableArrayList();

        try {
            List<String> roomIdList = RoomBookingRepo.getRoomId();

            for (String roomId : roomIdList) {
                roomList.add(roomId);
            }
            cmbRoomId.setItems(roomList);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnPrintBillOnAction(ActionEvent event) {


    }


    private void setCellValueFactory() {
       colRmId.setCellValueFactory(new PropertyValueFactory<>("RoomID"));
       colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
       colUnPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
       colQtOnHand.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));
       colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
       colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
       colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblBDate.setText(String.valueOf(now));
    }
}
