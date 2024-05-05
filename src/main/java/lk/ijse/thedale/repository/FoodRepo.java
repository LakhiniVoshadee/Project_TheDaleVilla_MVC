package lk.ijse.thedale.repository;

import lk.ijse.thedale.db.Dbconnection;
import lk.ijse.thedale.model.Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FoodRepo {
    public static boolean save(Food food) throws SQLException {
        String sql = "INSERT INTO food VALUES(?,?)";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        
        pstm.setObject(1,food.getFoodID());
        pstm.setObject(2,food.getDescription());
        
        return pstm.executeUpdate() >0;
    }

    public static boolean update(Food food) throws SQLException {
        String sql = "UPDATE food SET Description = ? WHERE FoodID = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        
        pstm.setObject(1,food.getDescription());
        pstm.setObject(2,food.getFoodID());
        
        return pstm.executeUpdate() >0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM food WHERE FoodID = ?";
        PreparedStatement pstm = Dbconnection.getInstance().getConnection().prepareStatement(sql);
        
        return pstm.executeUpdate() >0;
    }

    public static String generateNextId() throws SQLException {
        String sql = "Select FoodId from food order by foodID desc limit 1";
        Connection connection = Dbconnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String id = null;
        if(resultSet.next()){
            id = resultSet.getString(1);
            return splitId(id);
        }
        return splitId(null);
    }

    private static String splitId(String id) {
        if (id != null){
            String[] split = id.split("Food ");
            int FoodId = Integer.parseInt(split[1]);
            FoodId++;
            return "Food "+FoodId;
        }
        return "Food 1";

    }

    public List<Food> getFood() throws SQLException {
        String sql = "SELECT * FROM food";
        ResultSet resultSet = Dbconnection.getInstance().getConnection().prepareStatement(sql).executeQuery();
        List<Food> foodList = new ArrayList<Food>();
        while (resultSet.next()){
            foodList.add(new Food(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }
        return foodList;
    }
}
