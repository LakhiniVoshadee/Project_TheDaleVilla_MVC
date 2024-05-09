package lk.ijse.thedale.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class RoomDetails {
    private String RoomBookingID;
    private String RoomID;
    private String Qty;
    private double UnitPrice;
}
