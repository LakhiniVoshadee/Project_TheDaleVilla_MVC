/*package lk.ijse.thedale.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lk.ijse.thedale.repository.RentBookingRepo;
import lk.ijse.thedale.tm.RentBookingTm;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private JFXComboBox<?> cmbCId;

    @FXML
    private JFXComboBox<?> cmbRentId;

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


    private ObservableList<RentBookingTm> cartList = FXCollections.observableArrayList();
    private double netTotal = 0;

    @FXML
    void addToCartOnAction(ActionEvent event) {


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
          lblBId.setText(RentBookingRepo.generateNextId());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        setDate();

    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblBDate.setText(String.valueOf(now));
    }
}
*/