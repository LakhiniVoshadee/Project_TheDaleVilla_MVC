package lk.ijse.thedale.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class RoomBookingTm {
    private String RoomID;
    private String Type;
    private double UnitPrice;
    private String QtyOnHand;
    private int Qty;
    private double Total;
    private JFXButton btnRemove;
}
