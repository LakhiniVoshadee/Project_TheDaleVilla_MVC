package lk.ijse.thedale.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.thedale.model.Room;
import lk.ijse.thedale.repository.CustomerRepo;
import lk.ijse.thedale.repository.RoomRepo;
import lk.ijse.thedale.tm.RoomTm;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RoomFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;


    @FXML
    private Pane pageInPane;

    @FXML
    private TableView<RoomTm> tblRoom;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtRoomId;

    @FXML
    private TextField txtType;

    @FXML
    private ComboBox<String> cmbId;

    @FXML
    private TableColumn<?, ?> colCusId;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtQty;






    RoomRepo roomRepo = new RoomRepo();

    private List<Room> roomList = new ArrayList<>();


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearField();
    }

    private void clearField(){
        txtRoomId.setText("");
        txtType.setText("");
        txtDate.setValue(null);
        txtQtyOnHand.setText("");
        txtUnitPrice.setText("");
        txtQty.setText("");

    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtRoomId.getText();
        RoomRepo roomRepo = new RoomRepo();

        try{
            boolean isDeleted = roomRepo.delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Room Deleted").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtRoomId.getText();
        String type = txtType.getText();
        String date = String.valueOf(txtDate.getValue());
        String qtyOnHand = txtQtyOnHand.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        String qty = txtQty.getText();
        String cusID = cmbId.getValue();

        Room room = new Room(id,type,date,qtyOnHand, unitPrice, qty, cusID);

        try{
            boolean isSaved = RoomRepo.save(room);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Room Saved").show();
                loadRoomTable();
            }
        }catch (SQLException e){
           new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtRoomId.getText();
        String type = txtType.getText();
        String date = txtDate.getValue().toString();
        String qtyOnHand = txtQtyOnHand.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        String qty = txtQty.getText();
        String cusID = cmbId.getValue();

        Room room = new Room(id,type,date,qtyOnHand, unitPrice,qty, cusID);

        try{
            boolean isUpdated = RoomRepo.update(room);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Room Updated").show();
                loadRoomTable();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadRoomTable() {
        RoomRepo roomRepo = new RoomRepo();
        ObservableList<RoomTm>tmList = FXCollections.observableArrayList();
        try{
            List<Room> roomList = roomRepo.getRoom();
            for (Room room : roomList) {
                RoomTm roomTm = new RoomTm(
                        room.getRoomID(),
                        room.getType(),
                        room.getDate(),
                        room.getCusID(),
                        room.getQtyOnHand(),
                        room.getUnitPrice(),
                        room.getQty()
                );
                tmList.add(roomTm);
            }
            tblRoom.setItems(tmList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtRoomId.setText(RoomRepo.generateNextId());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
         this.roomList = getAllRoom();
        setCellValueFactory();
        loadRoomTable();
        getCustomerId();
    }

    private void getCustomerId() throws SQLException {
        ObservableList<String> obList = FXCollections.observableArrayList();

        List<String> idList = CustomerRepo.getIds();

        for (String id : idList) {
            obList.add(id);
        }
        cmbId.setItems(obList);
    }

    private void setCellValueFactory() {

        colId.setCellValueFactory(new PropertyValueFactory<>("RoomID"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colCusId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
    }

    private List<Room> getAllRoom() {
        List<Room> roomList = null;
        try {
            roomList = roomRepo.getRoom();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return roomList;
    }
}
