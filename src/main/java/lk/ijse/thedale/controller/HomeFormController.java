package lk.ijse.thedale.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lk.ijse.thedale.model.HomePage;
import lk.ijse.thedale.repository.CustomerRepo;
import lk.ijse.thedale.repository.RoomRepo;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeFormController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private Label lblCusCount;

    @FXML
    private Label lblRentalCount;

    @FXML
    private Label lblRoomCount;

    @FXML
    private Pane pagingPane;

    @FXML
    private PieChart pieChart;

    HomePage homePage = new HomePage();

   // RoomRepo roomRepo = new RoomRepo();

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPieChart();
        roomCount();
        customerCount();
        setDataToBarChart();

    }

    private void roomCount() {
        RoomRepo roomRepo = new RoomRepo();
        try {
            int count = roomRepo.countRoom();
            lblRoomCount.setText(String.valueOf("0"+count));
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    private void customerCount() {
        CustomerRepo customerRepo = new CustomerRepo();
        try {
            int count = customerRepo.countCustomer();
            lblCusCount.setText(String.valueOf("0"+count));
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

    }



    private void setDataToBarChart() throws SQLException {
        ObservableList<XYChart.Series<String, Integer>> barChartData = RoomRepo.getDataToBarChart();

        barChart.setData(barChartData);
    }

    private void setPieChart() {
        try {
            ObservableList<PieChart.Data> obList = FXCollections.observableArrayList();
            ArrayList<PieChart.Data> data = homePage.getPieChartData();
            for (PieChart.Data datum : data) {
                obList.add(datum);
            }
            pieChart.getData().addAll(obList);
            pieChart.setTitle("Most Trending Products");
            pieChart.setStartAngle(180);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
    }
}}
