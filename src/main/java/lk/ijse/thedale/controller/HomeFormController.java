package lk.ijse.thedale.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lk.ijse.thedale.model.HomePage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeFormController implements Initializable {

    @FXML
    private BarChart<?, ?> barChart;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPieChart();
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
