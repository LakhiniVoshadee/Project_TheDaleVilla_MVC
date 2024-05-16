package lk.ijse.thedale.model;

import javafx.scene.chart.PieChart;
import lk.ijse.thedale.db.Dbconnection;
import lk.ijse.thedale.repository.RoomRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomePage {
    RoomRepo roomRepo = new RoomRepo();
    public ArrayList<PieChart.Data> getPieChartData() throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();
        String sql = "select RoomID,SUM(Qty)as orderCount from RoomDetails group by RoomID order by orderCount desc limit 5";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ArrayList<PieChart.Data> pieChartData = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Room room = roomRepo.searchRoom(resultSet.getString(1));
            pieChartData .add(
                    new PieChart.Data(room.getType(), resultSet.getInt(2))
            );
        }
        return pieChartData;

    }
}
