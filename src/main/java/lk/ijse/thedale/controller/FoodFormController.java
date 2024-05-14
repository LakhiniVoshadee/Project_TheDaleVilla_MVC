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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lk.ijse.thedale.model.Food;
import lk.ijse.thedale.repository.FoodRepo;
import lk.ijse.thedale.tm.FoodTm;
import lk.ijse.thedale.util.Validation;
//import lk.ijse.thedale.util.Validation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class FoodFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colDes;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<FoodTm> tblFood;


    @FXML
    private TextField txtDesId;

    @FXML
    private TextField txtFoodId;

    LinkedHashMap<TextField, Pattern> map =new LinkedHashMap();

    FoodRepo foodRepo = new FoodRepo();

    private List<Food> foodList = new ArrayList<>();

    @FXML
    void btnClearOnAction(ActionEvent event) { clearFields();}

    private void clearFields(){
        txtFoodId.setText("");
        txtDesId.setText("");

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtFoodId.getText();

        try {
            boolean isDeleted = FoodRepo.delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted Successfully").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("FoodID"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("Description"));

    }

    private void loadFoodTable() {
        FoodRepo foodRepo = new FoodRepo();
        ObservableList<FoodTm>tmList = FXCollections.observableArrayList();
        try {
            List<Food>foodList = foodRepo.getFood();
            for (Food food : foodList) {
                FoodTm foodTm = new FoodTm(
                        food.getFoodID(),
                        food.getDescription()
                );
                tmList.add(foodTm);
            }
            tblFood.setItems(tmList);
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }

    private List<Food> getAllFood() {
        List<Food>foodList = null;
        try {
            foodList = foodRepo.getFood();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        return foodList;
    }

    @FXML
    void btnSaveOnACtion(ActionEvent event) {
        String id = txtFoodId.getText();
        String des = txtDesId.getText();

        Food food = new  Food(id, des);

        try {
            boolean isSaved = FoodRepo.save(food);
               new Alert(Alert.AlertType.CONFIRMATION,"Food saved successfully.").show();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            loadFoodTable();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtFoodId.getText();
        String des = txtDesId.getText();

        Food food = new  Food(id, des);

        try {
            boolean isUpdated = FoodRepo.update(food);
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Food updated successfully.").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            loadFoodTable();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtFoodId.setText(FoodRepo.generateNextId());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        this.foodList = getAllFood();
        setCellValueFactory();
        loadFoodTable();

      /*  Pattern patternId = Pattern.compile("([A-Z0-9])$");

        map.put(txtFoodId, patternId);

       */
    }

    @FXML
    void txtOnKeyRele(KeyEvent event) {
        Validation.validate(map);



    }


}
