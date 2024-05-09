package lk.ijse.thedale.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class RentBookingTm {
    private  String RentID;
    private int Qty;
    private String Description;
   // private String Type;
    private String QtyOnHand;
    private double UnitPrice;
    private double Total;
    private JFXButton btnRemove;


}
