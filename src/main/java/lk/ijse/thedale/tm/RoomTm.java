package lk.ijse.thedale.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class RoomTm {
    private String RoomID;
    private String Type;
    private String Date;
    private String customerId;
   // private String QtyOnHand;
    private double UnitPrice;
    private String Qty;
}
