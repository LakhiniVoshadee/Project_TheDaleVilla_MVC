package lk.ijse.thedale.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Room {
    private String RoomID;
    private String Type;
    private String Date;
  //  private String QtyOnHand;
    private double UnitPrice;
    private String Qty;
    private String CusID;
}
